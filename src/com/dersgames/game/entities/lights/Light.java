package com.dersgames.game.entities.lights;

import com.dersgames.game.core.Vector3f;
import com.dersgames.game.entities.Entity;

public class Light extends Entity{
	
	private Vector3f m_Position;
	private Vector3f m_Color;
	
	public Light(Vector3f position, Vector3f color) {
		super("BaseLight", position.x, position.y, position.z);
		
		m_Position = position;
		m_Color = color;
	}
	
	public Vector3f getPosition() {
		return m_Position;
	}

	public void setPosition(Vector3f position) {
		this.m_Position = position;
	}

	public Vector3f getColor() {
		return m_Color;
	}

	public void setColor(Vector3f color) {
		this.m_Color = color;
	}
	
}
