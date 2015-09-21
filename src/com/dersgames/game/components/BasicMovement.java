package com.dersgames.game.components;

import com.dersgames.game.graphics.Window;

public class BasicMovement extends GameComponent{
	
	private float m_Speed;
	
	public BasicMovement(float speed){
		super("BasicMovement");
		m_Speed = speed;
	}
	
	public void update(float dt){
		float x = m_GameObject.getTransform().getPosition().x;
		float y = m_GameObject.getTransform().getPosition().y;
		
//		if(x >= Window.getWidth())
//			m_GameObject.destroy();
		
		x += m_Speed;
		
		m_GameObject.getTransform().translate(x, y, 0);
	}

}
