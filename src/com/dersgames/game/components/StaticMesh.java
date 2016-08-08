package com.dersgames.game.components;

import com.dersgames.game.graphics.Renderer3D;
import com.dersgames.game.graphics.models.TexturedModel;

public class StaticMesh extends Renderable3D{
	
	public StaticMesh(String tag, TexturedModel model){
		super(tag);
		m_TexturedModel = model;
	}
	
	@Override
	public void render(Renderer3D renderer) {
		renderer.submit(this);
	}

}
