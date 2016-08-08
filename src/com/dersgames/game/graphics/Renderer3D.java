package com.dersgames.game.graphics;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dersgames.game.components.Renderable3D;
import com.dersgames.game.components.lights.Light;
import com.dersgames.game.core.Camera;
import com.dersgames.game.core.Matrix4f;
import com.dersgames.game.entities.Entity;
import com.dersgames.game.graphics.models.Model;
import com.dersgames.game.graphics.models.TexturedModel;
import com.dersgames.game.graphics.shaders.Shader;
import com.dersgames.game.graphics.shaders.StaticShader;
import com.dersgames.game.graphics.textures.ModelTexture;

public class Renderer3D {
	
	private StaticShader m_Shader;
	private Matrix4f m_Projection;
	private Map<TexturedModel, List<Renderable3D>> m_Renderables;

	public Renderer3D(StaticShader shader, Matrix4f projection){
		m_Shader = shader;
		m_Projection = projection;
		m_Renderables = new HashMap<TexturedModel, List<Renderable3D>>();
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_FRONT);
	}
	
	public void submit(Renderable3D renderable){
		TexturedModel model = renderable.getTexturedModel();
		if(m_Renderables.containsKey(model))
			m_Renderables.get(model).add(renderable);
		else{
			List<Renderable3D> batch = new ArrayList<Renderable3D>();
			batch.add(renderable);
			m_Renderables.put(model, batch);
		}
	}
	
	public void render(Light sun, Camera camera){
		m_Shader.enable();
		m_Shader.loadLight(sun);
		m_Shader.loadViewMatrix(camera);
		m_Shader.loadProjectionMatrix(m_Projection);
		render(m_Renderables);
		m_Shader.disable();
		m_Renderables.clear();
	}
	
	private void render(Map<TexturedModel, List<Renderable3D>> renderables){
		for(TexturedModel model : renderables.keySet()){
			prepareTexturedModel(model);
			List<Renderable3D> batch = renderables.get(model);
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
		m_Shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texture.getID());
	}
	
	private void prepareRenderable(Renderable3D renderable){
		m_Shader.setUniform("transformationMatrix", 
				renderable.getEntity().getTransform().getTransformationMatrix());
	}
	
	private void unbindTexturedModel(){
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
	}
	
	public void cleanUp(){
		m_Shader.deleteShader();
	}
	
	public Shader getShader(){
		return m_Shader;
	}

}
