package com.dersgames.engine.graphics.renderers;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.graphics.GLRenderUtils;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.models.ModelManager;
import com.dersgames.engine.graphics.shaders.ShaderManager;
import com.dersgames.engine.graphics.shaders.SkyboxShader;
import com.dersgames.engine.graphics.textures.TextureManager;

public class SkyboxRenderer implements IRenderer<Renderable>{
	
	private static final float SIZE = 1000f;
	
	private static final float[] VERTICES = {        
	    -SIZE,  SIZE, -SIZE,
	    -SIZE, -SIZE, -SIZE,
	    SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,
	     SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,

	    -SIZE, -SIZE,  SIZE,
	    -SIZE, -SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE,  SIZE,
	    -SIZE, -SIZE,  SIZE,

	     SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,

	    -SIZE, -SIZE,  SIZE,
	    -SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE, -SIZE,  SIZE,
	    -SIZE, -SIZE,  SIZE,

	    -SIZE,  SIZE, -SIZE,
	     SIZE,  SIZE, -SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	    -SIZE,  SIZE,  SIZE,
	    -SIZE,  SIZE, -SIZE,

	    -SIZE, -SIZE, -SIZE,
	    -SIZE, -SIZE,  SIZE,
	     SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,
	    -SIZE, -SIZE,  SIZE,
	     SIZE, -SIZE,  SIZE
	};
	
	private static String[] CUBEMAP_TEXTURES_DAY = {"right", "left", "top", "bottom", "back", "front"};
	private static String[] CUBEMAP_TEXTURES_NIGHT = {"nightRight", "nightLeft", "nightTop", "nightBottom", "nightBack", "nightFront"};
	
	private int m_DayTexture;
	private int m_NightTexture;
	
	private Model m_Cube;
	private SkyboxShader m_Shader;
	
	public SkyboxRenderer(){
		m_Cube 		   = ModelManager.getInstance().loadModel(VERTICES, 3);
		m_DayTexture   = TextureManager.getInstance().loadCubeMapTexture(CUBEMAP_TEXTURES_DAY);
		m_NightTexture = TextureManager.getInstance().loadCubeMapTexture(CUBEMAP_TEXTURES_NIGHT);
		m_Shader 	   = (SkyboxShader) ShaderManager.getInstance().getShader(ShaderManager.DEFAULT_SKYBOX_SHADER);
	}

	@Override
	public void begin() {
		m_Shader.enable();
		RenderEngine renderEngine = RenderEngine.getInstance();
		Camera camera = renderEngine.getCamera();
		m_Shader.loadViewMatrix(camera);
		m_Shader.loadProjectionMatrix(camera.getProjectionMatrix());
		m_Shader.loadFogColor(renderEngine.getSkyColor());
	}

	@Override
	public void render(){
		glBindVertexArray(m_Cube.getVaoID());
		bindTextures();
		GLRenderUtils.drawArrays(m_Cube.getVertexCount());
		glBindVertexArray(0);
	}

	@Override
	public void end(boolean lastRenderPass) {
		m_Shader.disable();
	}

	private void bindTextures(){
		m_Shader.loadBlendFactor(0.0f);
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_CUBE_MAP, m_DayTexture);
		
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_CUBE_MAP, m_NightTexture);
	}

	@Override
	public void dispose(){
		m_Shader.deleteShaderProgram();
	}

	@Override
	public SkyboxShader getShader() {
		return m_Shader;
	}

	@Override
	public void submit(Renderable renderable) {
		// TODO Auto-generated method stub
		
	}
}
