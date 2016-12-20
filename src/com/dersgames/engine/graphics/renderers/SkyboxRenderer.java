package com.dersgames.engine.graphics.renderers;

import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.shaders.SkyboxShader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class SkyboxRenderer {
	
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
	
	private static String[] CUBEMAP_TEXTURES = {"right", "left", "top", "bottom", "back", "front"};
	
	private int m_Texture;
	
	private Model m_Cube;
	private SkyboxShader m_Shader;
	
	public SkyboxRenderer(){
		m_Cube = Loader.loadModel(VERTICES, 3);
		m_Texture = Loader.loadCubeMapTexture(CUBEMAP_TEXTURES);
		m_Shader = new SkyboxShader();
	}
	
	public void render(){
		glBindVertexArray(m_Cube.getVaoID());
		glEnableVertexAttribArray(0);
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_CUBE_MAP, m_Texture);
		
		glDrawArrays(GL_TRIANGLES, 0, m_Cube.getVertexCount());
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}

	public SkyboxShader getShader() {
		return m_Shader;
	}
}
