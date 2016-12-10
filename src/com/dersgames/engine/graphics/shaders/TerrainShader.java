package com.dersgames.engine.graphics.shaders;

import java.util.List;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.lights.Light;
import com.dersgames.engine.core.Matrix4f;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.entities.Entity;

public class TerrainShader extends Shader{
	
	public static final int MAX_LIGHTS = 4;

	public TerrainShader() {
		super("terrainVertexShader", "terrainFragmentShader");
		
		addUniform("modelMatrix");
		addUniform("viewMatrix");
		addUniform("projectionMatrix");
		
		addUniform("lightPosition", MAX_LIGHTS);
		addUniform("lightColor", MAX_LIGHTS);
		addUniform("attenuation", MAX_LIGHTS);
		addUniform("skyColor");
		
		addUniform("shininess");
		addUniform("reflectivity");
		
		addUniform("backgroundTexture");
		addUniform("rTexture");
		addUniform("gTexture");
		addUniform("bTexture");
		addUniform("blendMap");
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
	
	public void loadLightSources(List<Light> lights){
		for(int i = 0; i < MAX_LIGHTS; i++){
			if(i < lights.size()){
				super.loadVector3f("lightPosition"+i, lights.get(i).getPosition());
				super.loadVector3f("lightColor"+i, lights.get(i).getColor());
				super.loadVector3f("attenuation"+i, lights.get(i).getAttenuation());
			}else{
				super.loadVector3f("lightPosition"+i, new Vector3f(0,0,0));
				super.loadVector3f("lightColor"+i, new Vector3f(0,0,0));
				super.loadVector3f("attenuation"+i, new Vector3f(1,0,0));
			}
		}
	}
	
	public void loadSkyColor(Vector3f skyColor){
		loadVector3f("skyColor", skyColor);
	}
	
	public void loadSpecularVariables(float damper, float reflectivity){
		loadFloat("shininess", damper);
		loadFloat("reflectivity", reflectivity);
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
