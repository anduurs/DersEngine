package com.dersgames.engine.graphics.models;

import com.dersgames.engine.graphics.Material;

public class TexturedMesh {
	
	private Mesh m_Mesh;
	private Material m_Material;
	
	public TexturedMesh(Mesh mesh, Material material){
		m_Mesh = mesh;
		m_Material = material;
	}

	public Mesh getMesh() {
		return m_Mesh;
	}

	public Material getMaterial() {
		return m_Material;
	}
}
