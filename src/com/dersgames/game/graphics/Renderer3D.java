package com.dersgames.game.graphics;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDisable;
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
import com.dersgames.game.graphics.models.Model;
import com.dersgames.game.graphics.models.TexturedModel;
import com.dersgames.game.graphics.shaders.Shader;
import com.dersgames.game.graphics.shaders.StaticShader;
import com.dersgames.game.graphics.shaders.TerrainShader;
import com.dersgames.game.graphics.textures.ModelTexture;
import com.dersgames.game.terrains.Terrain;

public class Renderer3D {
	
	private StaticShader m_Shader;
	private TerrainShader m_TerrainShader;
	private Matrix4f m_Projection;
	private Map<TexturedModel, List<Renderable3D>> m_Renderables;
	private List<Terrain> m_Terrains;

	public Renderer3D(){
		m_Shader = new StaticShader();
		m_TerrainShader = new TerrainShader();
		m_Projection = new Matrix4f().setPerspectiveProjection(70.0f, (float)Window.getWidth() / (float)Window.getHeight(), 0.01f, 1000.0f);
		m_Renderables = new HashMap<TexturedModel, List<Renderable3D>>();
		m_Terrains = new ArrayList<Terrain>();
		
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
		if(renderable instanceof Terrain){
			m_Terrains.add((Terrain)renderable);
		}else{
			TexturedModel model = renderable.getTexturedModel();
			if(m_Renderables.containsKey(model))
				m_Renderables.get(model).add(renderable);
			else{
				List<Renderable3D> batch = new ArrayList<Renderable3D>();
				batch.add(renderable);
				m_Renderables.put(model, batch);
			}
		}
	}
	
	public void render(Light sun, Camera camera){
		m_Shader.enable();
		m_Shader.loadLightSource(sun);
		m_Shader.loadViewMatrix(camera);
		m_Shader.loadProjectionMatrix(m_Projection);
		render(m_Renderables);
		m_Shader.disable();
		m_Renderables.clear();
		
		m_TerrainShader.enable();
		m_TerrainShader.loadLightSource(sun);
		m_TerrainShader.loadViewMatrix(camera);
		m_TerrainShader.loadProjectionMatrix(m_Projection);
		render(m_Terrains);
		m_TerrainShader.disable();
		
		m_Terrains.clear();
	}
	
	private void render(List<Terrain> terrains){
		for(Terrain terrain : terrains){
			prepareTexturedModel(terrain.getTexturedModel());
			prepareRenderable(terrain);
			glDrawElements(GL_TRIANGLES, terrain.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
		}
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
		if(texture.hasTransparency()) disableCulling();
		m_Shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texture.getID());
	}
	
	private void prepareRenderable(Renderable3D renderable){
		m_Shader.loadMatrix4f("transformationMatrix", 
				renderable.getEntity().getTransform().getTransformationMatrix());
	}
	
	private void unbindTexturedModel(){
		enableCulling();
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
