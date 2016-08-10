package com.dersgames.engine.components;

import com.dersgames.engine.graphics.models.TexturedModel;
import com.dersgames.engine.graphics.renderers.Renderer3D;

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
