package com.dersgames.engine.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

import com.dersgames.engine.core.Scene;
import com.dersgames.engine.graphics.renderers.*;
import com.dersgames.engine.graphics.renderers.postprocessing.PostProcessRenderer;
import com.dersgames.engine.graphics.shaders.*;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.GUIComponent;
import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.core.Debug;
import com.dersgames.engine.core.Entity;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.graphics.water.WaterTile;
import com.dersgames.engine.math.Vector3f;
import com.dersgames.engine.math.Vector4f;
import com.dersgames.engine.terrain.Terrain;

import java.util.ArrayList;
import java.util.List;

public class RenderEngine {
	private Camera m_Camera;

	private List<Renderer3D> m_Renderers;
	
	private TerrainRenderer m_TerrainRenderer;
	private EntityRenderer m_EntityRenderer;
	private NormalMapRenderer m_NormalMapRenderer;
	private SkyboxRenderer m_SkyboxRenderer;
	private WaterRenderer m_WaterRenderer;
	private GUIRenderer m_GUIRenderer;
	private PostProcessRenderer m_PostProcessRenderer;

	private FrameBuffer m_MultiSampledFrameBuffer;
	private FrameBuffer m_OutputFrameBuffer;
	private FrameBuffer m_OutputFrameBuffer2;
	private FrameBuffer m_WaterReflectionFrameBuffer;
	private FrameBuffer m_WaterRefractionFrameBuffer;

    private Vector3f m_SkyColor = new Vector3f(135.0f / 255.0f, 210.0f / 255.0f, 235.0f / 255.0f);
	//private static Vector3f m_SkyColor = new Vector3f(0.5444f, 0.62f, 0.69f);

    public WaterTile water;
    
    private Vector4f m_ClippingPlane_Reflection;
    private Vector4f m_ClippingPlane_Refraction;
    private Vector4f m_ClippingPlane_Default;
    private Vector4f m_CurrentClippingPlane;
    
    public Vector4f getCurrentClippingPlane() {
    	return m_CurrentClippingPlane;
    }
    
    private static RenderEngine instance;
    
    public static RenderEngine getInstance() {
    	if (instance == null) {
    		instance = new RenderEngine();
    	}
    	
    	return instance;
    }

	private RenderEngine(){
		
	}
	
	public void init() {
		initFramebuffers();
        initRenderers();

		Debug.log("All shaders compiled succesfully");

		glSetup();
		
		m_ClippingPlane_Reflection = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
		m_ClippingPlane_Refraction = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
		m_ClippingPlane_Default    = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);

		//debugWater();
		
//		for(Renderer3D renderer : m_Renderers){
//			Shader shader = renderer.getShader();
//			shader.enable();
//			shader.loadProjectionMatrix(m_Camera.getProjectionMatrix());
//			shader.disable();
//		}
//
//		Shader shader = m_WaterRenderer.getShader();
//		shader.enable();
//		shader.loadProjectionMatrix(m_Camera.getProjectionMatrix());
//		shader.disable();
	}

	private void debugWater(){
		Entity guiEntity = new Entity("gui", 0.5f, 0.5f, 200f, 200f);
		guiEntity.addComponent(new GUIComponent("Gui", new Texture(m_WaterReflectionFrameBuffer.getColorTexture())));

		Entity guiEntity2 = new Entity("gui", -0.5f, 0.5f, 200f, 200f);
		guiEntity2.addComponent(new GUIComponent("Gui", new Texture(m_WaterRefractionFrameBuffer.getColorTexture())));

		//REFLECTION TEXTURE
		//Scene.addEntity(guiEntity);
		//REFRACTION TEXTURE
		//Scene.addEntity(guiEntity2);
	}

	private void initRenderers(){
		m_TerrainRenderer = new TerrainRenderer();
		m_EntityRenderer  = new EntityRenderer();
		m_NormalMapRenderer = new NormalMapRenderer();
		m_SkyboxRenderer = new SkyboxRenderer();
		m_WaterRenderer = new WaterRenderer(m_WaterReflectionFrameBuffer, m_WaterRefractionFrameBuffer);
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

		m_WaterReflectionFrameBuffer = new FrameBuffer(800, 600,
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

	public void enableFaceCulling(){
		glEnable(GL_CULL_FACE);
		glCullFace(GL_FRONT);
	}
	
	public void disableFaceCulling(){
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
		if(m_WaterRenderer.getNumberOfWaterTiles() > 0){
			renderWaterReflectionAndRefraction();
		}
		
		m_MultiSampledFrameBuffer.bind();

		clearFrameBuffer();
		m_CurrentClippingPlane = m_ClippingPlane_Default;
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
			renderer.begin();
			renderer.render();
			renderer.end(lastRenderPass);
		}
	}
	
	private void renderWaterReflectionAndRefraction(){
		glEnable(GL_CLIP_DISTANCE0);

		m_WaterReflectionFrameBuffer.bind();

		//move the camera down under the watertile and adjust the orientation
		float distance = 2 * (m_Camera.getPosition().y - water.getHeight());
		m_Camera.getPosition().y -= distance;
		m_Camera.invertPitch();

		clearFrameBuffer();
		m_CurrentClippingPlane = new Vector4f(0.0f,1.0f,0.0f, -water.getHeight() - 18f);
		renderScene(false);

        //reset the camera position and orientation
        m_Camera.getPosition().y += distance;
        m_Camera.invertPitch();

		m_WaterReflectionFrameBuffer.unbind();

		m_WaterRefractionFrameBuffer.bind();
		clearFrameBuffer();
		m_CurrentClippingPlane = new Vector4f(0.0f,-1.0f,0.0f, water.getHeight() + 1f);
		renderScene(false);
		glDisable(GL_CLIP_DISTANCE0);
		m_WaterRefractionFrameBuffer.unbind();
	}

	private void renderWater(boolean lastRenderPass){
		m_WaterRenderer.begin();
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

	public TerrainRenderer getTerrainRenderer() { return m_TerrainRenderer; }
	public EntityRenderer getEntityRenderer() { return m_EntityRenderer; }
	public NormalMapRenderer getNormalMapRenderer(){ return m_NormalMapRenderer; }
	public SkyboxRenderer getSkyboxRenderer() { return m_SkyboxRenderer; }
	public WaterRenderer getWaterRenderer() { return m_WaterRenderer; }
	public GUIRenderer getGUIRenderer(){ return m_GUIRenderer; }
	public PostProcessRenderer getPostProcessRenderer(){ return m_PostProcessRenderer; }
	public Camera getCamera(){ return m_Camera; }
	public Vector3f getSkyColor(){ return m_SkyColor; }
}
