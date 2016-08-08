package com.dersgames.game.graphics.renderers;

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

import com.dersgames.game.components.Renderable3D;
import com.dersgames.game.graphics.Renderer3D;
import com.dersgames.game.graphics.models.Model;
import com.dersgames.game.graphics.models.TexturedModel;
import com.dersgames.game.graphics.shaders.TerrainShader;
import com.dersgames.game.graphics.textures.ModelTexture;
import com.dersgames.game.terrains.Terrain;

public class TerrainRenderer {
	
	private TerrainShader m_TerrainShader;
	private List<Terrain> m_Terrains;
	
	public TerrainRenderer(){
		m_TerrainShader = new TerrainShader();
		m_Terrains = new ArrayList<Terrain>();
	}
	
	public void addTerrain(Terrain terrain){
		m_Terrains.add(terrain);
	}
	
	public void clear(){
		m_Terrains.clear();
	}
	
	public void render(){
		for(Terrain terrain : m_Terrains){
			prepareTexturedModel(terrain.getTexturedModel());
			prepareRenderable(terrain);
			glDrawElements(GL_TRIANGLES, terrain.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
		}
	}
	
	private void prepareTexturedModel(TexturedModel texturedModel){
		Model model = texturedModel.getModel();
		
		glBindVertexArray(model.getVaoID());
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		ModelTexture texture = texturedModel.getModelTexture();
		if(texture.hasTransparency()) Renderer3D.disableCulling();
		m_TerrainShader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texture.getID());
	}
	
	private void prepareRenderable(Renderable3D renderable){
		m_TerrainShader.loadMatrix4f("transformationMatrix", 
				renderable.getEntity().getTransform().getTransformationMatrix());
	}
	
	private void unbindTexturedModel(){
		Renderer3D.enableCulling();
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
	}
	
	public void cleanUp(){
		m_TerrainShader.deleteShader();
	}
	
	public TerrainShader getShader(){
		return m_TerrainShader;
	}

}
