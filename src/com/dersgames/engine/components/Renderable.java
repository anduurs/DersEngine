package com.dersgames.engine.components;

import com.dersgames.engine.graphics.Material;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.models.Mesh;
import com.dersgames.engine.graphics.models.TexturedMesh;

public abstract class Renderable extends Component{
	
	protected TexturedMesh m_TexturedMesh;
	protected Material m_Material;
	
	protected int m_TextureIndex;
	
	public Renderable(String tag){
		super(tag);
	}
	
	public Renderable(String tag, TexturedMesh mesh, int textureIndex){
		super(tag);
		
		m_TexturedMesh = mesh;
		m_Material = mesh.getMaterial();
		m_TextureIndex = textureIndex;
	}
	
	public Renderable(String tag, TexturedMesh mesh){
		this(tag, mesh, 0);
	}
	
	@Override
	public void init() {}
	
	public abstract void render(RenderEngine renderer);

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
