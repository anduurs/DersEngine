package com.dersgames.engine.graphics.lights;

import com.dersgames.engine.core.Transform;
import com.dersgames.engine.math.Vector3f;

public class SpotLight extends PointLight{
	
	private float m_CutOffAngle;

	public SpotLight(String tag, Transform transform, Vector3f color, Vector3f attenuation,
			float intensity, float range, float cutOffAngle) {
		super(tag, transform, color, attenuation, intensity, range);
		
		m_CutOffAngle = cutOffAngle;
	}
	
	public Vector3f getDirection(){
		return getTransform().getRotation().getForward();
	}

	public float getCutOffAngle() {
		return m_CutOffAngle;
	}

	public void setCutOffAngle(float cutOffAngle) {
		m_CutOffAngle = cutOffAngle;
	}

}
