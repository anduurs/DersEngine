package com.dersgames.game.components;

import com.dersgames.game.graphics.Renderable3D;
import com.dersgames.game.graphics.Renderer3D;
import com.dersgames.game.graphics.models.TexturedModel;

public class StaticMesh extends Renderable3D{
	
	private TexturedModel m_TexturedModel;
	
	public StaticMesh(String tag, TexturedModel model){
		super(tag);
		m_TexturedModel = model;
	}

	@Override
	public void update(float dt) {
		
	}
	
	@Override
	public void render(Renderer3D renderer) {
		renderer.getShader().setUniform("transformationMatrix", 
				m_Entity.getTransform().getTransformationMatrix());
		renderer.render(m_TexturedModel);
	}

}
