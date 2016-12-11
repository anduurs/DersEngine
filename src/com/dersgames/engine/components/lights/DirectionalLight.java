package com.dersgames.engine.components.lights;

import com.dersgames.engine.core.Vector3f;

public class DirectionalLight extends Light{

	public DirectionalLight(String tag, Vector3f ambient, Vector3f diffuse, 
			Vector3f specular) {
		super(tag, ambient, diffuse, specular);
		
	}
	
	public Vector3f getDirection(){
		return getRotation().getForward();
	}

}
