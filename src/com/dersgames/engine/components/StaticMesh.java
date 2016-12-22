package com.dersgames.engine.components;

import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.models.TexturedModel;

public class StaticMesh extends Renderable{
	
	private TexturedModel m_TexturedModel;
	private Material m_Material;
	
	private int m_TextureIndex;
	
	public StaticMesh(String tag, TexturedModel model){
		this(tag, model, 0);
	}
	
	public StaticMesh(String tag, TexturedModel model, int textureIndex){
		super(tag);
		m_TexturedModel = model;
		m_Material = model.getMaterial();
		m_TextureIndex = textureIndex;
	}
	
	@Override
	public void render(RenderEngine renderer) {
		renderer.submit(this);
	}
	
	public float getTextureXOffset() {
		int col = m_TextureIndex % m_Material.getTextureAtlas().getNumberOfRows();
		return (float)col / (float)m_Material.getTextureAtlas().getNumberOfRows();
	}
	
	public float getTextureYOffset() {
		int row = m_TextureIndex / m_Material.getTextureAtlas().getNumberOfRows();
		return (float)row / (float)m_Material.getTextureAtlas().getNumberOfRows();
	}

	public TexturedModel getTexturedModel() {
		return m_TexturedModel;
	}
	
	public Model getModel(){
		return m_TexturedModel.getModel();
	}

	public Material getMaterial() {
		return m_Material;
	}
}
