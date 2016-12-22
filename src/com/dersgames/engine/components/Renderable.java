package com.dersgames.engine.components;

import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.models.TexturedModel;

public abstract class Renderable extends Component{
	
	public Renderable(String tag){
		super(tag);
	}
	
	@Override
	public void init() {}
	
	@Override
	public void update(float dt) {}
	
	public abstract void render(RenderEngine renderer);
	
}
