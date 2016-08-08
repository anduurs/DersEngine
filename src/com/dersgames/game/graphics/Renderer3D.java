package com.dersgames.game.graphics;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import com.dersgames.game.components.Renderable3D;
import com.dersgames.game.components.lights.Light;
import com.dersgames.game.core.Camera;
import com.dersgames.game.core.Matrix4f;
import com.dersgames.game.graphics.renderers.EntityRenderer;
import com.dersgames.game.graphics.renderers.TerrainRenderer;
import com.dersgames.game.graphics.shaders.StaticShader;
import com.dersgames.game.graphics.shaders.TerrainShader;
import com.dersgames.game.terrains.Terrain;

public class Renderer3D {

	private Matrix4f m_Projection;
	
	private TerrainRenderer m_TerrainRenderer;
	private EntityRenderer m_EntityRenderer;

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
	
	public void render(Light sun, Camera camera){
		StaticShader shader = m_EntityRenderer.getShader();
		
		shader.enable();
		shader.loadLightSource(sun);
		shader.loadViewMatrix(camera);
		shader.loadProjectionMatrix(m_Projection);
		m_EntityRenderer.render();
		shader.disable();
		m_EntityRenderer.clear();
		
		TerrainShader terrainShader = m_TerrainRenderer.getShader();
		
		terrainShader.enable();
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
