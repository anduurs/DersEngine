package com.dersgames.engine.graphics.models;

import com.dersgames.engine.graphics.textures.ModelTexture;

public class TexturedModel {
	
	private Model m_Model;
	private ModelTexture m_ModelTexture;
	
	public TexturedModel(Model model, ModelTexture texture){
		m_Model = model;
		m_ModelTexture = texture;
	}

	public Model getModel() {
		return m_Model;
	}

	public ModelTexture getTexture() {
		return m_ModelTexture;
	}

}
