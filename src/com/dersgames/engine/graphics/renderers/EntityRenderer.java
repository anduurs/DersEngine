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
import com.dersgames.engine.graphics.Material;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.models.TexturedMesh;
import com.dersgames.engine.graphics.shaders.StaticShader;

public class EntityRenderer {
	
	private StaticShader m_Shader;
	private Map<TexturedMesh, List<Renderable>> m_Renderables;
	
	public EntityRenderer(){
		m_Shader = new StaticShader();
		m_Renderables = new HashMap<TexturedMesh, List<Renderable>>();
	}
	
	public void addRenderable(Renderable renderable){
		StaticMesh sm = (StaticMesh) renderable;
		TexturedMesh mesh = sm.getTexturedMesh();
		if(m_Renderables.containsKey(mesh))
			m_Renderables.get(mesh).add(renderable);
		else{
			List<Renderable> batch = new ArrayList<Renderable>();
			batch.add(renderable);
			m_Renderables.put(mesh, batch);
		}
	}
	
	public void clear(){
		m_Renderables.clear();
	}
	
	public void render(){
		for(TexturedMesh mesh : m_Renderables.keySet()){
			bindTexturedMesh(mesh);
			List<Renderable> batch = m_Renderables.get(mesh);
			for(Renderable renderable : batch){
				StaticMesh sm = (StaticMesh) renderable;
				loadRenderableData(sm);
				glDrawElements(GL_TRIANGLES, sm.getMesh().getVertexCount(), GL_UNSIGNED_INT, 0);
			}
			unbind();
		}
	}
	
	private void bindTexturedMesh(TexturedMesh texturedMesh){
		glBindVertexArray(texturedMesh.getMesh().getVaoID());
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		Material material = texturedMesh.getMaterial();
		
		if(material.hasTransparency()) 
			RenderEngine.disableCulling();
		
		m_Shader.loadSpecularVariables(material.getShineDamper(), material.getReflectivity());
		m_Shader.loadUseFakeLighting(material.getUseFakeLighting());
		m_Shader.loadNumOfRows(material.getTextureAtlas().getNumberOfRows());
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texturedMesh.getMaterial().getTextureID());
	}
	
	private void loadRenderableData(StaticMesh mesh){
		m_Shader.loadMatrix4f("transformationMatrix", 
				mesh.getEntity().getTransform().getTransformationMatrix());
		
		float xOffset = mesh.getTextureXOffset();
		float yOffset = mesh.getTextureYOffset();
		
		m_Shader.loadOffset(new Vector2f(xOffset, yOffset));
	}
	
	private void unbind(){
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
