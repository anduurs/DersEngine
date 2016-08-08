package com.dersgames.game.graphics.textures;

public class ModelTexture {
	
	private int m_TextureID;
	
	private float m_ShineDamper = 1.0f;
	private float m_Reflectivity = 0.0f;
	
	public ModelTexture(int textureID){
		m_TextureID = textureID;
	}
	
	public int getID(){
		return m_TextureID;
	}

	public float getShineDamper() {
		return m_ShineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.m_ShineDamper = shineDamper;
	}

	public float getReflectivity() {
		return m_Reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.m_Reflectivity = reflectivity;
	}

}
