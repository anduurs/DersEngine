package com.dersgames.engine.components;

import com.dersgames.engine.core.Component;

public abstract class Renderable extends Component{
	
	public Renderable(String tag){
		super(tag);
	}
	
	@Override
	public void init() {}
	
	@Override
	public void update(float dt) {}
	
	public abstract void render();
	
}
