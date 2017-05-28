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
import com.dersgames.engine.core.Scene;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.shaders.TerrainShader;
import com.dersgames.engine.graphics.textures.TerrainTexturePack;
import com.dersgames.engine.terrains.Terrain;

public class TerrainRenderer implements Renderer3D{
	
	private TerrainShader m_TerrainShader;
	private List<Terrain> m_Terrains;
	
	public TerrainRenderer(){
		m_TerrainShader = new TerrainShader();
		m_Terrains = new ArrayList<Terrain>();
		
		m_TerrainShader.enable();
		m_TerrainShader.connectTextureUnits();
		m_TerrainShader.disable();
	}
	
	public void addTerrain(Terrain terrain){
		m_Terrains.add(terrain);
	}
	
	public void clear(){
		m_Terrains.clear();
	}

	@Override
	public void begin(Camera camera) {
		m_TerrainShader.enable();
		m_TerrainShader.loadSkyColor(RenderEngine.getSkyColor());
		m_TerrainShader.loadLightSources(Scene.getDirectionalLight(), Scene.getPointLights(), Scene.getSpotLights());
		m_TerrainShader.loadViewMatrix(camera);
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
	public void end() {
		m_TerrainShader.disable();
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
		RenderEngine.enableFaceCulling();
		glBindVertexArray(0);
	}
	
	public void dispose(){
		m_TerrainShader.deleteShaderProgram();
	}
	
	public TerrainShader getShader(){
		return m_TerrainShader;
	}

}
