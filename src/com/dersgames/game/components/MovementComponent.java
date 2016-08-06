package com.dersgames.game.components;

public class MovementComponent extends Component{
	
	float tmp = 0;
	
	public MovementComponent(String tag){
		super(tag);
	}

	@Override
	public void update(float dt) {
		if(tmp >= 7000) tmp = 0;
		else tmp += dt;
		
		float sinTmp = (float) Math.sin(tmp);
		
		m_Entity.getTransform().translate(sinTmp, 0, 5.0f);
		m_Entity.getTransform().rotate(0, sinTmp * 180.0f,  0);
	}

}
