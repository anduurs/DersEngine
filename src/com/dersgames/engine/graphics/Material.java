package com.dersgames.engine.graphics;

import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.graphics.shaders.Shader;
import com.dersgames.engine.graphics.textures.TextureAtlas;

public class Material {
	
	private Shader m_Shader;
	private TextureAtlas m_TextureAtlas;
	
	private Vector3f m_AmbientLight;
	private Vector3f m_DiffuseLight;
	private Vector3f m_SpecularLight;
	private Vector3f m_EmissiveLight;
	
	private float m_Shininess;
	
	private boolean m_HasTransparency;
	private boolean m_UseFakeLighting;
	
	public Material(TextureAtlas textureAtlas, 
			float ambient, float diffuse, float specular, float emissive, float shininess, Shader shader){
		this(   textureAtlas, 
				new Vector3f(ambient, ambient, ambient),
				new Vector3f(diffuse, diffuse, diffuse),
				new Vector3f(specular, specular, specular), 
				new Vector3f(emissive, emissive, emissive), 
				shininess, 
				false, 
				false,
				shader
			);
	}
	
	public Material(TextureAtlas textureAtlas, Vector3f ambient, 
			Vector3f diffuse, Vector3f specular, Vector3f emissive, 
			float shininess, Shader shader){
		
		this(textureAtlas, ambient, diffuse, specular, emissive, shininess, false, false, shader);
	}
	
	public Material(TextureAtlas textureAtlas, Vector3f ambient, 
			Vector3f diffuse, Vector3f specular, Vector3f emissive, float shininess, 
			boolean transparency, boolean useFakeLighting, Shader shader){
		
		m_TextureAtlas = textureAtlas;
		
		m_AmbientLight = ambient;
		m_DiffuseLight = diffuse;
		m_SpecularLight = specular;
		m_EmissiveLight = emissive;
		
		m_Shininess = shininess;
		m_HasTransparency = transparency;
		m_UseFakeLighting = useFakeLighting;
		m_Shader = shader;
		
		m_Shader.addUniform("material.ambient");
		m_Shader.addUniform("material.diffuse");
		m_Shader.addUniform("material.specular");
		m_Shader.addUniform("material.emissive");
		m_Shader.addUniform("material.shininess");
		m_Shader.addUniform("useFakeLighting");
		m_Shader.addUniform("numOfRows");
		
		m_Shader.enable();
		
		m_Shader.loadVector3f("material.ambient", m_AmbientLight);
		m_Shader.loadVector3f("material.diffuse", m_DiffuseLight);
		m_Shader.loadVector3f("material.specular", m_SpecularLight);
		m_Shader.loadVector3f("material.emissive", m_EmissiveLight);
		
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
	
	public Vector3f getAmbientLight() {
		return m_AmbientLight;
	}

	public void setAmbientLight(Vector3f ambientLight) {
		m_AmbientLight = ambientLight;
	}
	
	public Vector3f getDiffuseLight() {
		return m_DiffuseLight;
	}


	public void setDiffuseLight(Vector3f diffuseLight) {
		m_DiffuseLight = diffuseLight;
	}


	public Vector3f getSpecularLight() {
		return m_SpecularLight;
	}


	public void setSpecularLight(Vector3f specularLight) {
		m_SpecularLight = specularLight;
	}
	
	public Vector3f getEmissiveLight() {
		return m_EmissiveLight;
	}

	public void setEmissiveLight(Vector3f emissiveLight) {
		m_EmissiveLight = emissiveLight;
	}

}
