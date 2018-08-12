package com.dersgames.engine.graphics.renderers;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.List;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.core.Debug;
import com.dersgames.engine.core.Scene;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.shaders.ShaderManager;
import com.dersgames.engine.graphics.shaders.TerrainShader;
import com.dersgames.engine.graphics.textures.TerrainTexturePack;
import com.dersgames.engine.maths.Vector4f;
import com.dersgames.engine.terrains.Terrain;
import com.dersgames.examplegame.Game;

public class TerrainRenderer implements Renderer3D{
	
	private TerrainShader m_TerrainShader;
	private List<Terrain> m_Terrains;
	
	public TerrainRenderer(){
		m_TerrainShader = (TerrainShader) ShaderManager.getInstance().getShader(ShaderManager.DEFAULT_TERRAIN_SHADER);
		m_Terrains = new ArrayList<Terrain>();
		
		m_TerrainShader.enable();
		m_TerrainShader.connectTextureUnits();
		m_TerrainShader.loadClippingPlane(new Vector4f(0.0f,-1.0f,0.0f,15.0f));
		m_TerrainShader.disable();
	}
	
	public void addTerrain(Terrain terrain){
		m_Terrains.add(terrain);
	}
	
	public void clear(){
		m_Terrains.clear();
	}

	@Override
	public void begin() {
		m_TerrainShader.enable();
		RenderEngine renderEngine = RenderEngine.getInstance();
		m_TerrainShader.loadSkyColor(renderEngine.getSkyColor());
		m_TerrainShader.loadLightSources(Game.currentScene.getDirectionalLight(), 
				Game.currentScene.getPointLights(), Game.currentScene.getSpotLights());
		Camera camera = renderEngine.getCamera();
		m_TerrainShader.loadViewMatrix(camera);
		m_TerrainShader.loadProjectionMatrix(camera.getProjectionMatrix());
		m_TerrainShader.loadClippingPlane(renderEngine.getCurrentClippingPlane());
	}

	public void render(){
		for(Terrain terrain : m_Terrains){
			loadTexturedMeshData(terrain);
			loadRenderableData(terrain);
			glDrawElements(GL_TRIANGLES, terrain.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
		}
	}

	@Override
	public void end(boolean lastRenderPass) {
		m_TerrainShader.disable();
		if(lastRenderPass){
			clear();
		}
	}

	private void loadTexturedMeshData(Terrain terrain){
		Model mesh = terrain.getModel();
		glBindVertexArray(mesh.getVaoID());
		bindTextures(terrain);
	}
	
	private void bindTextures(Terrain terrain){
		TerrainTexturePack texturePack = terrain.getTexturePack();
		
		int samplerSlot = 0;

		for(Material mat : texturePack.getTerrainMaterials()){
			mat.updateUniforms();
			glActiveTexture(GL_TEXTURE0 + samplerSlot);
			glBindTexture(GL_TEXTURE_2D, mat.getTextureID());
			samplerSlot++;
		}
		
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, terrain.getBlendMap().getTextureID());
	}
	
	private void loadRenderableData(Renderable renderable){
		m_TerrainShader.loadModelMatrix(renderable.getEntity());
	}
	
	private void unbindTexturedModel(){
		RenderEngine.getInstance().enableFaceCulling();
		glBindVertexArray(0);
	}
	
	public void dispose(){
		m_TerrainShader.deleteShaderProgram();
	}
	
	public TerrainShader getShader(){
		return m_TerrainShader;
	}

}
