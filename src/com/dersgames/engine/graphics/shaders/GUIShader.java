package com.dersgames.engine.graphics.shaders;

import com.dersgames.engine.core.Matrix4f;
import com.dersgames.engine.core.Vector3f;

public class GUIShader extends Shader{

	public GUIShader() {
		super("gui/guiVertexShader", "gui/guiFragmentShader");
		
		addUniform("textureSampler");
		addUniform("color");
		addUniform("usingColor");
		addUniform("modelMatrix");
		
		enable();
		loadInteger("textureSampler", 0);
		disable();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	

	public void loadModelMatrix(Matrix4f matrix){
		loadMatrix4f("modelMatrix", matrix);
	}
	
	public void loadColor(Vector3f color){
		loadVector3f("color", color);
	}
	
	public void loadUsingColor(boolean usingColor){
		if(usingColor)
			loadFloat("usingColor", 1.0f);
		else loadFloat("usingColor", 0.0f);
	}
	
}
