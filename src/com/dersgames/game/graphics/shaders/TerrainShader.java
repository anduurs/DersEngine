package com.dersgames.game.graphics.shaders;

import com.dersgames.game.components.lights.Light;
import com.dersgames.game.core.Camera;
import com.dersgames.game.core.Matrix4f;

public class TerrainShader extends Shader{

	public TerrainShader() {
		super("terrainVertexShader", "terrainFragmentShader");
		enable();
		addUniform("textureSampler");
		addUniform("transformationMatrix");
		addUniform("projectionMatrix");
		addUniform("viewMatrix");
		addUniform("lightPosition");
		addUniform("lightColor");
		addUniform("shineDamper");
		addUniform("reflectivity");
		loadInteger("textureSampler", 0);
		disable();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
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
