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

import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.components.lights.Light;
import com.dersgames.engine.core.Camera;
import com.dersgames.engine.core.Matrix4f;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.graphics.renderers.EntityRenderer;
import com.dersgames.engine.graphics.renderers.TerrainRenderer;
import com.dersgames.engine.graphics.shaders.StaticShader;
import com.dersgames.engine.graphics.shaders.TerrainShader;
import com.dersgames.engine.terrains.Terrain;

public class RenderEngine {

	private Matrix4f m_Projection;
	
	private TerrainRenderer m_TerrainRenderer;
	private EntityRenderer m_EntityRenderer;
	
	private static Vector3f m_SkyColor = new Vector3f(135.0f / 255.0f, 210.0f / 255.0f, 235.0f / 255.0f);
//	private static Vector3f m_SkyColor = new Vector3f(0.5f, 0.5f, 0.5f);

	public RenderEngine(){
		m_Projection = new Matrix4f().setPerspectiveProjection(70.0f, 
				(float)Window.getWidth() / (float)Window.getHeight(), 0.01f, 1000.0f);
		
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
	
	public void render(List<Light> lightSources, Camera camera){
		clearFrameBuffer();
		
		StaticShader shader = m_EntityRenderer.getShader();
		shader.enable();
		shader.loadSkyColor(m_SkyColor);
		shader.loadLightSources(lightSources);
		shader.loadViewMatrix(camera);
		shader.loadProjectionMatrix(m_Projection);
		m_EntityRenderer.render();
		shader.disable();
		m_EntityRenderer.clear();
		
		TerrainShader terrainShader = m_TerrainRenderer.getShader();
		terrainShader.enable();
		terrainShader.loadSkyColor(m_SkyColor);
		terrainShader.loadLightSources(lightSources);
		terrainShader.loadViewMatrix(camera);
		terrainShader.loadProjectionMatrix(m_Projection);
		m_TerrainRenderer.render();
		terrainShader.disable();
		m_TerrainRenderer.clear();
	}
	
	public void dispose(){
		m_TerrainRenderer.dispose();
		m_EntityRenderer.dispose();
	}
	
	private void clearFrameBuffer(){
		glClearColor(m_SkyColor.x, m_SkyColor.y, m_SkyColor.z, 0.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

}