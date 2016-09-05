package com.dersgames.engine.components.lights;

import com.dersgames.engine.components.Component;
import com.dersgames.engine.core.Vector3f;

public class Light extends Component{
	
	private Vector3f m_Color;
	private Vector3f m_Attenuation;
	
	public Light(String tag, Vector3f color) {
		super(tag);
		
		m_Color = color;
		m_Attenuation = new Vector3f(1, 0, 0);
	}
	
	public Light(String tag, Vector3f color, Vector3f attenuation) {
		super(tag);
		
		m_Color = color;
		m_Attenuation = attenuation;
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void update(float dt) {
		
	}
	
	public Vector3f getPosition() {
		return m_Entity.getPosition();
	}

	public Vector3f getColor() {
		return m_Color;
	}

	public void setColor(Vector3f color) {
		this.m_Color = color;
	}

	public Vector3f getAttenuation() {
		return m_Attenuation;
	}

	public void setAttenuation(Vector3f attenuation) {
		m_Attenuation = attenuation;
	}

}
