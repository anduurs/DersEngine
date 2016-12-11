package com.dersgames.engine.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dersgames.engine.components.lights.DirectionalLight;
import com.dersgames.engine.components.lights.PointLight;
import com.dersgames.engine.components.lights.SpotLight;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.graphics.RenderEngine;

public class Scene {
	
	private static List<Entity> m_EntityList;
	private DirectionalLight m_Sun;
	private List<PointLight> m_PointLights;
	private List<SpotLight> m_SpotLights;
	
	public Scene(){
		m_EntityList = new ArrayList<Entity>();
		m_PointLights = new ArrayList<PointLight>();
		m_SpotLights = new ArrayList<SpotLight>();
	}
	
	public void addPointLight(PointLight pointLight){
		m_PointLights.add(pointLight);
		addEntity(pointLight.getEntity());
	}
	
	public void addSpotLight(SpotLight spotLight){
		m_SpotLights.add(spotLight);
		addEntity(spotLight.getEntity());
	}
	
	public void addDirectionalLight(DirectionalLight directionalLight){
		m_Sun = directionalLight;
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
		
		renderer.render(m_Sun, m_PointLights, m_SpotLights);
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
