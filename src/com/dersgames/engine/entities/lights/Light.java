package com.dersgames.engine.entities.lights;

import com.dersgames.engine.core.Transform;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.maths.Vector3f;

public class Light extends Entity{
	
	protected Vector3f m_Color;
	protected float m_Intensity;
	
	public Light(String tag, Vector3f position, Vector3f color, float intensity) {
		super(tag, position.x, position.y, position.z);
		
		m_Color = color;
		m_Intensity = intensity;
	}
	
	public Light(String tag, Transform transform, Vector3f color, float intensity) {
		super(tag, transform);
		
		m_Color = color;
		m_Intensity = intensity;
	}
	
	public Light(String tag, Vector3f color, float intensity) {
		super(tag);
		
		m_Color = color;
		m_Intensity = intensity;
	}
	
	public Vector3f getLightColor() {
		return m_Color;
	}

	public void setLightColor(Vector3f color) {
		m_Color = color;
	}

	public float getIntensity() {
		return m_Intensity;
	}

	public void setIntensity(float intensity) {
		m_Intensity = intensity;
	}

}
