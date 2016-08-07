package com.dersgames.game.events.types;

import com.dersgames.game.events.Event;

public class MouseButtonEvent extends Event {
	
	protected int m_Button;
	protected int x, y;
	
	protected MouseButtonEvent(int button, int x, int y, Type type) {
		super(type);
		m_Button = button;
		this.x = x;
		this.y = y;
	}

	public int getButton() {
		return m_Button;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
