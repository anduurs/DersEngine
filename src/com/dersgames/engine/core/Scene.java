package com.dersgames.engine.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.lights.DirectionalLight;
import com.dersgames.engine.graphics.lights.PointLight;
import com.dersgames.engine.graphics.lights.SpotLight;
import com.dersgames.engine.math.Vector3f;

public abstract class Scene {
	private String m_SceneName;
	
	private List<Entity> m_Entities;
	
	private List<DirectionalLight> m_DirectionalLights;
	private List<PointLight> m_PointLights;
	private List<SpotLight> m_SpotLights;
	
	private Vector3f m_SceneAmbientLight = new Vector3f(0.05f, 0.05f, 0.05f);
	
	public Scene(String name){
		m_SceneName = name;
		
		m_Entities 			= new ArrayList<>();
		m_PointLights 		= new ArrayList<>();
		m_SpotLights 	    = new ArrayList<>();
		m_DirectionalLights = new ArrayList<>();
	}
	
	public void onSceneLoaded() {
		initEntities();
		initLightSetup();
	}
	
	public void onSceneDestroyed() {
		cleanUp();
	}
	
	public abstract void initEntities();
	public abstract void initLightSetup();
	
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
		m_Entities.add(entity);
	}
	
	public void update(float dt){
		refresh();
		updateEntities(dt);
		updateLights(dt);
	}

	public void render(){
		for(Entity entity : m_Entities) {
			entity.renderComponents();
		}

		RenderEngine.getInstance().render();
	}
	
	public int getEntityCount(){
		return m_Entities.size();
	}
	
	public Entity findEntityByTag(String tag){
		for(Entity entity : m_Entities)
			if(entity.getTag().equals(tag)) 
				return entity;
		return null;
	}
	
	private void updateEntities(float dt) {
		for(Entity entity : m_Entities) {
			entity.updateComponents(dt);
		}
	}
	
	private void updateLights(float dt) {
		getDirectionalLight().updateComponents(dt);
		
		for(PointLight light : getPointLights()) {
			light.updateComponents(dt);
		}

		for(SpotLight light : getSpotLights()) {
			light.updateComponents(dt);
		}	
	}
	
	private void refresh() {
		refreshEntityList();
		refreshDirectionalLightLightList();
		refreshPointLightList();
		refreshSpotLightList();
	}
	
	private void refreshEntityList(){
		for(Iterator<Entity> it = m_Entities.iterator(); it.hasNext();){
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
	
	private void cleanUp() {
		m_Entities.clear();
		m_PointLights.clear();
		m_SpotLights.clear();
		m_DirectionalLights.clear();
		m_SceneAmbientLight = null;
	}
	
	public String getSceneName() {
		return m_SceneName;
	}
	
	public List<PointLight> getPointLights(){
		return m_PointLights;
	}
	
	public List<SpotLight> getSpotLights(){
		return m_SpotLights;
	}
	
	public DirectionalLight getDirectionalLight(){
		return m_DirectionalLights.get(0);
	}
	
	public Vector3f getSceneAmbientLight(){
		return m_SceneAmbientLight;
	}

	public void setSceneAmbientLight(Vector3f sceneAmbientLight) {
		m_SceneAmbientLight = sceneAmbientLight;
	}

}
