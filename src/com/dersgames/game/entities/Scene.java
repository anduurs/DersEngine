package com.dersgames.game.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dersgames.game.components.lights.Light;
import com.dersgames.game.core.Camera;
import com.dersgames.game.graphics.Renderer3D;

public class Scene {
	
	private static List<Entity> m_EntityList;
	private Camera m_Camera;
	private Light m_Sun;
	
	public Scene(){
		m_EntityList = new ArrayList<Entity>();
	}
	
	public void addCameraToScene(Camera camera){
		m_Camera = camera;
	}
	
	public void addSunToScene(Light sun){
		m_Sun = sun;
	}
	
	public void addEntity(Entity entity){
		m_EntityList.add(entity);
	}
	
	public void update(float dt){
		m_Camera.update(dt);
		refreshEntityList();
		for(Entity e : getEntities())
			e.updateComponents(dt);
	}
	
	public void render(Renderer3D renderer){
		for(Entity e : getEntities())
			e.renderComponents(renderer);
		
		renderer.render(m_Sun, m_Camera);
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
