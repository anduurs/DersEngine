package com.dersgames.engine.graphics.shaders;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.math.Matrix4f;
import com.dersgames.engine.math.Vector3f;

public class SkyboxShader extends Shader{

	public SkyboxShader() {
		super("skybox/skyboxVertexShader", "skybox/skyboxFragmentShader");
		
		addUniform("viewMatrix");
		addUniform("projectionMatrix");
		addUniform("fogColor");
		addUniform("cubeMap");
		addUniform("cubeMap2");
		addUniform("blendFactor");
		
		enable();
		loadInteger("cubeMap", 0);
		loadInteger("cubeMap2", 1);
		disable();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	public void loadFogColor(Vector3f color){
		super.loadVector3f("fogColor", color);
	}
	
	public void loadBlendFactor(float blendFactor){
		super.loadFloat("blendFactor", blendFactor);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = camera.getViewMatrix();
		//make sure that the camera always will be at the center of the skybox by removing
		//the translation part of the view matrix
		viewMatrix.set(0, 3, 0);
		viewMatrix.set(1, 3, 0);
		viewMatrix.set(2, 3, 0);
		loadMatrix4f("viewMatrix", viewMatrix);
	}

}
