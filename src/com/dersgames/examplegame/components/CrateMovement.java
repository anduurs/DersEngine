package com.dersgames.examplegame.components;

import com.dersgames.engine.components.Component;

public class CrateMovement extends Component{
	
	private float m_Speed;
	
	public CrateMovement(String tag, float speed){
		super(tag);
		m_Speed = speed;
	}

	@Override
	public void init() {}

	@Override
	public void update(float dt) {
		getTransform().rotate(getRotation().getUp(), m_Speed * dt);
		getTransform().rotate(getRotation().getForward(), m_Speed * dt);
	}

}