package com.dersgames.engine.events.types;

import com.dersgames.engine.events.Event;

public class MousePressedEvent extends MouseButtonEvent {
	
	public MousePressedEvent(int button, int x, int y) {
		super(button, x, y, Event.Type.MOUSE_PRESSED);
	}

}
