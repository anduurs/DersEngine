package com.dersgames.examplegame.components.player;

import com.dersgames.engine.components.Component;

public class MovementComponent extends Component{
	
	private float m_RotationSpeed;
	
	public MovementComponent(String tag, float rotSpeed){
		super(tag);
		m_RotationSpeed = rotSpeed;
	}

	@Override
	public void init() {}

	@Override
	public void update(float dt) {
		getTransform().rotate(getRotation().getUp(), dt * m_RotationSpeed);
	}

}
