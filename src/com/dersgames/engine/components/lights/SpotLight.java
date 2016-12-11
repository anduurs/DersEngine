package com.dersgames.engine.components.lights;

import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.graphics.RenderEngine;

public class SpotLight extends PointLight{
	
	private float m_CutOffAngle;

	public SpotLight(String tag, Vector3f ambient, Vector3f diffuse, Vector3f specular, Vector3f attenuation,
			float intensity, float range, float cutOffAngle) {
		super(tag, ambient, diffuse, specular, attenuation, intensity, range);
		
		m_CutOffAngle = cutOffAngle;
	}
	
	public void update(float dt) {
//		getTransform().setTranslationVector(RenderEngine.getCamera().getPosition());
//		getTransform().getRotation().getForward().set((RenderEngine.getCamera().getRotation().getForward()));
	}
	
	public Vector3f getDirection(){
		return getRotation().getForward();
	}

	public float getCutOffAngle() {
		return m_CutOffAngle;
	}

	public void setCutOffAngle(float cutOffAngle) {
		m_CutOffAngle = cutOffAngle;
	}

}
