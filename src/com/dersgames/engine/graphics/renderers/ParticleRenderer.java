package com.dersgames.engine.graphics.renderers;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.List;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.core.Debug;
import com.dersgames.engine.core.Matrix4f;
import com.dersgames.engine.core.Scene;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.shaders.ParticleShader;
import com.dersgames.engine.particles.Particle;

public class ParticleRenderer {
	
	private static final float[] VERTICES = {-0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f};
	
	private Model m_Model;
	private ParticleShader m_Shader;
	
	public ParticleRenderer(){
		m_Shader = new ParticleShader();
		m_Model = Loader.loadModel(VERTICES, 2);
	}

	public void render(Camera camera){
		begin();
		
		for(Entity particle: Scene.getParticles()){
			updateModelViewMatrix(particle, camera);
			glDrawArrays(GL_TRIANGLES, 0, m_Model.getVertexCount());
		}
		
		end();
	}
	
	private void begin(){
		glBindVertexArray(m_Model.getVaoID());
		glEnableVertexAttribArray(0);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDepthMask(false);
	}
	
	private void updateModelViewMatrix(Entity particle, Camera camera){
		Matrix4f modelMatrix = particle.getTransform().getModelMatrix();
		Matrix4f viewMatrix = camera.getViewMatrix();
		
		modelMatrix.set(0, 0, viewMatrix.get(0, 0));
		modelMatrix.set(0, 1, viewMatrix.get(1, 0));
		modelMatrix.set(0, 2, viewMatrix.get(2, 0));
		
		modelMatrix.set(1, 0, viewMatrix.get(0, 1));
		modelMatrix.set(1, 1, viewMatrix.get(1, 1));
		modelMatrix.set(1, 2, viewMatrix.get(2, 1));
		
		modelMatrix.set(2, 0, viewMatrix.get(0, 2));
		modelMatrix.set(2, 1, viewMatrix.get(1, 2));
		modelMatrix.set(2, 2, viewMatrix.get(2, 2));
		
		particle.getTransform().rotate(new Vector3f(0,0,1), 0);
		particle.getTransform().scale(particle.getTransform().getScale());
		
		Matrix4f modelViewMatrix = viewMatrix.mul(modelMatrix);
		m_Shader.loadModelViewMatrix(modelViewMatrix);
	}
	
	private void end(){
		glDepthMask(true);
		glDisable(GL_BLEND);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
	
	public void dispose(){
		m_Shader.deleteShaderProgram();
	}

	public ParticleShader getShader() {
		return m_Shader;
	}
}
