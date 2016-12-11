package com.dersgames.engine.components.lights;

import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.utils.Randomizer;

public class PointLight extends Light{
	
	private Vector3f m_Attenuation;
	public float speed = 1.0f;
	

	public PointLight(String tag, Vector3f ambient, Vector3f diffuse, 
			Vector3f specular, Vector3f attenuation, float intensity) {
		
		super(tag, ambient, diffuse, specular, intensity);
		m_Attenuation = attenuation;
	}
	
	float temp = 0.0f;
	
	public void update(float dt) {
		temp += dt * speed;
		getTransform().setTranslationVector(new Vector3f(getTransform().getPosition().x, getTransform().getPosition().y, 
				180.0f * (float)(Math.cos(temp) + 1.0/2.0)+200.0f));
	}
	
	public Vector3f getAttenuation() {
		return m_Attenuation;
	}

	public void setAttenuation(Vector3f attenuation) {
		m_Attenuation = attenuation;
	}

}
