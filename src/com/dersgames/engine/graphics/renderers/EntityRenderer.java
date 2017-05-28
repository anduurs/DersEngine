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
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.core.Scene;
import com.dersgames.engine.components.Camera;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.models.TexturedModel;
import com.dersgames.engine.graphics.shaders.NormalMapShader;
import com.dersgames.engine.graphics.shaders.EntityShader;
import com.dersgames.engine.graphics.shaders.PhongShader;
import com.dersgames.engine.maths.Vector2f;
import com.dersgames.engine.maths.Vector3f;

public class EntityRenderer implements Renderer3D {

	protected PhongShader m_Shader;
	protected Map<TexturedModel, List<StaticMesh>> m_Renderables;
	
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

	protected void clear(){
		m_Renderables.clear();
	}

	@Override
	public void begin(Camera camera){
		m_Shader.enable();
		m_Shader.loadSkyColor(RenderEngine.getSkyColor());
		m_Shader.loadLightSources(Scene.getDirectionalLight(), Scene.getPointLights(), Scene.getSpotLights());
		m_Shader.loadViewMatrix(camera);
	}

	@Override
	public void render(){
		for(TexturedModel model : m_Renderables.keySet()){
			loadTexturedModelData(model);
			List<StaticMesh> batch = m_Renderables.get(model);
			for(StaticMesh staticMesh : batch){
				loadRenderableData(staticMesh);
				glDrawElements(GL_TRIANGLES, staticMesh.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
			}
			unbind();
		}
	}
	
	protected void loadTexturedModelData(TexturedModel texturedModel){
		glBindVertexArray(texturedModel.getModel().getVaoID());
		
		Material material = texturedModel.getMaterial();
		
		if(material.hasTransparency()) 
			RenderEngine.disableFaceCulling();
		
		material.updateUniforms();
	
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texturedModel.getMaterial().getTextureID());
		
		if(material.isUsingSpecularMap()){
			glActiveTexture(GL_TEXTURE1);
			glBindTexture(GL_TEXTURE_2D, texturedModel.getMaterial().getSpecularMapTextureID());
		}
	}

	protected void loadRenderableData(StaticMesh staticMesh){
		m_Shader.loadModelMatrix(staticMesh.getEntity());
		
		float xOffset = staticMesh.getTextureXOffset();
		float yOffset = staticMesh.getTextureYOffset();

		m_Shader.loadTexCoordOffset(new Vector2f(xOffset, yOffset));
	}

	protected void unbind(){
		RenderEngine.enableFaceCulling();
		glBindVertexArray(0);
	}

	@Override
	public void end(){
		m_Shader.disable();
		clear();
	}

	@Override
	public void dispose(){
		m_Shader.deleteShaderProgram();
	}

	@Override
	public PhongShader getShader(){
		return m_Shader;
	}
}
