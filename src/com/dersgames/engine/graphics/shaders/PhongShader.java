package com.dersgames.engine.graphics.shaders;

import java.util.List;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.lights.DirectionalLight;
import com.dersgames.engine.components.lights.PointLight;
import com.dersgames.engine.components.lights.SpotLight;
import com.dersgames.engine.core.Matrix4f;
import com.dersgames.engine.core.Scene;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.entities.Entity;

public abstract class PhongShader extends Shader{
	
	public static final int MAX_POINT_LIGHTS = 100;
	public static final int MAX_SPOT_LIGHTS = 4;

	public PhongShader(String vertShader, String fragShader) {
		super(vertShader, fragShader);
		
		addUniform("modelMatrix");
		addUniform("viewMatrix");
		addUniform("projectionMatrix");
		
		addUniform("skyColor");
		addUniform("ambientLight");
		
		addUniform("directionalLight.direction");
		addUniform("directionalLight.light.color");
		addUniform("directionalLight.light.intensity");
		
		for(int i = 0; i < MAX_POINT_LIGHTS; i++){
			addUniform("pointLights["+i+"].position");
			addUniform("pointLights["+i+"].attenuation");
			addUniform("pointLights["+i+"].range");
			addUniform("pointLights["+i+"].light.color");
			addUniform("pointLights["+i+"].light.intensity");
		}
		
		for(int i = 0; i < MAX_SPOT_LIGHTS; i++){
			addUniform("spotLights["+i+"].pointLight.position");
			addUniform("spotLights["+i+"].pointLight.attenuation");
			addUniform("spotLights["+i+"].pointLight.range");
			addUniform("spotLights["+i+"].pointLight.light.color");
			addUniform("spotLights["+i+"].pointLight.light.intensity");
			addUniform("spotLights["+i+"].direction");
			addUniform("spotLights["+i+"].cutOffAngle");
		}
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}
	
	public void loadLightSources(DirectionalLight directionalLight, List<PointLight> pointLights, List<SpotLight> spotLights){
		super.loadVector3f("ambientLight", Scene.getSceneAmbientLight());
		
		super.loadVector3f("directionalLight.direction", directionalLight.getDirection());
		super.loadVector3f("directionalLight.light.color", directionalLight.getLightColor());
		super.loadFloat("directionalLight.light.intensity", directionalLight.getIntensity());
		
		for(int i = 0; i < MAX_POINT_LIGHTS; i++){
			if(i < pointLights.size() && pointLights.size() != 0){
				super.loadVector3f("pointLights["+i+"].position", pointLights.get(i).getPosition());
				super.loadVector3f("pointLights["+i+"].attenuation", pointLights.get(i).getAttenuation());
				super.loadVector3f("pointLights["+i+"].light.color", pointLights.get(i).getLightColor());
				
				super.loadFloat("pointLights["+i+"].light.intensity", pointLights.get(i).getIntensity());
				super.loadFloat("pointLights["+i+"].range", pointLights.get(i).getRange());
			}
		}
		
		for(int i = 0; i < MAX_SPOT_LIGHTS; i++){
			if(i < spotLights.size() && spotLights.size() != 0){
				super.loadVector3f("spotLights["+i+"].pointLight.position", spotLights.get(i).getPosition());
				super.loadVector3f("spotLights["+i+"].pointLight.attenuation", spotLights.get(i).getAttenuation());
				super.loadVector3f("spotLights["+i+"].pointLight.light.color", spotLights.get(i).getLightColor());
				super.loadVector3f("spotLights["+i+"].direction", spotLights.get(i).getDirection());
				
				super.loadFloat("spotLights["+i+"].pointLight.light.intensity", spotLights.get(i).getIntensity());
				super.loadFloat("spotLights["+i+"].pointLight.range", spotLights.get(i).getRange());
				super.loadFloat("spotLights["+i+"].cutOffAngle", spotLights.get(i).getCutOffAngle());
			}
		}
	}
	
	public void loadSkyColor(Vector3f skyColor){
		loadVector3f("skyColor", skyColor);
	}
	
	public void loadModelMatrix(Entity entity){
		loadMatrix4f("modelMatrix", entity.getTransform().getModelMatrix());
	}
	
	public void loadViewMatrix(Camera camera){
		loadMatrix4f("viewMatrix", camera.getViewMatrix());
	}
	
	public void loadProjectionMatrix(Matrix4f projection){
		loadMatrix4f("projectionMatrix", projection);
	}
	
}
