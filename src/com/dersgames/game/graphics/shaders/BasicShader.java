package com.dersgames.game.graphics.shaders;

import com.dersgames.game.components.Transform;

public class BasicShader extends Shader{

	public BasicShader() {
		super("basicVert", "basicFrag");
		
		addUniform("projection_matrix");
		addUniform("model_matrix");
		addUniform("view_matrix");
		addUniform("is_Static");
		
		addUniform("sampler");
		
		enable();
		setUniformi("sampler", 0);
		
		disable();
	}

}
