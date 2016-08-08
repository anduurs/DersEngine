package com.dersgames.game.graphics.shaders;

import com.dersgames.game.components.lights.Light;
import com.dersgames.game.core.Camera;
import com.dersgames.game.core.Matrix4f;

public class StaticShader extends Shader{

	public StaticShader() {
		super("vertexShader", "fragmentShader");
		enable();
		addUniform("textureSampler");
		addUniform("transformationMatrix");
		addUniform("projectionMatrix");
		addUniform("viewMatrix");
		addUniform("lightPosition");
		addUniform("lightColor");
		addUniform("shineDamper");
		addUniform("reflectivity");
		addUniform("useFakeLighting");
		
		loadInteger("textureSampler", 0);
		disable();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}
	
	public void loadUseFakeLighting(boolean useFakelighting){
		if(useFakelighting)
			loadFloat("useFakeLighting", 1.0f);
		else loadFloat("useFakeLighting", 0.0f);
	}
	
	public void loadShineVariables(float damper, float reflectivity){
		loadFloat("shineDamper", damper);
		loadFloat("reflectivity", reflectivity);
	}
	
	public void loadLightSource(Light light){
		loadVector3f("lightPosition", light.getPosition());
		loadVector3f("lightColor", light.getColor());
	}
	
	public void loadViewMatrix(Camera camera){
		loadMatrix4f("viewMatrix", camera.getViewMatrix());
	}
	
	public void loadProjectionMatrix(Matrix4f projection){
		loadMatrix4f("projectionMatrix", projection);
	}

}
