package com.dersgames.engine.graphics.shaders;

public class WaterShader extends Shader{

	public WaterShader() {
		super("water/waterVertexShader", "water/waterFragmentShader");
		
		addUniform("modelMatrix");
		addUniform("viewMatrix");
		addUniform("projectionMatrix");

		addUniform("reflectionTexture");
		addUniform("refractionTexture");
	}

	public void connectTextureUnits(){
		super.loadInteger("reflectionTexture", 0);
		super.loadInteger("refractionTexture", 1);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
