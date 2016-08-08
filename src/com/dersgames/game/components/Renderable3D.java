package com.dersgames.game.components;

import com.dersgames.game.graphics.Renderer3D;
import com.dersgames.game.graphics.models.TexturedModel;

public abstract class Renderable3D extends Component{
	
	protected TexturedModel m_TexturedModel;
	
	public Renderable3D(String tag){
		super(tag);
		
	}
	
	public abstract void render(Renderer3D renderer);

	@Override
	public void update(float dt) {}
	
	public TexturedModel getTexturedModel(){
		return m_TexturedModel;
	}

}
