package com.dersgames.game.graphics.shaders;

import com.dersgames.game.components.lights.Light;
import com.dersgames.game.core.Camera;
import com.dersgames.game.core.Matrix4f;

public class PhongShader extends Shader{

	public PhongShader() {
		super("basicVert", "basicFrag");
		enable();
		addUniform("textureSampler");
		addUniform("transformationMatrix");
		addUniform("projectionMatrix");
		addUniform("viewMatrix");
		addUniform("lightPosition");
		addUniform("lightColor");
		addUniform("shineDamper");
		addUniform("reflectivity");
		setUniformi("textureSampler", 0);
		disable();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}
	
	public void loadShineVariables(float damper, float reflectivity){
		setUniformf("shineDamper", damper);
		setUniformf("reflectivity", reflectivity);
	}
	
	public void loadLightSource(Light light){
		setUniformf("lightPosition", light.getPosition());
		setUniformf("lightColor", light.getColor());
	}
	
	public void loadViewMatrix(Camera camera){
		setUniform("viewMatrix", camera.getViewMatrix());
	}
	
	public void loadProjectionMatrix(Matrix4f projection){
		setUniform("projectionMatrix", projection);
	}

}
