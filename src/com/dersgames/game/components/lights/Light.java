package com.dersgames.game.components.lights;

import com.dersgames.game.components.Component;
import com.dersgames.game.core.Vector3f;

public class Light extends Component{
	
	private Vector3f m_Color;
	
	public Light(String tag, Vector3f color) {
		super(tag);
		
		m_Color = color;
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
