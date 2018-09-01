package com.dersgames.engine.components;

import com.dersgames.engine.core.Component;
import com.dersgames.engine.graphics.RenderEngine;

public abstract class Renderable extends Component{
	
	protected RenderEngine m_RenderEngine;

	public Renderable(String tag){
		super(tag);
		
		m_RenderEngine = RenderEngine.getInstance();
	}
	
	@Override
	public void init() {}
	
	@Override
	public void update(float dt) {}
	
	public abstract void render();
}
