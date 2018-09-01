package com.dersgames.engine.graphics.water;

import com.dersgames.engine.components.Renderable;

public class WaterTile extends Renderable {
	
	public static final float TILE_SIZE = 60;

	public WaterTile(String tag){
		super(tag);
	
	}

	@Override
	public void render() {
		m_RenderEngine.getWaterRenderer().submit(this);
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
}
