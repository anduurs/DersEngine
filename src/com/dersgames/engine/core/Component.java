package com.dersgames.engine.core;

import com.dersgames.engine.math.Quaternion;
import com.dersgames.engine.math.Vector3f;

public abstract class Component {
	
	protected Entity m_Entity;
	protected boolean m_Active;
	protected String m_Tag;
	
	public Component(){
		this("Component");
	}
	
	public Component(String tag){
		m_Tag = tag;
		m_Active = true;
		m_Entity = null;
	}
	
	public abstract void init();
	public abstract void update(float dt);
	
	public Entity getEntity() {
		return m_Entity;
	}
	
	public Transform getTransform(){
		return m_Entity.getTransform();
	}
	
	public Vector3f getPosition(){
		return m_Entity.getPosition();
	}
	
	public Quaternion getRotation(){
		return m_Entity.getTransform().getRotation();
	}
	
	public Vector3f getScale(){
		return m_Entity.getTransform().getScale();
	}

	public void setEntity(Entity entity) {
		m_Entity = entity;
	}

	public boolean isActive() {
		return m_Active;
	}
	
	public void setActive(boolean active){
		m_Active = active;
	}

	public String getTag() {
		return m_Tag;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (m_Active ? 1231 : 1237);
		result = prime * result + ((m_Entity == null) ? 0 : m_Entity.hashCode());
		result = prime * result + ((m_Tag == null) ? 0 : m_Tag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Component other = (Component) obj;
		if (m_Active != other.m_Active)
			return false;
		if (m_Entity == null) {
			if (other.m_Entity != null)
				return false;
		} else if (!m_Entity.equals(other.m_Entity))
			return false;
		if (m_Tag == null) {
			if (other.m_Tag != null)
				return false;
		} else if (!m_Tag.equals(other.m_Tag))
			return false;
		return true;
	}


}
