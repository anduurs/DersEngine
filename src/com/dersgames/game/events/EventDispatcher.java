package com.dersgames.game.events;

public class EventDispatcher {
	
	private Event m_Event;
	
	public EventDispatcher(Event event) {
		m_Event = event;
	}
	
	public void dispatch(Event.Type type, EventHandler handler) {
		if(m_Event.handled) return;
		
		if(m_Event.getType() == type)
			m_Event.handled = handler.onEvent(m_Event);
	}
}
