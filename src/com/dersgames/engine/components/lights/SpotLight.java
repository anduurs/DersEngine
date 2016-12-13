package com.dersgames.engine.components.lights;

import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.graphics.RenderEngine;

public class SpotLight extends PointLight{
	
	private float m_CutOffAngle;

	public SpotLight(String tag, Vector3f color, Vector3f attenuation,
			float intensity, float range, float cutOffAngle) {
		super(tag, color, attenuation, intensity, range);
		
		m_CutOffAngle = cutOffAngle;
	}
	
	public void update(float dt) {
		getTransform().setTranslation(RenderEngine.getCamera().getPosition());
		getTransform().setRotation((RenderEngine.getCamera().getRotation()));
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
