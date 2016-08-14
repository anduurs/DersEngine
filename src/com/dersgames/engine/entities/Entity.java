package com.dersgames.engine.entities;

import java.util.ArrayList;
import java.util.List;

import com.dersgames.engine.components.Component;
import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.core.Transform;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.graphics.RenderEngine;

public class Entity {
	
	private List<Component> m_Components;
	private List<Renderable> m_Renderables;
	
	private String m_Tag;
	private Transform m_Transform;
	private boolean m_Alive;
	
	public Entity(){
		this("Entity");
	}
	
	public Entity(String tag){
		this(tag, 0, 0, 0);
	}
	
	public Entity(String tag, float x, float y){
		this(tag, x, y, 0);
	}
	
	public Entity(String tag, float x, float y, float z, float scale){
		this(tag, x, y, z);
		m_Transform.scale(scale, scale, scale);
	}
	
	public Entity(String tag, float x, float y, float z){
		m_Tag = tag;
		m_Alive = true;
		
		m_Components = new ArrayList<Component>();
		m_Renderables = new ArrayList<Renderable>();
		
		m_Transform = new Transform();
		m_Transform.translate(x, y, z);
	}
	
	public Component addComponent(Component component){
		component.setEntity(this);
		
		if(component instanceof Renderable){
			Renderable r = (Renderable) component;
			getRenderables().add(r);
		}
		
		getComponents().add(component);
		return component;
	}
	
	public Component findComponentByTag(String tag){
		for(Component c : getComponents())
			if(c.getTag().equals(tag))
				return c;
		return null;
	}
	
	public void updateComponents(float dt){
		for(Component c : getComponents())
			if(c.isActive())
				c.update(dt);
	}
	
	public void renderComponents(RenderEngine renderer){
		for(Renderable r : getRenderables())
			if(r.isActive())
				r.render(renderer);
	}
	
	public void destroy(){
		m_Alive = false;
	}

	public String getTag() {
		return m_Tag;
	}

	public Transform getTransform() {
		return m_Transform;
	}
	
	public Vector3f getPosition(){
		return m_Transform.getPosition();
	}
	
	public boolean isAlive(){
		return m_Alive;
	}
	
	private synchronized List<Component> getComponents(){
		return m_Components;
	}
	
	private synchronized List<Renderable> getRenderables(){
		return m_Renderables;
	}
}
