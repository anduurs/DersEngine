package com.dersgames.engine.graphics.textures;

public class TerrainTexturePack {
	
	private Texture m_BackgroundTexture;
	private Texture m_rTexture;
	private Texture m_gTexture;
	private Texture m_bTexture;
	private Texture m_aTexture;
	
	public TerrainTexturePack(Texture backgroundTexture, Texture rTexture, 
			Texture gTexture, Texture bTexture, Texture aTexture) {
		
		m_BackgroundTexture = backgroundTexture;
		m_rTexture = rTexture;
		m_gTexture = gTexture;
		m_bTexture = bTexture;
		m_aTexture = aTexture;
	}
	
	public TerrainTexturePack(Texture backgroundTexture, Texture rTexture, 
			Texture gTexture, Texture bTexture) {
		
		m_BackgroundTexture = backgroundTexture;
		m_rTexture = rTexture;
		m_gTexture = gTexture;
		m_bTexture = bTexture;
	}
	
	public Texture getBackgroundTexture() {
		return m_BackgroundTexture;
	}

	public Texture getrTexture() {
		return m_rTexture;
	}

	public Texture getgTexture() {
		return m_gTexture;
	}

	public Texture getbTexture() {
		return m_bTexture;
	}
	
	public Texture getaTexture() {
		return m_aTexture;
	}

}
