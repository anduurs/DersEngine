package com.dersgames.game.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dersgames.game.graphics.Renderer3D;

public class EntityManager {
	
	private static List<Entity> m_EntityList;
	
	public EntityManager(){
		m_EntityList = new ArrayList<Entity>();
	}
	
	public void addEntity(Entity entity){
		m_EntityList.add(entity);
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
	
	public static Entity findEntityByTag(String tag){
		for(Entity e : getEntities())
			if(e.getTag().equals(tag)) return e;
		return null;
	}
	
	private void refreshEntityList(){
		for(Iterator<Entity> it = m_EntityList.iterator(); it.hasNext();){
			Entity e = it.next();
			if(!e.isAlive()) it.remove();
		}
	}
	
	private static synchronized List<Entity> getEntities(){
		return m_EntityList;
	}

}
