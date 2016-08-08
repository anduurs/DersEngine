package com.dersgames.game.components;

import com.dersgames.game.utils.Randomizer;

public class MovementComponent extends Component{
	
	float tmp = 0;
	int timer = 0;
	
	public MovementComponent(String tag){
		super(tag);
		
	}

	@Override
	public void update(float dt) {
//		timer++;
//		if(timer >= 60 * 5){
//			int rnd = Randomizer.getInt(1, 100);
//			
//			if(rnd >= 1 && rnd <= 30)
//				m_Entity.destroy();
//			
//			timer = 0;
//		}
		tmp += dt * 10.0f;
		float sinTmp = (float) Math.sin(tmp);
		//m_Entity.getTransform().translate(sinTmp, m_Entity.getTransform().getPosition().y, m_Entity.getTransform().getPosition().z);
		m_Entity.getTransform().rotate(0, tmp,  0);
	}

}
