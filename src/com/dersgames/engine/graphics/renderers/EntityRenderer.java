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

import com.dersgames.engine.components.Renderable3D;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.models.TexturedModel;
import com.dersgames.engine.graphics.shaders.StaticShader;
import com.dersgames.engine.graphics.textures.ModelTexture;

public class EntityRenderer {
	
	private StaticShader m_Shader;
	private Map<TexturedModel, List<Renderable3D>> m_Renderables;
	
	public EntityRenderer(){
		m_Shader = new StaticShader();
		m_Renderables = new HashMap<TexturedModel, List<Renderable3D>>();
	}
	
	public void addRenderable(Renderable3D renderable){
		TexturedModel model = renderable.getTexturedModel();
		if(m_Renderables.containsKey(model))
			m_Renderables.get(model).add(renderable);
		else{
			List<Renderable3D> batch = new ArrayList<Renderable3D>();
			batch.add(renderable);
			m_Renderables.put(model, batch);
		}
	}
	
	public void clear(){
		m_Renderables.clear();
	}
	
	public void render(){
		for(TexturedModel model : m_Renderables.keySet()){
			prepareTexturedModel(model);
			List<Renderable3D> batch = m_Renderables.get(model);
			for(Renderable3D renderable : batch){
				prepareRenderable(renderable);
				glDrawElements(GL_TRIANGLES, model.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
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
		
		ModelTexture texture = texturedModel.getModelTexture();
		if(texture.hasTransparency()) Renderer3D.disableCulling();
		m_Shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		m_Shader.loadUseFakeLighting(texture.getUseFakeLighting());
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texture.getID());
	}
	
	private void prepareRenderable(Renderable3D renderable){
		m_Shader.loadMatrix4f("transformationMatrix", 
			renderable.getEntity().getTransform().getTransformationMatrix());
	}
	
	private void unbindTexturedModel(){
		Renderer3D.enableCulling();
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
