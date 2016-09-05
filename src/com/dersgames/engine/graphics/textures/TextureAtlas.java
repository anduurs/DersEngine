package com.dersgames.engine.graphics.textures;

public class TextureAtlas {
	
	private int m_TextureID;
	private int m_NumberOfRows;
	
	public TextureAtlas(int textureID, int numOfRows){
		m_TextureID = textureID;
		m_NumberOfRows = numOfRows;
	}

	public int getTextureID() {
		return m_TextureID;
	}

	public int getNumberOfRows() {
		return m_NumberOfRows;
	}

}
