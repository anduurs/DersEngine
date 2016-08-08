package com.dersgames.game.components.lights;

import com.dersgames.game.components.Component;
import com.dersgames.game.core.Vector3f;

public class BaseLight extends Component{
	
	private Vector3f m_Color;
	private float m_Intensity;
	
	public BaseLight(String tag, Vector3f color, float intensity) {
		super(tag);
		
		m_Color = color;
		m_Intensity = intensity;
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

}
