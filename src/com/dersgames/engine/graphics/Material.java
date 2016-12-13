package com.dersgames.engine.graphics;

import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.graphics.shaders.Shader;
import com.dersgames.engine.graphics.textures.TextureAtlas;

public class Material {
	
	private Shader m_Shader;
	private TextureAtlas m_TextureAtlas;
	
	private Vector3f m_BaseColor;
	private Vector3f m_Specular;
	private Vector3f m_Emissive;
	
	private float m_Shininess;
	
	private boolean m_HasTransparency;
	private boolean m_UseFakeLighting;
	
	public Material(TextureAtlas textureAtlas, 
			float baseColor, float specular, float emissive, float shininess, Shader shader){
		this(   textureAtlas, 
				new Vector3f(baseColor, baseColor, baseColor),
				new Vector3f(specular, specular, specular), 
				new Vector3f(emissive, emissive, emissive), 
				shininess, 
				false, 
				false,
				shader
			);
	}
	
	public Material(TextureAtlas textureAtlas, Vector3f baseColor, 
			 Vector3f specular, Vector3f emissive, 
			float shininess, Shader shader){
		
		this(textureAtlas, baseColor, specular, emissive, shininess, false, false, shader);
	}
	
	public Material(TextureAtlas textureAtlas, Vector3f baseColor, 
			Vector3f specular, Vector3f emissive, float shininess, 
			boolean transparency, boolean useFakeLighting, Shader shader){
		
		m_TextureAtlas = textureAtlas;
		
		m_BaseColor = baseColor;
		m_Specular = specular;
		m_Emissive = emissive;
		
		m_Shininess = shininess;
		m_HasTransparency = transparency;
		m_UseFakeLighting = useFakeLighting;
		m_Shader = shader;
		
		m_Shader.addUniform("material.diffuseMap");
		m_Shader.addUniform("material.baseColor");
		m_Shader.addUniform("material.specular");
		m_Shader.addUniform("material.emissive");
		m_Shader.addUniform("material.shininess");
		m_Shader.addUniform("useFakeLighting");
		m_Shader.addUniform("numOfRows");
		
		m_Shader.enable();
		m_Shader.loadInteger("material.diffuseMap", 0);
		m_Shader.loadVector3f("material.baseColor", m_BaseColor);
		m_Shader.loadVector3f("material.specular", m_Specular);
		m_Shader.loadVector3f("material.emissive", m_Emissive);
		
		m_Shader.loadFloat("material.shininess", shininess);
		
		if(useFakeLighting)
			m_Shader.loadFloat("useFakeLighting", 1.0f);
		else m_Shader.loadFloat("useFakeLighting", 0.0f);
		
		m_Shader.loadFloat("numOfRows", m_TextureAtlas.getNumberOfRows());
		
		m_Shader.disable();
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

	public float getShininess() {
		return m_Shininess;
	}

	public void setShininess(float shineDamper) {
		this.m_Shininess = shineDamper;
	}

	public boolean hasTransparency() {
		return m_HasTransparency;
	}

	public void setTransparency(boolean hasTransparency) {
		m_HasTransparency= hasTransparency;
	}
	
	public Vector3f getBaseColor() {
		return m_BaseColor;
	}

	public void setBaseColor(Vector3f baseColor) {
		m_BaseColor = baseColor;
	}

	public Vector3f getSpecular() {
		return m_Specular;
	}


	public void setSpecular(Vector3f specular) {
		m_Specular = specular;
	}
	
	public Vector3f getEmissive() {
		return m_Emissive;
	}

	public void setEmissive(Vector3f emissive) {
		m_Emissive = emissive;
	}

}
