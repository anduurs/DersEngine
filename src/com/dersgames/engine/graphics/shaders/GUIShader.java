package com.dersgames.engine.graphics.shaders;

import com.dersgames.engine.core.Matrix4f;

public class GUIShader extends Shader{

	public GUIShader() {
		super("gui/guiVertexShader", "gui/guiFragmentShader");
		
		addUniform("textureSampler");
		addUniform("modelMatrix");
		addUniform("projectionMatrix");
		
		enable();
		loadInteger("textureSampler", 0);
		disable();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
}
