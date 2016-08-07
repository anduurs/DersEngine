package com.dersgames.game.events.types;

import com.dersgames.game.events.Event;

public class MouseMovedEvent extends Event{
	
	private int x, y;
	private boolean m_Dragged;
	
	public MouseMovedEvent(int x, int y, boolean dragged) {
		super(Event.Type.MOUSE_MOVED);
		this.x = x;
		this.y = y;
		m_Dragged = dragged;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean getDragged() {
		return m_Dragged;
	}

}
