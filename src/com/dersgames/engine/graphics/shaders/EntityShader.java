package com.dersgames.engine.graphics.shaders;

import java.util.List;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.lights.Light;
import com.dersgames.engine.core.Matrix4f;
import com.dersgames.engine.core.Vector2f;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.entities.Entity;

public class EntityShader extends Shader{
	
	public static final int MAX_LIGHTS = 4;

	public EntityShader() {
		super("vertexShader", "fragmentShader");
		
		addUniform("textureSampler");
		addUniform("modelMatrix");
		addUniform("viewMatrix");
		addUniform("projectionMatrix");
		addUniform("shininess");
		addUniform("reflectivity");
		addUniform("useFakeLighting");
		addUniform("skyColor");
		addUniform("numOfRows");
		addUniform("offset");
		
		addUniform("lightPosition", MAX_LIGHTS);
		addUniform("lightColor", MAX_LIGHTS);
		addUniform("attenuation", MAX_LIGHTS);
		
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
	
	public void loadNumOfRowsInTextureAtlas(float rows){
		loadFloat("numOfRows", rows);
	}
	
	public void loadTexCoordOffset(Vector2f offset){
		loadVector2f("offset", offset);
	}
	
	public void loadSkyColor(Vector3f skyColor){
		loadVector3f("skyColor", skyColor);
	}
	
	public void loadUseFakeLighting(boolean useFakelighting){
		if(useFakelighting)
			loadFloat("useFakeLighting", 1.0f);
		else loadFloat("useFakeLighting", 0.0f);
	}
	
	public void loadSpecularProperties(float damper, float reflectivity){
		loadFloat("shininess", damper);
		loadFloat("reflectivity", reflectivity);
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
