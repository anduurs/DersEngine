package com.dersgames.game.graphics;

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

import com.dersgames.game.components.Renderable3D;
import com.dersgames.game.components.lights.Light;
import com.dersgames.game.core.Camera;
import com.dersgames.game.core.Matrix4f;
import com.dersgames.game.core.Vector3f;
import com.dersgames.game.graphics.renderers.EntityRenderer;
import com.dersgames.game.graphics.renderers.TerrainRenderer;
import com.dersgames.game.graphics.shaders.StaticShader;
import com.dersgames.game.graphics.shaders.TerrainShader;
import com.dersgames.game.terrains.Terrain;

public class Renderer3D {

	private Matrix4f m_Projection;
	
	private TerrainRenderer m_TerrainRenderer;
	private EntityRenderer m_EntityRenderer;
	
//	private static Vector3f m_SkyColor = new Vector3f(135.0f / 255.0f, 206.0f / 255.0f, 235.0f / 255.0f);
	private static Vector3f m_SkyColor = new Vector3f(0.5f, 0.5f, 0.5f);

	public Renderer3D(){
		m_Projection = new Matrix4f().setPerspectiveProjection(70.0f, 
				(float)Window.getWidth() / (float)Window.getHeight(), 0.01f, 1000.0f);
		
		m_TerrainRenderer = new TerrainRenderer();
		m_EntityRenderer = new EntityRenderer();
		
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
	
	public void submit(Renderable3D renderable){
		if(renderable instanceof Terrain)
			m_TerrainRenderer.addTerrain((Terrain)renderable);
		else m_EntityRenderer.addRenderable(renderable);
	}
	
	private void clearBuffers(){
		glClearColor(m_SkyColor.x, m_SkyColor.y, m_SkyColor.z, 0.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void render(Light sun, Camera camera){
		clearBuffers();
		
		StaticShader shader = m_EntityRenderer.getShader();
		shader.enable();
		shader.loadSkyColor(m_SkyColor);
		shader.loadLightSource(sun);
		shader.loadViewMatrix(camera);
		shader.loadProjectionMatrix(m_Projection);
		m_EntityRenderer.render();
		shader.disable();
		m_EntityRenderer.clear();
		
		TerrainShader terrainShader = m_TerrainRenderer.getShader();
		terrainShader.enable();
		terrainShader.loadSkyColor(m_SkyColor);
		terrainShader.loadLightSource(sun);
		terrainShader.loadViewMatrix(camera);
		terrainShader.loadProjectionMatrix(m_Projection);
		m_TerrainRenderer.render();
		terrainShader.disable();
		m_TerrainRenderer.clear();
	}
	
	public void cleanUp(){
		m_TerrainRenderer.cleanUp();
		m_EntityRenderer.cleanUp();
	}

}
