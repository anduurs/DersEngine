package com.dersgames.engine.graphics.textures;

public class Texture {
	
	private int m_TextureID;
	private int m_NumberOfRows;
	
	/**
	 * Constructor for creating a texture atlas with the same number of rows as columns
	 * @param textureID the id for this texture which is used for texture binding by opengl
	 * @param numOfRows the number of rows in the texture atlas 
	 */
	public Texture(int textureID, int numOfRows){
		m_TextureID = textureID;
		m_NumberOfRows = numOfRows;
	}
	
	public Texture(int textureID){
		m_TextureID = textureID;
		m_NumberOfRows = 1;
	}

	public int getTextureID() {
		return m_TextureID;
	}

	public int getNumberOfRows() {
		return m_NumberOfRows;
	}

}
