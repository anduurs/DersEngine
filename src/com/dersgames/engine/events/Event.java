package com.dersgames.engine.events;

public class Event {
	
	public enum Type {
		MOUSE_PRESSED,
		MOUSE_RELEASED,
		MOUSE_MOVED,
		KEY_PRESSED,
		KEY_RELEASED
	}
	
	private Type m_Type;
	boolean handled;
	
	protected Event(Type type) {
		m_Type = type;
	}
	
	public Type getType() {
		return m_Type;
	}
}
