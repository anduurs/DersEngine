package com.dersgames.engine.entities.lights;

import com.dersgames.engine.core.Transform;
import com.dersgames.engine.core.Vector3f;

public class PointLight extends Light{
	
	private Vector3f m_Attenuation;
	private float m_Range;
	public float speed = 1.0f;
	
	public PointLight(String tag, Transform transform, Vector3f color, Vector3f attenuation,
			float intensity, float range) {
		
		super(tag, transform, color, intensity);
		
		m_Attenuation = attenuation;
		m_Range = range;
	}
	
	public PointLight(String tag, Vector3f position, Vector3f color, Vector3f attenuation,
			float intensity, float range) {
		
		super(tag, position, color, intensity);
		
		m_Attenuation = attenuation;
		m_Range = range;
	}
	
	float temp = 0.0f;
	
	public void update(float dt) {
		temp += dt * speed;
		if(speed > 0){
			getTransform().setTranslation(new Vector3f(getTransform().getPosition().x, getTransform().getPosition().y, 
				180.0f * (float)(Math.cos(temp) + 1.0/2.0)+200.0f));
		}
	}
	
	public Vector3f getAttenuation() {
		return m_Attenuation;
	}

	public void setAttenuation(Vector3f attenuation) {
		m_Attenuation = attenuation;
	}

	public float getRange() {
		return m_Range;
	}

	public void setRange(float range) {
		m_Range = range;
	}

}
