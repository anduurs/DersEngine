package com.dersgames.engine.graphics.water;

import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.graphics.RenderEngine;

public class WaterTile extends Renderable{
	
	public static final float TILE_SIZE = 60;

    private final float WAVE_SPEED = 0.03f;
    private float m_MoveFactor = 0;
	     
	public WaterTile(String tag){
		super(tag);
	
	}

	@Override
    public void update(float dt){
        m_MoveFactor += WAVE_SPEED * dt;
        m_MoveFactor %= 1;
    }
	 
	@Override
	public void render(RenderEngine renderer) {
		renderer.submit(this);
	}
	
    public float getHeight() {
        return getPosition().y;
    }
 
    public float getX() {
        return getPosition().x ;
    }
 
    public float getZ() {
        return getPosition().z;
    }

    public float getMoveFactor(){
        return m_MoveFactor;
    }
}
