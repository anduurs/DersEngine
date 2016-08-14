package com.dersgames.engine.components;

import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.models.TexturedModel;

public class StaticMesh extends Renderable{
	
	private TexturedModel m_TexturedModel;
	private int m_TextureIndex;
	
	public StaticMesh(String tag, TexturedModel model){
		this(tag, model, 0);
	}
	
	public StaticMesh(String tag, TexturedModel model, int textureIndex){
		super(tag);
		m_TexturedModel = model;
		m_TextureIndex  = textureIndex;
	}
	
	@Override
	public void render(RenderEngine renderer) {
		renderer.submit(this);
	}
	
	public TexturedModel getTexturedModel(){
		return m_TexturedModel;
	}

	public float getTextureXOffset() {
		int rows = m_TexturedModel.getTexture().getNumberOfRows();
		int col = m_TextureIndex % rows;
		return (float)col / (float)rows;
	}
	
	public float getTextureYOffset() {
		int rows = m_TexturedModel.getTexture().getNumberOfRows();
		int row = m_TextureIndex / rows;
		return (float)row / (float)rows;
	}

}
