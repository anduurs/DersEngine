package com.dersgames.examplegame.components;

import com.dersgames.engine.components.Component;

public class DragonMovement extends Component{
	
	private float m_Speed;
	
	public DragonMovement(String tag, float speed){
		super(tag);
		m_Speed = speed;
	}

	@Override
	public void init() {}

	@Override
	public void update(float dt) {
		getTransform().rotate(getRotation().getUp(), m_Speed * dt);
	}

}
