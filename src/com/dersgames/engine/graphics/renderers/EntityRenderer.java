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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.core.Vector2f;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.models.TexturedModel;
import com.dersgames.engine.graphics.shaders.StaticShader;
import com.dersgames.engine.graphics.textures.ModelTexture;

public class EntityRenderer {
	
	private StaticShader m_Shader;
	private Map<TexturedModel, List<Renderable>> m_Renderables;
	
	public EntityRenderer(){
		m_Shader = new StaticShader();
		m_Renderables = new HashMap<TexturedModel, List<Renderable>>();
	}
	
	public void addRenderable(Renderable renderable){
		StaticMesh sm = (StaticMesh) renderable;
		TexturedModel model = sm.getTexturedModel();
		if(m_Renderables.containsKey(model))
			m_Renderables.get(model).add(renderable);
		else{
			List<Renderable> batch = new ArrayList<Renderable>();
			batch.add(renderable);
			m_Renderables.put(model, batch);
		}
	}
	
	public void clear(){
		m_Renderables.clear();
	}
	
	public void render(){
		for(TexturedModel texturedModel : m_Renderables.keySet()){
			prepareTexturedModel(texturedModel);
			List<Renderable> batch = m_Renderables.get(texturedModel);
			for(Renderable renderable : batch){
				prepareRenderable(renderable);
				glDrawElements(GL_TRIANGLES, texturedModel.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
	}
	
	private void prepareTexturedModel(TexturedModel texturedModel){
		Model model = texturedModel.getModel();
		
		glBindVertexArray(model.getVaoID());
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		ModelTexture texture = texturedModel.getTexture();
		
		if(texture.hasTransparency()) 
			RenderEngine.disableCulling();
		
		m_Shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		m_Shader.loadUseFakeLighting(texture.getUseFakeLighting());
		m_Shader.loadNumOfRows(texture.getNumberOfRows());
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texture.getID());
	}
	
	private void prepareRenderable(Renderable renderable){
		m_Shader.loadMatrix4f("transformationMatrix", 
			renderable.getEntity().getTransform().getTransformationMatrix());
		
		StaticMesh sm = (StaticMesh) renderable;
		
		float xOffset = sm.getTextureXOffset();
		float yOffset = sm.getTextureYOffset();
		
		m_Shader.loadOffset(new Vector2f(xOffset, yOffset));
	}
	
	private void unbindTexturedModel(){
		RenderEngine.enableCulling();
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		
		glBindVertexArray(0);
	}
	
	public void dispose(){
		m_Shader.deleteShaderProgram();
	}
	
	public StaticShader getShader(){
		return m_Shader;
	}

}
