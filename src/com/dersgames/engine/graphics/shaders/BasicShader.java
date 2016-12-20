package com.dersgames.engine.graphics.shaders;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.core.Matrix4f;
import com.dersgames.engine.core.Vector2f;
import com.dersgames.engine.entities.Entity;

public class BasicShader extends Shader{

	public BasicShader() {
		super("basicVertexShader", "basicFragmentShader");
		
		addUniform("textureSampler");
		addUniform("modelMatrix");
		addUniform("viewMatrix");
		addUniform("projectionMatrix");
	
		addUniform("numOfRows");
		addUniform("offset");
		
		enable();
		loadInteger("textureSampler", 0);
		disable();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	public void loadNumOfRows(float rows){
		loadFloat("numOfRows", rows);
	}
	
	public void loadTexCoordOffset(Vector2f offset){
		loadVector2f("offset", offset);
	}

}
