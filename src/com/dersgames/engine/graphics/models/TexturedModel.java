package com.dersgames.engine.graphics.models;

import com.dersgames.engine.graphics.materials.Material;

public class TexturedModel {
	
	private Model m_Model;
	private Material m_Material;
	
	public TexturedModel(Model model, Material material){
		m_Model = model;
		m_Material = material;
	}

	public Model getModel() {
		return m_Model;
	}

	public Material getMaterial() {
		return m_Material;
	}
}
