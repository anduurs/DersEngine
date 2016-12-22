package com.dersgames.engine.graphics.shaders;

import com.dersgames.engine.core.Matrix4f;

public class ParticleShader extends Shader{

	public ParticleShader() {
		super("particleVertexShader", "particleFragmentShader");
		
		addUniform("modelViewMatrix");
		addUniform("projectionMatrix");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	public void loadModelViewMatrix(Matrix4f matrix){
		loadMatrix4f("modelViewMatrix", matrix);
	}

}
