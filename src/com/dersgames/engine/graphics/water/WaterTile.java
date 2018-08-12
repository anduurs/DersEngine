package com.dersgames.engine.graphics.water;

import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.graphics.RenderEngine;

public class WaterTile extends Renderable {
	
	public static final float TILE_SIZE = 60;

	public WaterTile(String tag){
		super(tag);
	
	}

	@Override
	public void render() {
		RenderEngine.getInstance().submit(this);
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
