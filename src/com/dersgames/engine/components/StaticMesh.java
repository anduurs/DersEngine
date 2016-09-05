package com.dersgames.engine.components;

import com.dersgames.engine.graphics.Material;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.models.Mesh;
import com.dersgames.engine.graphics.models.TexturedMesh;

public class StaticMesh extends Renderable{
	
	private TexturedMesh m_TexturedMesh;
	private Material m_Material;
	
	private int m_TextureIndex;
	
	public StaticMesh(String tag, TexturedMesh mesh){
		this(tag, mesh, 0);
	}
	
	public StaticMesh(String tag, TexturedMesh mesh, int textureIndex){
		super(tag);
		
		m_TexturedMesh = mesh;
		m_Material = mesh.getMaterial();
		m_TextureIndex  = textureIndex;
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

	public TexturedMesh getTexturedMesh() {
		return m_TexturedMesh;
	}
	
	public Mesh getMesh(){
		return m_TexturedMesh.getMesh();
	}

	public Material getMaterial() {
		return m_Material;
	}

}
