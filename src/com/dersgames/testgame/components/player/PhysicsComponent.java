package com.dersgames.testgame.components.player;

import com.dersgames.game.components.Component;
import com.dersgames.game.core.Camera;

public class PhysicsComponent extends Component{
	
	private float m_RunSpeed = 20;
	private float m_RotationSpeed = 100;
	
	public static final float GRAVITY = -50.0f;
	public static final int TERRAIN_HEIGHT = 0;
	
	private boolean m_IsInAir = false;
	
	private float m_CurrentSpeed = 0.0f;
	private float m_CurrentRotationSpeed = 0.0f;
	private float m_UpwardsSpeed = 0.0f;
	
	public PhysicsComponent(String tag){
		super(tag);
	}

	@Override
	public void init() {
		
		
	}

	@Override
	public void update(float dt) {
		float yRotAmount = m_Entity.getTransform().getRotation().y + m_CurrentRotationSpeed * dt;
		m_Entity.getTransform().rotate(0, yRotAmount, 0);
		
		float distance = m_CurrentSpeed * dt;
		
		float deltaX = (float)(distance * Math.sin(Math.toRadians(-m_Entity.getTransform().getRotation().y)));
		float deltaZ = (float)(distance * Math.cos(Math.toRadians(-m_Entity.getTransform().getRotation().y)));
		
		float xAmount = m_Entity.getPosition().x + deltaX;
		float zAmount = m_Entity.getPosition().z + deltaZ;
		
		m_Entity.getTransform().translate(xAmount, 0, zAmount);
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
