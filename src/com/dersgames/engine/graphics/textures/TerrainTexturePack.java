package com.dersgames.engine.graphics.textures;

public class TerrainTexturePack {
	
	private TerrainTexture m_BackgroundTexture;
	private TerrainTexture m_rTexture;
	private TerrainTexture m_gTexture;
	private TerrainTexture m_bTexture;
	
	public TerrainTexturePack(TerrainTexture backgroundTexture, TerrainTexture rTexture, 
			TerrainTexture gTexture, TerrainTexture bTexture) {
		
		m_BackgroundTexture = backgroundTexture;
		m_rTexture = rTexture;
		m_gTexture = gTexture;
		m_bTexture = bTexture;
	}
	
	public TerrainTexture getBackgroundTexture() {
		return m_BackgroundTexture;
	}

	public TerrainTexture getrTexture() {
		return m_rTexture;
	}

	public TerrainTexture getgTexture() {
		return m_gTexture;
	}

	public TerrainTexture getbTexture() {
		return m_bTexture;
	}

}
