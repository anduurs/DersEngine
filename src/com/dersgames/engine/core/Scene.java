package com.dersgames.engine.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.entities.lights.DirectionalLight;
import com.dersgames.engine.entities.lights.PointLight;
import com.dersgames.engine.entities.lights.SpotLight;
import com.dersgames.engine.graphics.RenderEngine;

public class Scene {
	
	private static List<Entity> m_EntityList;
	private static List<Entity> m_Particles;
	
	private static List<DirectionalLight> m_DirectionalLights;
	private static List<PointLight> m_PointLights;
	private static List<SpotLight> m_SpotLights;
	
	private static Vector3f m_SceneAmbientLight = new Vector3f(0.05f, 0.05f, 0.05f);
	
	public Scene(){
		m_EntityList = new ArrayList<>();
		m_PointLights = new ArrayList<>();
		m_SpotLights = new ArrayList<>();
		m_DirectionalLights = new ArrayList<>();
		m_Particles = new ArrayList<>();
	}
	
	public void addDirectionalLight(DirectionalLight directionalLight){
		m_DirectionalLights.add(directionalLight);
	}
	
	public void addPointLight(PointLight pointLight){
		m_PointLights.add(pointLight);
	}
	
	public void addSpotLight(SpotLight spotLight){
		m_SpotLights.add(spotLight);
	}
	
	public void addEntity(Entity entity){
		m_EntityList.add(entity);
	}
	
	public void addParticle(Entity entity){
		m_Particles.add(entity);
	}
	
	public void update(float dt){
		refreshParticles();
		refreshEntityList();
		refreshDirectionalLightLightList();
		refreshPointLightList();
		refreshSpotLightList();
		
		for(Entity entity : getEntities())
			entity.updateComponents(dt);
		
		for(Entity entity : getParticles())
			entity.updateComponents(dt);

		getDirectionalLight().updateComponents(dt);
		
		for(PointLight light : getPointLights())
			light.updateComponents(dt);
		
		for(SpotLight light : getSpotLights())
			light.updateComponents(dt);
	}

	public void render(RenderEngine renderer){
		for(Entity entity : getEntities())
			entity.renderComponents(renderer);
		
		renderer.render();
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
			if(!entity.isAlive()){ 
				it.remove();
			}
		}
	}
	
	private void refreshParticles(){
		for(Iterator<Entity> it = m_Particles.iterator(); it.hasNext();){
			Entity entity = it.next();
			if(!entity.isAlive()){ 
				it.remove();
			}
		}
	}
	
	private void refreshDirectionalLightLightList(){
		for(Iterator<DirectionalLight> it = m_DirectionalLights.iterator(); it.hasNext();){
			Entity entity = it.next();
			if(!entity.isAlive()){ 
				it.remove();
			}
		}
	}
	
	private void refreshPointLightList(){
		for(Iterator<PointLight> it = m_PointLights.iterator(); it.hasNext();){
			Entity entity = it.next();
			if(!entity.isAlive()){ 
				it.remove();
			}
		}
	}
	
	private void refreshSpotLightList(){
		for(Iterator<SpotLight> it = m_SpotLights.iterator(); it.hasNext();){
			Entity entity = it.next();
			if(!entity.isAlive()){ 
				it.remove();
			}
		}
	}
	
	private static synchronized List<Entity> getEntities(){
		return m_EntityList;
	}
	
	public static synchronized List<Entity> getParticles() {
		return m_Particles;
	}
	
	public static synchronized List<PointLight> getPointLights(){
		return m_PointLights;
	}
	
	public static synchronized List<SpotLight> getSpotLights(){
		return m_SpotLights;
	}
	
	public static synchronized DirectionalLight getDirectionalLight(){
		return m_DirectionalLights.get(0);
	}
	
	public static Vector3f getSceneAmbientLight(){
		return m_SceneAmbientLight;
	}

	public static void setSceneAmbientLight(Vector3f sceneAmbientLight) {
		m_SceneAmbientLight = sceneAmbientLight;
	}

}
