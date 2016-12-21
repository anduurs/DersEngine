package com.dersgames.engine.graphics.textures;

import java.util.ArrayList;
import java.util.List;

import com.dersgames.engine.graphics.materials.Material;

public class TerrainTexturePack {
	
	private Texture m_BackgroundTexture;
	private Texture m_rTexture;
	private Texture m_gTexture;
	private Texture m_bTexture;
	private Texture m_aTexture;
	
	private List<Material> m_TerrainMaterials; 
	
//	public TerrainTexturePack(Texture backgroundTexture, Texture rTexture, 
//			Texture gTexture, Texture bTexture, Texture aTexture) {
//		
//		m_BackgroundTexture = backgroundTexture;
//		m_rTexture = rTexture;
//		m_gTexture = gTexture;
//		m_bTexture = bTexture;
//		m_aTexture = aTexture;
//		
//		m_TerrainMaterials = new ArrayList<>();
//	}
//	
//	public TerrainTexturePack(Texture backgroundTexture, Texture rTexture, 
//			Texture gTexture, Texture bTexture) {
//		
//		m_BackgroundTexture = backgroundTexture;
//		m_rTexture = rTexture;
//		m_gTexture = gTexture;
//		m_bTexture = bTexture;
//		
//		m_TerrainMaterials = new ArrayList<>();
//	}
	
	public TerrainTexturePack() {
		m_TerrainMaterials = new ArrayList<>();
	}
	
	public void addTerrainMaterial(Material material){
		m_TerrainMaterials.add(material);
	}
	
	public Texture getTexture(int index) {
		return m_TerrainMaterials.get(index).getTextureAtlas();
	}
	
	public List<Material> getTerrainMaterials(){
		return m_TerrainMaterials;
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
