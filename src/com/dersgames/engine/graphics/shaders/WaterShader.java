package com.dersgames.engine.graphics.shaders;

import com.dersgames.engine.graphics.shaders.Shader;

public class WaterShader extends Shader{

	public WaterShader() {
		super("water/waterVertexShader", "water/waterFragmentShader");
		
		addUniform("modelMatrix");
		addUniform("viewMatrix");
		addUniform("projectionMatrix");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
