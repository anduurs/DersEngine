package com.dersgames.engine.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dersgames.engine.components.lights.Light;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.graphics.RenderEngine;

public class Scene {
	
	private static List<Entity> m_EntityList;
	private List<Light> m_LightSources;
	
	public Scene(){
		m_EntityList = new ArrayList<Entity>();
		m_LightSources = new ArrayList<Light>();
	}
	
	public void addLightSource(Light lightSource){
		m_LightSources.add(lightSource);
		addEntity(lightSource.getEntity());
	}
	
	public void addEntity(Entity entity){
		m_EntityList.add(entity);
	}
	
	public void update(float dt){
		refreshEntityList();
		
		for(Entity entity : getEntities())
			entity.updateComponents(dt);
	}
	
	public void render(RenderEngine renderer){
		for(Entity entity : getEntities())
			entity.renderComponents(renderer);
		
		renderer.render(m_LightSources);
	}
	
	public int getEntityCount(){
		return getEntities().size();
	}
	
	public static Entity findEntityByTag(String tag){
		for(Entity entity : getEntities())
			if(entity.getTag().equals(tag)) 
				return entity;
		return null;
	}
	
	private void refreshEntityList(){
		for(Iterator<Entity> it = m_EntityList.iterator(); it.hasNext();){
			Entity entity = it.next();
			if(!entity.isAlive()) 
				it.remove();
		}
	}
	
	private static synchronized List<Entity> getEntities(){
		return m_EntityList;
	}

}
