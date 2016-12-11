package com.dersgames.engine.components.lights;

import com.dersgames.engine.core.Vector3f;

public class PointLight extends Light{
	
	private Vector3f m_Attenuation;

	public PointLight(String tag, Vector3f ambient, Vector3f diffuse, 
			Vector3f specular, Vector3f attenuation) {
		
		super(tag, ambient, diffuse, specular);
		m_Attenuation = attenuation;
	}
	
	public Vector3f getAttenuation() {
		return m_Attenuation;
	}

	public void setAttenuation(Vector3f attenuation) {
		m_Attenuation = attenuation;
	}

}
