package com.dersgames.game.graphics.shaders;

import com.dersgames.game.components.lights.BaseLight;

public class BasicShader extends Shader{

	public BasicShader() {
		super("basicVert", "basicFrag");
		enable();
		addUniform("textureSampler");
		addUniform("transformationMatrix");
		addUniform("projectionMatrix");
		addUniform("viewMatrix");
		addUniform("lightPosition");
		addUniform("lightColor");
		setUniformi("textureSampler", 0);
		disable();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}
	
	public void loadLightSource(BaseLight light){
		setUniformf("lightPosition", light.getPosition());
		setUniformf("lightColor", light.getColor());
	}

}
