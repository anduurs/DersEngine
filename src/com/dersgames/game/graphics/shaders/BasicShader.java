package com.dersgames.game.graphics.shaders;

public class BasicShader extends Shader{

	public BasicShader() {
		super("basicVert", "basicFrag");
		enable();
		addUniform("textureSampler");
		addUniform("transformationMatrix");
		addUniform("projectionMatrix");
		addUniform("viewMatrix");
		setUniformi("textureSampler", 0);
		disable();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

}
