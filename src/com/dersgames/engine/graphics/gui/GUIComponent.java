package com.dersgames.engine.graphics.gui;

import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.core.Matrix4f;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.textures.Texture;

public class GUIComponent extends Renderable{
	
	protected Texture m_Texture;

	public GUIComponent(String tag, Texture texture) {
		super(tag);
		m_Texture = texture;
	}
	
	public void update(float dt){
//		getTransform().rotate(getRotation().getForward(), dt*15.0f);
	}

	@Override
	public void render(RenderEngine renderer) {
		renderer.submit(this);
	}
	
	public Texture getTexture(){
		return m_Texture;
	}

}
