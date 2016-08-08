package com.dersgames.game.components;

import com.dersgames.game.graphics.Renderer3D;

public abstract class Renderable3D extends Component{
	
	public Renderable3D(String tag){
		super(tag);
	}
	
	public abstract void render(Renderer3D renderer);

	@Override
	public void update(float dt) {}

}
