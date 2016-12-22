package com.dersgames.engine.graphics.renderers;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.core.Vector2f;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.models.TexturedModel;
import com.dersgames.engine.graphics.shaders.EntityShader;

public class EntityRenderer {
	
	private EntityShader m_Shader;
	private Map<TexturedModel, List<StaticMesh>> m_Renderables;
	
	public EntityRenderer(){
		m_Shader = new EntityShader();
		m_Renderables = new HashMap<>();
	}
	
	public void addStaticMesh(StaticMesh staticMesh){
		TexturedModel model = staticMesh.getTexturedModel();
		
		if(m_Renderables.containsKey(model))
			m_Renderables.get(model).add(staticMesh);
		else{
			List<StaticMesh> batch = new ArrayList<>();
			batch.add(staticMesh);
			m_Renderables.put(model, batch);
		}
	}
	
	public void clear(){
		m_Renderables.clear();
	}
	
	public void render(){
		for(TexturedModel model : m_Renderables.keySet()){
			loadTexturedMeshData(model);
			List<StaticMesh> batch = m_Renderables.get(model);
			for(StaticMesh staticMesh : batch){
				loadRenderableData(staticMesh);
				glDrawElements(GL_TRIANGLES, staticMesh.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
			}
			unbind();
		}
	}
	
	private void loadTexturedMeshData(TexturedModel texturedModel){
		glBindVertexArray(texturedModel.getModel().getVaoID());
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		
		Material material = texturedModel.getMaterial();
		
		if(material.hasTransparency()) 
			RenderEngine.disableCulling();
		
		material.updateUniforms();
	
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texturedModel.getMaterial().getTextureID());
		
		if(material.isUsingSpecularMap()){
			glActiveTexture(GL_TEXTURE1);
			glBindTexture(GL_TEXTURE_2D, texturedModel.getMaterial().getSpecularMapTextureID());
		}
		
		if(material.isUsingNormalMap()){
			glActiveTexture(GL_TEXTURE2);
			glBindTexture(GL_TEXTURE_2D, texturedModel.getMaterial().getNormalMapTextureID());
		}
			
	}
	
	private void loadRenderableData(StaticMesh staticMesh){
		m_Shader.loadModelMatrix(staticMesh.getEntity());
		
		float xOffset = staticMesh.getTextureXOffset();
		float yOffset = staticMesh.getTextureYOffset();
		
		m_Shader.loadTexCoordOffset(new Vector2f(xOffset, yOffset));
	}
	
	private void unbind(){
		RenderEngine.enableCulling();
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
		
		glBindVertexArray(0);
	}
	
	public void dispose(){
		m_Shader.deleteShaderProgram();
	}
	
	public EntityShader getShader(){
		return m_Shader;
	}

}
