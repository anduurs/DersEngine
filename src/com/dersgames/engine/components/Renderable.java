package com.dersgames.engine.components;

import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.models.TexturedModel;

public abstract class Renderable extends Component{
	
	protected TexturedModel m_TexturedModel;
	protected Material m_Material;
	
	protected int m_TextureIndex;
	
	public Renderable(String tag){
		super(tag);
	}
	
	public Renderable(String tag, TexturedModel model, int textureIndex){
		super(tag);
		
		m_TexturedModel = model;
		m_Material = model.getMaterial();
		m_TextureIndex = textureIndex;
	}
	
	public Renderable(String tag, TexturedModel mesh){
		this(tag, mesh, 0);
	}
	
	@Override
	public void init() {}
	
	public void render(RenderEngine renderer){
		renderer.submit(this);
	}

	@Override
	public void update(float dt) {}
	
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
