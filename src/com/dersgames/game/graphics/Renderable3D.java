package com.dersgames.game.graphics;

import com.dersgames.game.components.Component;

public abstract class Renderable3D extends Component{
	
	public Renderable3D(String tag){
		super(tag);
	}
	
	public abstract void render(Renderer3D renderer);

	@Override
	public void update(float dt) {}

}
