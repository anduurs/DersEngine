package com.dersgames.engine.graphics.renderers;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.graphics.GLRenderUtils;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.shaders.PhongShader;
import com.dersgames.engine.graphics.shaders.ShaderManager;
import com.dersgames.engine.math.Vector2f;
import com.dersgames.engine.util.Pair;
import com.dersgames.examplegame.Game;

public class EntityRenderer implements IRenderer<StaticMesh> {

	protected PhongShader m_Shader;
	protected Map<Pair<Model, Material>, List<StaticMesh>> m_Renderables;
	
	public EntityRenderer(){
		m_Shader = (PhongShader) ShaderManager.getInstance().getShader(ShaderManager.DEFAULT_ENTITY_SHADER);
		m_Renderables = new HashMap<>();
	}
	
	@Override
	public void submit(StaticMesh renderable) {
		Model model  = renderable.getModel();
		Material mat = renderable.getMaterial();
		
		Pair<Model, Material> staticMeshKey = Pair.of(model, mat);
		
		if(m_Renderables.containsKey(staticMeshKey)) {
			m_Renderables.get(staticMeshKey).add(renderable);
		} else{
			List<StaticMesh> batch = new ArrayList<>();
			batch.add(renderable);
			m_Renderables.put(staticMeshKey, batch);
		}
	}
	
	protected void clear(){
		m_Renderables.clear();
	}

	@Override
	public void begin(){
		m_Shader.enable();
		RenderEngine renderEngine = RenderEngine.getInstance();
		m_Shader.loadVector3f("skyColor", renderEngine.getSkyColor());
		m_Shader.loadLightSources(Game.currentScene.getDirectionalLight(), 
				Game.currentScene.getPointLights(), Game.currentScene.getSpotLights());
		Camera camera = renderEngine.getCamera();
		m_Shader.loadViewMatrix(camera);
		m_Shader.loadProjectionMatrix(camera.getProjectionMatrix());
		m_Shader.loadClippingPlane(renderEngine.getCurrentClippingPlane());
	}

	@Override
	public void render(){
		for(Pair<Model, Material> staticMeshData : m_Renderables.keySet()){
			bind(staticMeshData);
			List<StaticMesh> batch = m_Renderables.get(staticMeshData);
			for(StaticMesh staticMesh : batch){
				updateStaticMeshSpecificData(staticMesh);
				GLRenderUtils.drawElements(staticMesh.getModel().getVertexCount());
			}
			unbind();
		}
	}

	protected void bind(Pair<Model, Material> staticMeshData){
		glBindVertexArray(staticMeshData.first.getVaoID());
		Material material = staticMeshData.second;
		
		if(material.hasTransparency()) 
			GLRenderUtils.disableFaceCulling();
		
		material.updateUniforms();
	
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, material.getTextureID());
		
		if(material.isUsingSpecularMap()){
			glActiveTexture(GL_TEXTURE1);
			glBindTexture(GL_TEXTURE_2D, material.getSpecularMapTextureID());
		}
	}

	protected void updateStaticMeshSpecificData(StaticMesh staticMesh){
		m_Shader.loadModelMatrix(staticMesh.getEntity());
		
		float xOffset = staticMesh.getTextureXOffset();
		float yOffset = staticMesh.getTextureYOffset();

		m_Shader.loadTexCoordOffset(new Vector2f(xOffset, yOffset));
	}

	protected void unbind(){
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, 0);
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, 0);
		GLRenderUtils.enableFaceCulling();
		glBindVertexArray(0);
	}

	@Override
	public void end(boolean lastRenderPass){
		m_Shader.disable();
		if(lastRenderPass){
			clear();
		}
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
