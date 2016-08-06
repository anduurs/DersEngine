package com.dersgames.game.entities;

import java.util.ArrayList;
import java.util.List;

import com.dersgames.game.graphics.Renderer3D;

public class EntityManager {
	
	private List<Entity> m_EntityList;
	
	public EntityManager(){
		m_EntityList = new ArrayList<Entity>();
	}
	
	public void addEntity(Entity entity){
		getEntities().add(entity);
	}
	
	public void update(float dt){
		refreshEntityList();
		for(Entity e : getEntities())
			e.updateComponents(dt);
	}
	
	public void render(Renderer3D renderer){
		for(Entity e : getEntities())
			e.renderComponents(renderer);
	}
	
	public int getEntityCount(){
		return getEntities().size();
	}
	
	private void refreshEntityList(){
		for(Entity e : getEntities())
			if(!e.isAlive()) getEntities().remove(e);
	}
	
	private synchronized List<Entity> getEntities(){
		return m_EntityList;
	}

}
