package com.dersgames.engine.graphics.shaders;

import java.util.List;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.lights.DirectionalLight;
import com.dersgames.engine.components.lights.PointLight;
import com.dersgames.engine.core.Matrix4f;
import com.dersgames.engine.core.Vector2f;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.entities.Entity;

public class EntityShader extends Shader{
	
	public static final int MAX_POINT_LIGHTS = 4;

	public EntityShader() {
		super("vertexShader", "fragmentShader");
		
		addUniform("textureSampler");
		
		addUniform("modelMatrix");
		addUniform("viewMatrix");
		addUniform("projectionMatrix");
		
		addUniform("skyColor");
		
		addUniform("offset");
		
		addUniform("directionalLight.direction");
		addUniform("directionalLight.ambient");
		addUniform("directionalLight.diffuse");
		addUniform("directionalLight.specular");
		
		for(int i = 0; i < MAX_POINT_LIGHTS; i++){
			addUniform("pointLights["+i+"].position");
			addUniform("pointLights["+i+"].attenuation");
			addUniform("pointLights["+i+"].ambient");
			addUniform("pointLights["+i+"].diffuse");
			addUniform("pointLights["+i+"].specular");
			addUniform("pointLights["+i+"].intensity");
		}
		
		enable();
		loadInteger("textureSampler", 0);
		disable();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}
	
	public void loadTexCoordOffset(Vector2f offset){
		loadVector2f("offset", offset);
	}
	
	public void loadSkyColor(Vector3f skyColor){
		loadVector3f("skyColor", skyColor);
	}
	
	public void loadLightSources(DirectionalLight directionalLight, List<PointLight> pointLights){
		super.loadVector3f("directionalLight.direction", directionalLight.getDirection());
		super.loadVector3f("directionalLight.ambient", directionalLight.getAmbientLight());
		super.loadVector3f("directionalLight.diffuse", directionalLight.getDiffuseLight());
		super.loadVector3f("directionalLight.specular", directionalLight.getSpecularLight());
		
		for(int i = 0; i < MAX_POINT_LIGHTS; i++){
			super.loadVector3f("pointLights["+i+"].position", pointLights.get(i).getPosition());
			super.loadVector3f("pointLights["+i+"].attenuation", pointLights.get(i).getAttenuation());
			super.loadVector3f("pointLights["+i+"].ambient", pointLights.get(i).getAmbientLight());
			super.loadVector3f("pointLights["+i+"].diffuse", pointLights.get(i).getDiffuseLight());
			super.loadVector3f("pointLights["+i+"].specular", pointLights.get(i).getSpecularLight());
			super.loadFloat("pointLights["+i+"].intensity", pointLights.get(i).getIntensity());
		}
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
