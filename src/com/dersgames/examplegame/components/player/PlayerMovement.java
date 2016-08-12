package com.dersgames.examplegame.components.player;

import com.dersgames.engine.components.Component;
import com.dersgames.engine.core.Debug;
import com.dersgames.engine.core.Scene;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.terrains.Terrain;

public class PlayerMovement extends Component{
	
	private float m_RunSpeed = 20;
	private float m_RotationSpeed = 100;
	
	private float m_CurrentSpeed = 0.0f;
	private float m_CurrentRotationSpeed = 0.0f;
	private float m_UpwardsSpeed = 0.0f;
	private final float GRAVITY = -100.0f;
	private float m_TerrainHeight = 0;
	
	public boolean strafeLeft = false, strafeRight = false;
	
	public PlayerMovement(){
		super("PlayerMovement");
	}

	@Override
	public void init() {
	}

	@Override
	public void update(float dt) {
		float distance = m_CurrentSpeed * dt;
		
		float deltaX = 0;
		float deltaZ = 0;
		
		float xAmount = 0;
		float zAmount = 0;
		
		if(strafeLeft){
			deltaX = (float)(distance * Math.sin(Math.toRadians(m_Entity.getTransform().getRotation().y - 90)));
			deltaZ = (float)(distance * Math.cos(Math.toRadians(m_Entity.getTransform().getRotation().y - 90)));
			
			xAmount = m_Entity.getPosition().x - deltaX;
			zAmount = m_Entity.getPosition().z + deltaZ;
		}else if(strafeRight){
			deltaX = (float)(distance * Math.sin(Math.toRadians(m_Entity.getTransform().getRotation().y + 90)));
			deltaZ = (float)(distance * Math.cos(Math.toRadians(m_Entity.getTransform().getRotation().y + 90)));
			
			xAmount = m_Entity.getPosition().x + deltaX;
			zAmount = m_Entity.getPosition().z - deltaZ;
		}else{
			deltaX = (float)(distance * Math.sin(Math.toRadians(-m_Entity.getTransform().getRotation().y)));
			deltaZ = (float)(distance * Math.cos(Math.toRadians(-m_Entity.getTransform().getRotation().y)));
			
			xAmount = m_Entity.getPosition().x + deltaX;
			zAmount = m_Entity.getPosition().z + deltaZ;
		}
		
		m_UpwardsSpeed += GRAVITY * dt;
		m_Entity.getPosition().y += m_UpwardsSpeed * dt;
		
		m_Entity.getTransform().translate(xAmount, m_Entity.getPosition().y, zAmount);
		
		Entity e = (Entity)Scene.findEntityByTag("Terrain");
		Terrain terrain = (Terrain) e.findComponentByTag("Terrain");
		m_TerrainHeight = terrain.getHeightOfTerrain(m_Entity.getPosition().x, m_Entity.getPosition().z);
		
		if(m_Entity.getPosition().y < m_TerrainHeight){
			m_UpwardsSpeed = 0;
			m_Entity.getPosition().y = m_TerrainHeight;
		}
	}
	
	public void jump(){
		
	}
	
	public float getRunSpeed() {
		return m_RunSpeed;
	}

	public float getRotationSpeed() {
		return m_RotationSpeed;
	}

	public float getCurrentSpeed() {
		return m_CurrentSpeed;
	}

	public void setCurrentSpeed(float currentSpeed) {
		m_CurrentSpeed = currentSpeed;
	}

	public float getCurrentRotationSpeed() {
		return m_CurrentRotationSpeed;
	}

	public void setCurrentRotationSpeed(float currentRotationSpeed) {
		m_CurrentRotationSpeed = currentRotationSpeed;
	}

	public float getUpwardsSpeed() {
		return m_UpwardsSpeed;
	}

	public void setUpwardsSpeed(float upwardsSpeed) {
		m_UpwardsSpeed = upwardsSpeed;
	}

}
