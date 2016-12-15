package com.dersgames.engine.components;

import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.models.TexturedModel;

public class StaticMesh extends Renderable{
	
	public StaticMesh(String tag, TexturedModel mesh){
		this(tag, mesh, 0);
	}
	
	public StaticMesh(String tag, TexturedModel mesh, int textureIndex){
		super(tag, mesh, textureIndex);
	}
	
	@Override
	public void render(RenderEngine renderer) {
		renderer.submit(this);
	}

}
