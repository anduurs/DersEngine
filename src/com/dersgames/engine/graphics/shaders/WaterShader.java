package com.dersgames.engine.graphics.shaders;

import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.maths.Vector3f;

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
		addUniform("moveFactor");
		addUniform("cameraPosition");
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
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
