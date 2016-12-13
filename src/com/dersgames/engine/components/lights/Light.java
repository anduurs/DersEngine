package com.dersgames.engine.components.lights;

import com.dersgames.engine.components.Component;
import com.dersgames.engine.core.Vector3f;

public class Light extends Component{
	
	protected Vector3f m_Color;
	protected float m_Intensity;
	
	public Light(String tag, Vector3f color, float intensity) {
		super(tag);
		
		m_Color = color;
		m_Intensity = intensity;
	}
	
	@Override
	public void init() {}
	@Override
	public void update(float dt) {}


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
