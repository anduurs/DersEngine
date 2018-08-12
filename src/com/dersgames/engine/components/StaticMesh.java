package com.dersgames.engine.components;

import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.models.Model;

public class StaticMesh extends Renderable {
	
	private Model m_Model;
	private Material m_Material;
	
	private int m_TextureIndex;
	
	public StaticMesh(String tag, Model model, Material material){
		super(tag);
		
		m_Model = model;
		m_Material = material;
		m_TextureIndex = 0;
	}
	
	public StaticMesh(String tag, Model model, Material material, int textureIndex){
		super(tag);
		
		m_Model = model;
		m_Material = material;
		m_TextureIndex = textureIndex;
	}
	
	@Override
	public void render() {
		RenderEngine.getInstance().submit(this);
	}
	
	public float getTextureXOffset() {
		int col = m_TextureIndex % m_Material.getTextureAtlas().getNumberOfRows();
		return (float)col / (float)m_Material.getTextureAtlas().getNumberOfRows();
	}
	
	public float getTextureYOffset() {
		int row = m_TextureIndex / m_Material.getTextureAtlas().getNumberOfRows();
		return (float)row / (float)m_Material.getTextureAtlas().getNumberOfRows();
	}

	public Model getModel() {
		return m_Model;
	}
	
	public Material getMaterial() {
		return m_Material;
	}
}
