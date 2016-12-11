package com.dersgames.engine.components.lights;

import com.dersgames.engine.core.Vector3f;

public class DirectionalLight extends Light{

	public DirectionalLight(String tag, Vector3f ambient, Vector3f diffuse, 
			Vector3f specular, float intensity) {
		super(tag, ambient, diffuse, specular, intensity);
		
	}
	
	public Vector3f getDirection(){
		return getRotation().getForward();
	}

}
