package com.dersgames.engine.graphics.water;

import com.dersgames.engine.core.Component;

public class WaterUpdate extends Component {
	
	public static final float TILE_SIZE = 60;

    private final float WAVE_SPEED = 0.03f;
    private float m_MoveFactor = 0;
	     
	public WaterUpdate(String tag){
		super(tag);
	
	}

	@Override
    public void update(float dt){
        m_MoveFactor += WAVE_SPEED * dt;
        m_MoveFactor %= 1;
    }

    public float getMoveFactor(){
        return m_MoveFactor;
    }

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
