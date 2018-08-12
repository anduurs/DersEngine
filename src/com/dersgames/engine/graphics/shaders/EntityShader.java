package com.dersgames.engine.graphics.shaders;

public class EntityShader extends PhongShader {
	
	public EntityShader() {
		super("entity/entityVertexShader", "entity/entityFragmentShader");
	}
}
