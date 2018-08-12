package com.dersgames.examplegame.components;

import com.dersgames.engine.core.Component;
import com.dersgames.engine.math.Vector3f;

public class CrateRotator extends Component{
	
	private float m_Speed;
	
	public CrateRotator(String tag, float speed){
		super(tag);
		m_Speed = speed;
	}

	@Override
	public void init() {}

	@Override
	public void update(float dt) {
		getTransform().rotate(new Vector3f(0,1,-1), m_Speed * dt);
	}

}