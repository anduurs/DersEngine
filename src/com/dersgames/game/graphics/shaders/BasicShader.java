package com.dersgames.game.graphics.shaders;

import com.dersgames.game.components.Transform;

public class BasicShader extends Shader{

	public BasicShader() {
		super("basicVert", "basicFrag");
		
		addUniform("projection_matrix");
		addUniform("sampler");
		
		enable();
		setUniformi("sampler", 0);
		setUniform("projection_matrix", Transform.getOrthoProjection());
		disable();
	}

}
