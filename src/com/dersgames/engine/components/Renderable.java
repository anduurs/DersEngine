package com.dersgames.engine.components;

import com.dersgames.engine.graphics.RenderEngine;

public abstract class Renderable extends Component{
	
	public Renderable(String tag){
		super(tag);
	}
	
	@Override
	public void init() {}
	
	public abstract void render(RenderEngine renderer);

	@Override
	public void update(float dt) {}
	
}
