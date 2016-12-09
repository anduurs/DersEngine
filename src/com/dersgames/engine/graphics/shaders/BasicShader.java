package com.dersgames.engine.graphics.shaders;

import com.dersgames.engine.core.CameraOld;
import com.dersgames.engine.core.Matrix4f;
import com.dersgames.engine.core.Vector2f;

public class BasicShader extends Shader{

	public BasicShader() {
		super("basicVertexShader", "basicFragmentShader");
		
		addUniform("textureSampler");
		addUniform("transformationMatrix");
		addUniform("projectionMatrix");
		addUniform("viewMatrix");
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
	
	public void loadOffset(Vector2f offset){
		loadVector2f("offset", offset);
	}
	
	public void loadViewMatrix(CameraOld camera){
		loadMatrix4f("viewMatrix", camera.getViewMatrix());
	}
	
	public void loadProjectionMatrix(Matrix4f projection){
		loadMatrix4f("projectionMatrix", projection);
	}
	

}
