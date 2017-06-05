package com.dersgames.engine.graphics.shaders;

import com.dersgames.engine.core.Scene;
import com.dersgames.engine.entities.lights.DirectionalLight;
import com.dersgames.engine.entities.lights.PointLight;
import com.dersgames.engine.entities.lights.SpotLight;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.maths.Vector3f;

import java.util.List;

public class WaterShader extends Shader{

	public WaterShader() {
		super("water/waterVertexShader", "water/waterFragmentShader");
		
		addUniform("modelMatrix");
		addUniform("viewMatrix");
		addUniform("projectionMatrix");

		addUniform("reflectionTexture");
		addUniform("refractionTexture");
		addUniform("dudvMap");
		addUniform("normalMap");
		addUniform("depthMap");

		addUniform("moveFactor");

		addUniform("cameraPosition");

		//addUniform("directionalLight.direction");
		addUniform("lightPosition");
		addUniform("lightColor");
		//addUniform("directionalLight.light.intensity");
	}

	public void loadLightSources(Vector3f lightPos, Vector3f lightColor) {
		super.loadVector3f("lightPosition", lightPos);
		super.loadVector3f("lightColor", lightColor);
		//super.loadFloat("directionalLight.light.intensity", directionalLight.getIntensity());
	}

	public void loadCameraPosition(Vector3f cameraPos){
		super.loadVector3f("cameraPosition", cameraPos);
	}

	public void loadMoveFactor(float moveFactor){
		super.loadFloat("moveFactor", moveFactor);
	}

	public void connectTextureUnits(){
		super.loadInteger("reflectionTexture", 0);
		super.loadInteger("refractionTexture", 1);
		super.loadInteger("dudvMap", 2);
		super.loadInteger("normalMap", 3);
		super.loadInteger("depthMap", 4);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
