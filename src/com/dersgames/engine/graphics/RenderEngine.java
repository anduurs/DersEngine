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

import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.components.lights.DirectionalLight;
import com.dersgames.engine.components.lights.PointLight;
import com.dersgames.engine.components.lights.SpotLight;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.graphics.renderers.EntityRenderer;
import com.dersgames.engine.graphics.renderers.SkyboxRenderer;
import com.dersgames.engine.graphics.renderers.TerrainRenderer;
import com.dersgames.engine.graphics.shaders.EntityShader;
import com.dersgames.engine.graphics.shaders.SkyboxShader;
import com.dersgames.engine.graphics.shaders.TerrainShader;
import com.dersgames.engine.input.KeyInput;
import com.dersgames.engine.terrains.Terrain;

public class RenderEngine {

	private static Camera m_Camera;
	
	private static TerrainRenderer m_TerrainRenderer;
	private static EntityRenderer m_EntityRenderer;
	private static SkyboxRenderer m_SkyboxRenderer;
	
	private static Vector3f m_SkyColor = new Vector3f(135.0f / 255.0f, 210.0f / 255.0f, 235.0f / 255.0f);
//	private static Vector3f m_SkyColor = new Vector3f(0.1f, 0.1f, 0.1f);
	
	private boolean m_RenderNormals = false;
	private boolean m_RenderTangents = false;
	private boolean m_WireFrameMode = false;

	public RenderEngine(){
		m_TerrainRenderer = new TerrainRenderer();
		m_EntityRenderer  = new EntityRenderer();
		m_SkyboxRenderer = new SkyboxRenderer();
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_MULTISAMPLE);
		
		enableCulling();
	}
	
	public static void enableCulling(){
		glEnable(GL_CULL_FACE);
		glCullFace(GL_FRONT);
	}
	
	public static void disableCulling(){
		glDisable(GL_CULL_FACE);
	}
	
	public void submit(Renderable renderable){
		if(renderable instanceof Terrain)
			m_TerrainRenderer.addTerrain((Terrain)renderable);
		else m_EntityRenderer.addRenderable(renderable);
	}
	
	public void render(DirectionalLight directionalLight, List<PointLight> pointLights, List<SpotLight> spotLights){
		clearFrameBuffer();
		
		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_N)){
			m_RenderNormals = true;
			m_RenderTangents = false;
			m_WireFrameMode = false;
		}else if(KeyInput.isKeyDown(GLFW.GLFW_KEY_T)){
			m_RenderNormals = false;
			m_RenderTangents = true;
			m_WireFrameMode = false;
		}else if(KeyInput.isKeyDown(GLFW.GLFW_KEY_P)){
			m_RenderNormals = false;
			m_RenderTangents = false;
			m_WireFrameMode = true;
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		}else if(KeyInput.isKeyDown(GLFW.GLFW_KEY_SPACE)){
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
			m_RenderNormals = false;
			m_RenderTangents = false;
			m_WireFrameMode = false;
		}
			
		EntityShader shader = m_EntityRenderer.getShader();
		shader.enable();
		
		if(m_RenderNormals){
			shader.loadInteger("renderNormals", 1);
			shader.loadInteger("renderTangents", 0);
			shader.loadInteger("wireframeMode", 0);
		}
		else if(m_RenderTangents){
			shader.loadInteger("renderTangents", 1);
			shader.loadInteger("renderNormals", 0);
			shader.loadInteger("wireframeMode", 0);
		}else if(m_WireFrameMode){
			shader.loadInteger("wireframeMode", 1);
			shader.loadInteger("renderTangents", 0);
			shader.loadInteger("renderNormals", 0);
		}else {
			shader.loadInteger("renderTangents", 0);
			shader.loadInteger("renderNormals", 0);
			shader.loadInteger("wireframeMode", 0);
		}
		
		shader.loadSkyColor(m_SkyColor);
		shader.loadLightSources(directionalLight, pointLights, spotLights);
		shader.loadViewMatrix(m_Camera);
		m_EntityRenderer.render();
		shader.disable();
		m_EntityRenderer.clear();
		
		TerrainShader terrainShader = m_TerrainRenderer.getShader();
		terrainShader.enable();
		
		if(m_RenderNormals){
			terrainShader.loadInteger("renderNormals", 1);
//			terrainShader.loadInteger("renderTangents", 0);
			terrainShader.loadInteger("wireframeMode", 0);
//		}
//		else if(m_RenderTangents){
//			terrainShader.loadInteger("renderTangents", 1);
//			terrainShader.loadInteger("renderNormals", 0);
//			terrainShader.loadInteger("wireframeMode", 0);
		}else if(m_WireFrameMode){
			terrainShader.loadInteger("wireframeMode", 1);
//			terrainShader.loadInteger("renderTangents", 0);
			terrainShader.loadInteger("renderNormals", 0);
		}else {
//			terrainShader.loadInteger("renderTangents", 0);
			terrainShader.loadInteger("renderNormals", 0);
			terrainShader.loadInteger("wireframeMode", 0);
		}
		
		terrainShader.loadSkyColor(m_SkyColor);
		terrainShader.loadLightSources(directionalLight, pointLights, spotLights);
		terrainShader.loadViewMatrix(m_Camera);
		m_TerrainRenderer.render();
		terrainShader.disable();
		m_TerrainRenderer.clear();
		
		SkyboxShader skyboxShader = m_SkyboxRenderer.getShader();
		skyboxShader.enable();
		skyboxShader.loadViewMatrix(m_Camera);
		m_SkyboxRenderer.render();
		skyboxShader.disable();
	}
	
	public void addCamera(Camera camera){
		m_Camera = camera;
		
		EntityShader shader = m_EntityRenderer.getShader();
		shader.enable();
		shader.loadProjectionMatrix(m_Camera.getProjectionMatrix());
		shader.disable();
		
		TerrainShader terrainShader = m_TerrainRenderer.getShader();
		terrainShader.enable();
		terrainShader.loadProjectionMatrix(m_Camera.getProjectionMatrix());
		terrainShader.disable();
		
		SkyboxShader skyboxShader = m_SkyboxRenderer.getShader();
		skyboxShader.enable();
		skyboxShader.loadProjectionMatrix(m_Camera.getProjectionMatrix());
		skyboxShader.disable();
	}
	
	public void dispose(){
		m_TerrainRenderer.dispose();
		m_EntityRenderer.dispose();
	}
	
	private void clearFrameBuffer(){
		glClearColor(m_SkyColor.x, m_SkyColor.y, m_SkyColor.z, 0.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public static TerrainRenderer getTerrainRenderer() {
		return m_TerrainRenderer;
	}

	public static EntityRenderer getEntityRenderer() {
		return m_EntityRenderer;
	}
	
	public static Camera getCamera(){
		return m_Camera;
	}

}
