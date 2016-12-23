package com.dersgames.engine.entities.lights;

import com.dersgames.engine.core.Transform;
import com.dersgames.engine.core.Vector3f;

public class DirectionalLight extends Light{

	public DirectionalLight(String tag, Transform transform, Vector3f color, float intensity) {
		super(tag,transform, color, intensity);
	}
	
	public Vector3f getDirection(){
		return getTransform().getRotation().getForward();
	}

}
