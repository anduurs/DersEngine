package com.dersgames.engine.graphics.shaders;

import com.dersgames.engine.components.lights.Light;
import com.dersgames.engine.core.Camera;
import com.dersgames.engine.core.Matrix4f;
import com.dersgames.engine.core.Vector3f;

public class TerrainShader extends Shader{

	public TerrainShader() {
		super("terrainVertexShader", "terrainFragmentShader");
		
		addUniform("transformationMatrix");
		addUniform("projectionMatrix");
		addUniform("viewMatrix");
		
		addUniform("lightPosition");
		addUniform("lightColor");
		addUniform("skyColor");
		
		addUniform("shineDamper");
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
	
	public void loadSkyColor(Vector3f skyColor){
		loadVector3f("skyColor", skyColor);
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
