package com.dersgames.engine.components;

import com.dersgames.engine.graphics.models.TexturedModel;
import com.dersgames.engine.graphics.renderers.Renderer3D;

public abstract class Renderable3D extends Component{
	
	protected TexturedModel m_TexturedModel;
	
	public Renderable3D(String tag){
		super(tag);
		
	}
	
	@Override
	public void init() {
		
		
	}
	
	public abstract void render(Renderer3D renderer);

	@Override
	public void update(float dt) {}
	
	public TexturedModel getTexturedModel(){
		return m_TexturedModel;
	}

}
