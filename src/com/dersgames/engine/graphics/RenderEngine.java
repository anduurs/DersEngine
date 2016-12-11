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

import java.util.List;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.components.lights.DirectionalLight;
import com.dersgames.engine.components.lights.PointLight;
import com.dersgames.engine.components.lights.SpotLight;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.graphics.renderers.EntityRenderer;
import com.dersgames.engine.graphics.renderers.TerrainRenderer;
import com.dersgames.engine.graphics.shaders.EntityShader;
import com.dersgames.engine.graphics.shaders.TerrainShader;
import com.dersgames.engine.terrains.Terrain;

public class RenderEngine {

	private static Camera m_Camera;
	
	private TerrainRenderer m_TerrainRenderer;
	private EntityRenderer m_EntityRenderer;
	
//	private static Vector3f m_SkyColor = new Vector3f(135.0f / 255.0f, 210.0f / 255.0f, 235.0f / 255.0f);
	private static Vector3f m_SkyColor = new Vector3f(0.1f, 0.1f, 0.1f);

	public RenderEngine(){
		m_TerrainRenderer = new TerrainRenderer();
		m_EntityRenderer  = new EntityRenderer();
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
		
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
		
		EntityShader shader = m_EntityRenderer.getShader();
		shader.enable();
		shader.loadSkyColor(m_SkyColor);
		shader.loadLightSources(directionalLight, pointLights, spotLights);
		shader.loadViewMatrix(m_Camera);
		m_EntityRenderer.render();
		shader.disable();
		m_EntityRenderer.clear();
		
		TerrainShader terrainShader = m_TerrainRenderer.getShader();
		terrainShader.enable();
		terrainShader.loadSkyColor(m_SkyColor);
		terrainShader.loadLightSources(directionalLight, pointLights, spotLights);
		terrainShader.loadViewMatrix(m_Camera);
		m_TerrainRenderer.render();
		terrainShader.disable();
		m_TerrainRenderer.clear();
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
	}
	
	public void dispose(){
		m_TerrainRenderer.dispose();
		m_EntityRenderer.dispose();
	}
	
	private void clearFrameBuffer(){
		glClearColor(m_SkyColor.x, m_SkyColor.y, m_SkyColor.z, 0.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public TerrainRenderer getTerrainRenderer() {
		return m_TerrainRenderer;
	}

	public EntityRenderer getEntityRenderer() {
		return m_EntityRenderer;
	}
	
	public static Camera getCamera(){
		return m_Camera;
	}

}
