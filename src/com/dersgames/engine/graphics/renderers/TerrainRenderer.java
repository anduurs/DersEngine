package com.dersgames.engine.graphics.renderers;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.GL_TEXTURE3;
import static org.lwjgl.opengl.GL13.GL_TEXTURE4;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.List;

import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.models.Mesh;
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
			glDrawElements(GL_TRIANGLES, terrain.getMesh().getVertexCount(), GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
		}
	}
	
	private void loadTexturedMeshData(Terrain terrain){
		Mesh mesh = terrain.getMesh();
		
		glBindVertexArray(mesh.getVaoID());
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		bindTextures(terrain);
	}
	
	private void bindTextures(Terrain terrain){
		TerrainTexturePack texturePack = terrain.getTexturePack();
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texturePack.getBackgroundTexture().getTextureID());
		
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, texturePack.getrTexture().getTextureID());
		
		glActiveTexture(GL_TEXTURE2);
		glBindTexture(GL_TEXTURE_2D, texturePack.getgTexture().getTextureID());
		
		glActiveTexture(GL_TEXTURE3);
		glBindTexture(GL_TEXTURE_2D, texturePack.getbTexture().getTextureID());
		
		glActiveTexture(GL_TEXTURE4);
		glBindTexture(GL_TEXTURE_2D, terrain.getBlendMap().getTextureID());
	}
	
	private void loadRenderableData(Renderable renderable){
		m_TerrainShader.loadModelMatrix(renderable.getEntity());
	}
	
	private void unbindTexturedModel(){
		RenderEngine.enableCulling();
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
	}
	
	public void dispose(){
		m_TerrainShader.deleteShaderProgram();
	}
	
	public TerrainShader getShader(){
		return m_TerrainShader;
	}

}
