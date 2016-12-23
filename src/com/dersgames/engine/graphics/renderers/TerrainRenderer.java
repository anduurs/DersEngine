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

import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.shaders.TerrainShader;
import com.dersgames.engine.graphics.textures.TerrainTexturePack;
import com.dersgames.engine.terrains.Terrain;

public class TerrainRenderer {
	
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
	
	public void render(){
		for(Terrain terrain : m_Terrains){
			loadTexturedMeshData(terrain);
			loadRenderableData(terrain);
			glDrawElements(GL_TRIANGLES, terrain.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
		}
	}
	
	private void loadTexturedMeshData(Terrain terrain){
		Model mesh = terrain.getModel();
		
		glBindVertexArray(mesh.getVaoID());
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		
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
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
		glBindVertexArray(0);
	}
	
	public void dispose(){
		m_TerrainShader.deleteShaderProgram();
	}
	
	public TerrainShader getShader(){
		return m_TerrainShader;
	}

}
