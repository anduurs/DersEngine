package com.dersgames.game.graphics;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import com.dersgames.game.core.Camera;
import com.dersgames.game.core.Matrix4f;
import com.dersgames.game.graphics.models.TexturedModel;
import com.dersgames.game.graphics.shaders.Shader;

public class Renderer3D {
	
	private Shader m_Shader;
	private Matrix4f m_Projection;
	private Camera m_MainCamera;
	
	public Renderer3D(Shader shader, Matrix4f projection, Camera camera){
		m_Shader = shader;
		m_Projection = projection;
		m_MainCamera = camera;
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
	}
	
	public void render(TexturedModel texturedModel){
		m_Shader.setUniform("projectionMatrix", m_Projection);
		m_Shader.setUniform("viewMatrix", m_MainCamera.getViewMatrix());
		
		glBindVertexArray(texturedModel.getModel().getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texturedModel.getModelTexture().getID());
		
		glDrawElements(GL_TRIANGLES, texturedModel.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
	
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
	}
	
	public Shader getShader(){
		return m_Shader;
	}

}
