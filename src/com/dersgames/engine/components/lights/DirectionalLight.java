package com.dersgames.engine.components.lights;

import com.dersgames.engine.core.Vector3f;

public class DirectionalLight extends Light{

	public DirectionalLight(String tag, Vector3f color, float intensity) {
		super(tag, color, intensity);
	}
	
	public Vector3f getDirection(){
		return getRotation().getForward();
	}

}
