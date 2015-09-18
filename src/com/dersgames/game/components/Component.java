package com.dersgames.game.components;

import com.dersgames.game.core.GameObject;

public abstract class Component {
	
	protected GameObject m_GameObject;
	protected boolean m_Enabled;
	protected String m_Tag;
	
	public Component(){
		this("Component");
	}
	
	public Component(String tag){
		m_Tag = tag;
		m_Enabled = true;
		m_GameObject = null;
	}
	
	public void init(){}
	public void update(float dt){}
	
	public GameObject getGameObject() {
		return m_GameObject;
	}

	public void setGameObject(GameObject gameObject) {
		m_GameObject = gameObject;
	}

	public boolean isEnabled() {
		return m_Enabled;
	}
	
	public void enable(){
		m_Enabled = true;
	}
	
	public void disable(){
		m_Enabled = false;
	}

	public String getTag() {
		return m_Tag;
	}


}
