package com.dersgames.engine.graphics.lights;

import com.dersgames.engine.core.Transform;
import com.dersgames.engine.math.Vector3f;

public class DirectionalLight extends Light{

	public DirectionalLight(String tag, Transform transform, Vector3f color, float intensity) {
		super(tag,transform, color, intensity);
	}
	
	public Vector3f getDirection(){
		return getTransform().getRotation().getForward();
	}


}
