package com.dersgames.engine.graphics.shaders;

import java.util.List;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.lights.DirectionalLight;
import com.dersgames.engine.components.lights.PointLight;
import com.dersgames.engine.components.lights.SpotLight;
import com.dersgames.engine.core.Matrix4f;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.entities.Entity;

public class TerrainShader extends Shader{
	
	public static final int MAX_POINT_LIGHTS = 4;
	public static final int MAX_SPOT_LIGHTS = 4;

	public TerrainShader() {
		super("terrainVertexShader", "terrainFragmentShader");
		
		addUniform("modelMatrix");
		addUniform("viewMatrix");
		addUniform("projectionMatrix");
		
		addUniform("skyColor");
		
		addUniform("backgroundTexture");
		addUniform("rTexture");
		addUniform("gTexture");
		addUniform("bTexture");
		addUniform("blendMap");
		
		addUniform("directionalLight.direction");
		addUniform("directionalLight.ambient");
		addUniform("directionalLight.diffuse");
		addUniform("directionalLight.specular");
		addUniform("directionalLight.intensity");
		
		for(int i = 0; i < MAX_POINT_LIGHTS; i++){
			addUniform("pointLights["+i+"].position");
			addUniform("pointLights["+i+"].attenuation");
			addUniform("pointLights["+i+"].ambient");
			addUniform("pointLights["+i+"].diffuse");
			addUniform("pointLights["+i+"].specular");
			addUniform("pointLights["+i+"].intensity");
			addUniform("pointLights["+i+"].range");
		}
		
		for(int i = 0; i < MAX_SPOT_LIGHTS; i++){
			addUniform("spotLights["+i+"].pointLight.position");
			addUniform("spotLights["+i+"].pointLight.attenuation");
			addUniform("spotLights["+i+"].pointLight.ambient");
			addUniform("spotLights["+i+"].pointLight.diffuse");
			addUniform("spotLights["+i+"].pointLight.specular");
			addUniform("spotLights["+i+"].pointLight.intensity");
			addUniform("spotLights["+i+"].pointLight.range");
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
	
	public void connectTextureUnits(){
		loadInteger("backgroundTexture", 0);
		loadInteger("rTexture", 1);
		loadInteger("gTexture", 2);
		loadInteger("bTexture", 3);
		loadInteger("blendMap", 4);
	}
	
	public void loadLightSources(DirectionalLight directionalLight, List<PointLight> pointLights, List<SpotLight> spotLights){
		super.loadVector3f("directionalLight.direction", directionalLight.getDirection());
		super.loadVector3f("directionalLight.ambient", directionalLight.getAmbientLight());
		super.loadVector3f("directionalLight.diffuse", directionalLight.getDiffuseLight());
		super.loadVector3f("directionalLight.specular", directionalLight.getSpecularLight());
		super.loadFloat("directionalLight.intensity", directionalLight.getIntensity());
		
		for(int i = 0; i < MAX_POINT_LIGHTS; i++){
			if(i < pointLights.size() && pointLights.size() != 0){
				super.loadVector3f("pointLights["+i+"].position", pointLights.get(i).getPosition());
				super.loadVector3f("pointLights["+i+"].attenuation", pointLights.get(i).getAttenuation());
				super.loadVector3f("pointLights["+i+"].ambient", pointLights.get(i).getAmbientLight());
				super.loadVector3f("pointLights["+i+"].diffuse", pointLights.get(i).getDiffuseLight());
				super.loadVector3f("pointLights["+i+"].specular", pointLights.get(i).getSpecularLight());
				
				super.loadFloat("pointLights["+i+"].intensity", pointLights.get(i).getIntensity());
				super.loadFloat("pointLights["+i+"].range", pointLights.get(i).getRange());
			}
		}
		
		for(int i = 0; i < MAX_SPOT_LIGHTS; i++){
			if(i < spotLights.size() && spotLights.size() != 0){
				super.loadVector3f("spotLights["+i+"].pointLight.position", spotLights.get(i).getPosition());
				super.loadVector3f("spotLights["+i+"].pointLight.attenuation", spotLights.get(i).getAttenuation());
				super.loadVector3f("spotLights["+i+"].pointLight.ambient", spotLights.get(i).getAmbientLight());
				super.loadVector3f("spotLights["+i+"].pointLight.diffuse", spotLights.get(i).getDiffuseLight());
				super.loadVector3f("spotLights["+i+"].pointLight.specular", spotLights.get(i).getSpecularLight());
				
				super.loadFloat("spotLights["+i+"].pointLight.intensity", spotLights.get(i).getIntensity());
				super.loadFloat("spotLights["+i+"].pointLight.range", spotLights.get(i).getRange());
				
				super.loadVector3f("spotLights["+i+"].direction", spotLights.get(i).getDirection());
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
