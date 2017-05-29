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
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

import com.dersgames.engine.graphics.renderers.*;
import com.dersgames.engine.graphics.shaders.*;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.GUIComponent;
import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.core.Debug;
import com.dersgames.engine.core.Scene;
import com.dersgames.engine.graphics.water.WaterTile;
import com.dersgames.engine.maths.Vector3f;
import com.dersgames.engine.terrains.Terrain;

import java.util.ArrayList;
import java.util.List;

public class RenderEngine {

	private static Camera m_Camera;
	
	private static TerrainRenderer m_TerrainRenderer;
	private static EntityRenderer m_EntityRenderer;
	private static NormalMapRenderer m_NormalMapRenderer;
	private static SkyboxRenderer m_SkyboxRenderer;
	private static WaterRenderer m_WaterRenderer;
	private static GUIRenderer m_GUIRenderer;
	private static PostProcessRenderer m_PostProcessRenderer;

	private List<Renderer3D> m_Renderers;
	
    private static Vector3f m_SkyColor = new Vector3f(135.0f / 255.0f, 210.0f / 255.0f, 235.0f / 255.0f);
	//private static Vector3f m_SkyColor = new Vector3f(0.5444f, 0.62f, 0.69f);
	
	private boolean m_RenderNormals = false;
	private boolean m_RenderTangents = false;
	private boolean m_WireFrameMode = false;

	private FrameBuffer m_FrameBuffer;
	
	public RenderEngine(){
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
		m_Renderers.add(m_SkyboxRenderer);
		m_Renderers.add(m_TerrainRenderer);
		//m_Renderers.add(m_WaterRenderer);


		m_FrameBuffer = new FrameBuffer(Window.getWidth(), Window.getHeight(), FrameBuffer.DepthBufferType.DEPTH_RENDER_BUFFER, false);
		
		Debug.log("All shaders compiled succesfully");
		
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
		m_FrameBuffer.bind();
		clearFrameBuffer();
		renderScene();
		m_FrameBuffer.unbind();
		m_PostProcessRenderer.render(m_FrameBuffer.getColorTexture());

		GUIShader guiShader = m_GUIRenderer.getShader();
		guiShader.enable();
		m_GUIRenderer.render();
		guiShader.disable();
		m_GUIRenderer.clear();
	}

	private void renderScene(){
		for(Renderer3D renderer : m_Renderers){
			renderer.begin(m_Camera);
			renderer.render();
			renderer.end();
		}
	}
	
	public void addCamera(Camera camera){
		m_Camera = camera;

		for(Renderer3D renderer : m_Renderers){
			Shader shader = renderer.getShader();
			shader.enable();
			shader.loadProjectionMatrix(m_Camera.getProjectionMatrix());
			shader.disable();
		}
	}
	
	public void dispose(){
		m_TerrainRenderer.dispose();
		m_EntityRenderer.dispose();
		m_NormalMapRenderer.dispose();
		m_SkyboxRenderer.dispose();
		m_WaterRenderer.dispose();
		m_GUIRenderer.dispose();
		m_PostProcessRenderer.dispose();
		m_FrameBuffer.dispose();
	}
	
	private void clearFrameBuffer(){
		glClearColor(m_SkyColor.x, m_SkyColor.y, m_SkyColor.z, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public static TerrainRenderer getTerrainRenderer() {
		return m_TerrainRenderer;
	}
	public static EntityRenderer getEntityRenderer() {
		return m_EntityRenderer;
	}
	public static  NormalMapRenderer getNormalMapRenderer(){ return m_NormalMapRenderer; }
	public static SkyboxRenderer getSkyboxRenderer() {
		return m_SkyboxRenderer;
	}
	public static WaterRenderer getWaterRenderer() {
		return m_WaterRenderer;
	}
	public static GUIRenderer getGUIRenderer(){
		return m_GUIRenderer;
	}
	
	public static Camera getCamera(){
		return m_Camera;
	}
	public static Vector3f getSkyColor(){ return m_SkyColor; }
}
