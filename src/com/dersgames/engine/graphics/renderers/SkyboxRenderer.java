package com.dersgames.engine.graphics.renderers;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.shaders.SkyboxShader;
import com.dersgames.engine.maths.Vector4f;

public class SkyboxRenderer implements Renderer3D{
	
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
		m_Cube = Loader.loadModel(VERTICES, 3);
		m_DayTexture = Loader.loadCubeMapTexture(CUBEMAP_TEXTURES_DAY);
		m_NightTexture = Loader.loadCubeMapTexture(CUBEMAP_TEXTURES_NIGHT);
		m_Shader = new SkyboxShader();
	}

	@Override
	public void begin(Camera camera, Vector4f clippingPlane) {
		m_Shader.enable();
		m_Shader.loadViewMatrix(camera);
		m_Shader.loadFogColor(RenderEngine.getSkyColor());
	}

	@Override
	public void render(){
		glBindVertexArray(m_Cube.getVaoID());
		bindTextures();
		glDrawArrays(GL_TRIANGLES, 0, m_Cube.getVertexCount());
		glBindVertexArray(0);
	}

	@Override
	public void end(boolean lastRenderPass) {
		m_Shader.disable();
	}

	private void bindTextures(){
		m_Shader.loadBlendFactor(1.0f);
		
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
}
