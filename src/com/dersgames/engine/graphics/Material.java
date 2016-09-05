package com.dersgames.engine.graphics;

import com.dersgames.engine.graphics.textures.TextureAtlas;

public class Material {
	
	private TextureAtlas m_TextureAtlas;
	
	private float m_ShineDamper;
	private float m_Reflectivity;
	
	private boolean m_HasTransparency;
	private boolean m_UseFakeLighting;
	
	public Material(TextureAtlas textureAtlas){
		this(textureAtlas, 1.0f, 0.0f, false, false);
	}
	
	public Material(TextureAtlas textureAtlas, float shineDamper, float reflectivity, 
			boolean transparency, boolean useFakeLighting){
		
		m_TextureAtlas = textureAtlas;
		
		m_ShineDamper = shineDamper;
		m_Reflectivity = reflectivity;
		
		m_HasTransparency = transparency;
		m_UseFakeLighting = useFakeLighting;
	}
	
	public void setUseFakeLighting(boolean fakelight){
		m_UseFakeLighting = fakelight;
	}
	
	public boolean getUseFakeLighting(){
		return m_UseFakeLighting;
	}
	
	public int getTextureID(){
		return m_TextureAtlas.getTextureID();
	}
	
	public TextureAtlas getTextureAtlas(){
		return m_TextureAtlas;
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

	public boolean hasTransparency() {
		return m_HasTransparency;
	}

	public void setTransparency(boolean hasTransparency) {
		m_HasTransparency= hasTransparency;
	}

}
