package com.dersgames.engine.particles;

import com.dersgames.engine.components.Component;
import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.core.Debug;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.graphics.RenderEngine;

public class Particle extends Component{
	
	private Vector3f m_Velocity;
	
	private float m_Rotation;
	private float m_GravityEffect;
	private float m_LifeTime;
	private float m_ElapsedTime = 0;
	
	public Particle(String tag, Vector3f velocity, float rotation, float gravityEffect, 
			float lifeTime){
		
		super(tag);
		
		m_Velocity = velocity;
		m_Rotation = rotation;
		m_GravityEffect = gravityEffect;
		m_LifeTime = lifeTime;
	}
	
	public void update(float dt){
		m_Velocity.y += -50.0f * m_GravityEffect * dt;
		getTransform().setTranslation(getTransform().getPosition().add(m_Velocity.mul(dt)));
		m_ElapsedTime += dt;
		
		if(m_ElapsedTime >= m_LifeTime){
			getEntity().destroy();
		}
	}
	
	public float getRotationAngle(){
		return m_Rotation;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
