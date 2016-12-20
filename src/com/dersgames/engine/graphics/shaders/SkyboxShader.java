package com.dersgames.engine.graphics.shaders;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.core.Matrix4f;

public class SkyboxShader extends Shader{

	public SkyboxShader() {
		super("skyboxVertexShader", "skyboxFragmentShader");
		
		addUniform("viewMatrix");
		addUniform("projectionMatrix");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = camera.getViewMatrix();
		viewMatrix.set(0, 3, 0);
		viewMatrix.set(1, 3, 0);
		viewMatrix.set(2, 3, 0);
		loadMatrix4f("viewMatrix", viewMatrix);
	}

}
