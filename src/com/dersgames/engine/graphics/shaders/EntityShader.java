package com.dersgames.engine.graphics.shaders;

import com.dersgames.engine.maths.Vector2f;

public class EntityShader extends PhongShader{
	
	public EntityShader() {
		super("entity/entityVertexShader", "entity/entityFragmentShader");
	}
}
