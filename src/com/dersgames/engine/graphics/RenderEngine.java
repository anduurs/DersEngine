package com.dersgames.engine.graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

import com.dersgames.engine.core.Scene;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.graphics.renderers.*;
import com.dersgames.engine.graphics.renderers.postprocessing.PostProcessRenderer;
import com.dersgames.engine.graphics.shaders.*;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.GUIComponent;
import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.core.Debug;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.graphics.water.WaterTile;
import com.dersgames.engine.maths.Vector3f;
import com.dersgames.engine.terrains.Terrain;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class RenderEngine {
	private static Camera m_Camera;

	private List<Renderer3D> m_Renderers;
	
	private static TerrainRenderer m_TerrainRenderer;
	private static EntityRenderer m_EntityRenderer;
	private static NormalMapRenderer m_NormalMapRenderer;
	private static SkyboxRenderer m_SkyboxRenderer;
	private static WaterRenderer m_WaterRenderer;
	private static GUIRenderer m_GUIRenderer;
	private static PostProcessRenderer m_PostProcessRenderer;

	private FrameBuffer m_MultiSampledFrameBuffer;
	private FrameBuffer m_OutputFrameBuffer;
	private FrameBuffer m_OutputFrameBuffer2;
	private FrameBuffer m_WaterReflectionFrameBuffer;
	private FrameBuffer m_WaterRefractionFrameBuffer;

    private static Vector3f m_SkyColor = new Vector3f(135.0f / 255.0f, 210.0f / 255.0f, 235.0f / 255.0f);
	//private static Vector3f m_SkyColor = new Vector3f(0.5444f, 0.62f, 0.69f);

	public RenderEngine(){
		initRenderers();
		initFramebuffers();

		Debug.log("All shaders compiled succesfully");

		glSetup();

		Entity guiEntity = new Entity("gui", -0.5f, 0.5f, 200f, 200f);
		guiEntity.addComponent(new GUIComponent("Gui", new Texture(m_WaterReflectionFrameBuffer.getColorTexture())));
		Scene.addEntity(guiEntity);
	}

	private void initRenderers(){
		m_TerrainRenderer = new TerrainRenderer();
		m_EntityRenderer  = new EntityRenderer();
		m_NormalMapRenderer = new NormalMapRenderer();
		m_SkyboxRenderer = new SkyboxRenderer();
		m_WaterRenderer = new WaterRenderer();
		m_GUIRenderer = new GUIRenderer();
		m_PostProcessRenderer = new PostProcessRenderer();

		m_Renderers = new ArrayList<>();

		m_Renderers.add(m_EntityRenderer);
		m_Renderers.add(m_NormalMapRenderer);
		m_Renderers.add(m_TerrainRenderer);
		m_Renderers.add(m_SkyboxRenderer);		
	}

	private void initFramebuffers(){
		m_MultiSampledFrameBuffer = new FrameBuffer(Window.getWidth(), Window.getHeight(),
				FrameBuffer.DepthBufferType.DEPTH_RENDER_BUFFER, true, true);
		m_OutputFrameBuffer = new FrameBuffer(Window.getWidth(), Window.getHeight(),
				FrameBuffer.DepthBufferType.DEPTH_TEXTURE, false, false);
		m_OutputFrameBuffer2 = new FrameBuffer(Window.getWidth(), Window.getHeight(),
				FrameBuffer.DepthBufferType.DEPTH_TEXTURE, false, false);

		m_WaterReflectionFrameBuffer = new FrameBuffer(320, 180,
				FrameBuffer.DepthBufferType.DEPTH_RENDER_BUFFER, false, false);
		m_WaterRefractionFrameBuffer = new FrameBuffer(1280, 720,
				FrameBuffer.DepthBufferType.DEPTH_TEXTURE, false, false);
	}

	private void glSetup(){
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_MULTISAMPLE);

		enableFaceCulling();
	}

	public static void enableFaceCulling(){
		glEnable(GL_CULL_FACE);
		glCullFace(GL_FRONT);
	}
	
	public static void disableFaceCulling(){
		glDisable(GL_CULL_FACE);
	}
	
	public void submit(Renderable renderable){
		if(renderable instanceof Terrain)
			m_TerrainRenderer.addTerrain((Terrain)renderable);
		else if(renderable instanceof StaticMesh) {
			if(((StaticMesh) renderable).getMaterial().getShader() instanceof NormalMapShader){
				m_NormalMapRenderer.addStaticMesh((StaticMesh)renderable);
			}else{
				m_EntityRenderer.addStaticMesh((StaticMesh) renderable);
			}
		}
		else if(renderable instanceof WaterTile)
			m_WaterRenderer.addWaterTile((WaterTile)renderable);
		else if(renderable instanceof GUIComponent){
			m_GUIRenderer.addGUIComponent((GUIComponent)renderable);
		}
	}
	
	public void render(){
		m_WaterReflectionFrameBuffer.bind();

		clearFrameBuffer();
		renderScene(false);

		m_WaterReflectionFrameBuffer.unbind();

		m_MultiSampledFrameBuffer.bind();

		clearFrameBuffer();
		renderScene(true);
		renderWater(true);

		m_MultiSampledFrameBuffer.unbind();

		m_MultiSampledFrameBuffer.blitToFrameBuffer(GL_COLOR_ATTACHMENT0, m_OutputFrameBuffer);
		m_MultiSampledFrameBuffer.blitToFrameBuffer(GL_COLOR_ATTACHMENT1, m_OutputFrameBuffer2);

		m_PostProcessRenderer.renderPostProcessingEffects(m_OutputFrameBuffer.getColorTexture(),
				m_OutputFrameBuffer2.getColorTexture());

		renderGUI();
	}

	private void renderScene(boolean lastRenderPass){
		for(Renderer3D renderer : m_Renderers){
			renderer.begin(m_Camera);
			renderer.render();
			renderer.end(lastRenderPass);
		}
	}

	private void renderWater(boolean lastRenderPass){
		m_WaterRenderer.begin(m_Camera);
		m_WaterRenderer.render();
		m_WaterRenderer.end(lastRenderPass);
	}

	private void renderGUI(){
		m_GUIRenderer.begin();
		m_GUIRenderer.render();
		m_GUIRenderer.end();
	}
	
	public void addCamera(Camera camera){
		m_Camera = camera;

		for(Renderer3D renderer : m_Renderers){
			Shader shader = renderer.getShader();
			shader.enable();
			shader.loadProjectionMatrix(m_Camera.getProjectionMatrix());
			shader.disable();
		}

		Shader shader = m_WaterRenderer.getShader();
		shader.enable();
		shader.loadProjectionMatrix(m_Camera.getProjectionMatrix());
		shader.disable();
	}
	
	public void dispose(){
		for(Renderer3D renderer : m_Renderers)
		    renderer.dispose();

		m_WaterRenderer.dispose();
		m_GUIRenderer.dispose();
		m_PostProcessRenderer.dispose();
		m_MultiSampledFrameBuffer.dispose();
		m_OutputFrameBuffer.dispose();
		m_OutputFrameBuffer2.dispose();
		m_WaterReflectionFrameBuffer.dispose();
		m_WaterRefractionFrameBuffer.dispose();
        m_Renderers.clear();
	}
	
	private void clearFrameBuffer(){
		glClearColor(0,0,0, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public static TerrainRenderer getTerrainRenderer() {
		return m_TerrainRenderer;
	}
	public static EntityRenderer getEntityRenderer() {
		return m_EntityRenderer;
	}
	public static NormalMapRenderer getNormalMapRenderer(){ return m_NormalMapRenderer; }
	public static SkyboxRenderer getSkyboxRenderer() {
		return m_SkyboxRenderer;
	}
	public static WaterRenderer getWaterRenderer() {
		return m_WaterRenderer;
	}
	public static GUIRenderer getGUIRenderer(){
		return m_GUIRenderer;
	}
	public static PostProcessRenderer getPostProcessRenderer(){ return m_PostProcessRenderer; }
	
	public static Camera getCamera(){
		return m_Camera;
	}
	public static Vector3f getSkyColor(){ return m_SkyColor; }
}
