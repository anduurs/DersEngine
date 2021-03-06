package com.dersgames.engine.graphics.materials;

import java.util.List;

import com.dersgames.engine.graphics.shaders.IShader;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.math.Vector3f;

public class Material {
	
	private IShader m_Shader;
	private Texture m_TextureAtlas;
	private List<Texture> m_Textures;
	private Texture m_SpecularMap;
	private Texture m_NormalMap;
	
	private boolean m_UseSpecularMap;
	private boolean m_UseNormalMap;
	
	private Vector3f m_BaseColor;
	private Vector3f m_Specular;
	private Vector3f m_Emissive;
	
	private float m_Shininess;
	
	private boolean m_HasTransparency;
	private boolean m_UseFakeLighting;
	
	public int getID() {
		return m_Shader.getShaderProgramID();
	}
	
	public Material(Texture textureAtlas, IShader shader){
		this(   textureAtlas, 
				new Vector3f(1.0f, 1.0f, 1.0f),
				new Vector3f(0.2f, 0.2f, 0.2f), 
				new Vector3f(0, 0, 0), 
				0.0f,
				false, 
				false,
				shader
			);
	}
	
	public Material(Texture textureAtlas, 
			float baseColor, float specular, float emissive, float shininess, IShader shader){
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
	
	public Material(Texture textureAtlas, 
			float baseColor, float specular, float emissive, float shininess, boolean transparency, boolean useFakeLight, IShader shader){
		this(   textureAtlas, 
				new Vector3f(baseColor, baseColor, baseColor),
				new Vector3f(specular, specular, specular), 
				new Vector3f(emissive, emissive, emissive), 
				shininess, 
				transparency, 
				useFakeLight,
				shader
			);
	}

	public Material(Texture textureAtlas, Texture specularMap,
					float baseColor, float specular, float emissive, float shininess, IShader shader){
		this(   textureAtlas, specularMap,
				new Vector3f(baseColor, baseColor, baseColor),
				new Vector3f(specular, specular, specular),
				new Vector3f(emissive, emissive, emissive),
				shininess,
				false,
				false,
				shader
		);
	}
	
	public Material(Texture textureAtlas, Texture specularMap,
			float baseColor, float specular, float emissive, float shininess,boolean transparency, boolean useFakeLight, IShader shader){
		this(   textureAtlas, specularMap,
		new Vector3f(baseColor, baseColor, baseColor),
		new Vector3f(specular, specular, specular),
		new Vector3f(emissive, emissive, emissive),
		shininess,
		transparency,
		useFakeLight,
		shader
);
}
	
	public Material(Texture textureAtlas, Vector3f baseColor, 
			 Vector3f specular, Vector3f emissive, 
			float shininess, IShader shader){
		
		this(textureAtlas, baseColor, specular, emissive, shininess, false, false, shader);
	}
	
	public Material(Texture textureAtlas, Vector3f baseColor, 
			Vector3f specular, Vector3f emissive, float shininess, 
			boolean transparency, boolean useFakeLighting, IShader shader){
		
		m_UseSpecularMap = false;
		m_UseNormalMap = false;
		m_TextureAtlas = textureAtlas;
		
		m_BaseColor = baseColor;
		m_Specular = specular;
		m_Emissive = emissive;
		
		m_Shininess = shininess;
		m_HasTransparency = transparency;
		m_UseFakeLighting = useFakeLighting;
		m_Shader = shader;
		
		addUniforms();
	}
	
//	public Material(Texture textureAtlas, Texture specularMap, Vector3f baseColor, 
//			Vector3f specular, Vector3f emissive, float shininess, 
//			boolean transparency, boolean useFakeLighting, Shader shader){
//		
//		this(textureAtlas, baseColor, specular, emissive, 
//				shininess, transparency, useFakeLighting, shader);
//		
//		m_UseSpecularMap = true;
//		m_SpecularMap = specularMap;
//		
//		m_Shader.addUniform("material.specularMap");
//	}
	
	public Material(Texture textureAtlas, Texture normalMap, Vector3f baseColor, 
			Vector3f specular, Vector3f emissive, float shininess, 
			boolean transparency, boolean useFakeLighting, IShader shader){
		
		this(textureAtlas, baseColor, specular, emissive, 
				shininess, transparency, useFakeLighting, shader);
		
		m_UseNormalMap = true;
		m_NormalMap = normalMap;
	
		m_Shader.addUniform("material.normalMap");
	}
	
	public Material(Texture textureAtlas, Texture specularMap, Texture normalMap, Vector3f baseColor, 
			Vector3f specular, Vector3f emissive, float shininess, 
			boolean transparency, boolean useFakeLighting, IShader shader){
		
		m_BaseColor = baseColor;
		m_Specular = specular;
		m_Emissive = emissive;
		
		m_Shininess = shininess;
		m_HasTransparency = transparency;
		m_UseFakeLighting = useFakeLighting;
		m_Shader = shader;
		
		m_UseNormalMap = true;
		m_NormalMap = normalMap;
		m_Shader.addUniform("material.normalMap");
		
		m_UseSpecularMap = true;
		m_SpecularMap = specularMap;
		m_Shader.addUniform("material.specularMap");
		m_TextureAtlas = textureAtlas;
		
		addUniforms();
	}
	
	private void addUniforms(){
		m_Shader.addUniform("material.diffuseMap");
		m_Shader.addUniform("usingNormalMap");
		m_Shader.addUniform("material.useSpecularMap");
		m_Shader.addUniform("material.baseColor");
		m_Shader.addUniform("material.specular");
		m_Shader.addUniform("material.emissive");
		m_Shader.addUniform("material.shininess");
		m_Shader.addUniform("useFakeLighting");
		m_Shader.addUniform("numOfRows");
	}
	
	public void updateUniforms(){
		if(m_UseSpecularMap){
			m_Shader.loadFloat("material.useSpecularMap", 1.0f);
			m_Shader.loadInteger("material.specularMap", 1);
		}else m_Shader.loadFloat("material.useSpecularMap", 0.0f);
		
		if(m_UseNormalMap){
			m_Shader.loadFloat("usingNormalMap", 1.0f);
			m_Shader.loadInteger("material.normalMap", 2);
		}else{
			m_Shader.loadFloat("usingNormalMap", 0.0f);
		}
		
		m_Shader.loadInteger("material.diffuseMap", 0);
		m_Shader.loadVector3f("material.baseColor", m_BaseColor);
		m_Shader.loadVector3f("material.specular", m_Specular);
		m_Shader.loadVector3f("material.emissive", m_Emissive);
		
		m_Shader.loadFloat("material.shininess", m_Shininess);
		
		if(m_UseFakeLighting)
			m_Shader.loadFloat("useFakeLighting", 1.0f);
		else m_Shader.loadFloat("useFakeLighting", 0.0f);
		
		m_Shader.loadFloat("numOfRows", m_TextureAtlas.getNumberOfRows());
	}
	
	public void useFakeLighting(boolean fakelight){
		m_UseFakeLighting = fakelight;
	}
	
	public boolean isUsingFakeLighting(){
		return m_UseFakeLighting;
	}
	
	public int getTextureID(){
		return m_TextureAtlas.getTextureID();
	}
	
	public Texture getTextureAtlas(){
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

	public int getSpecularMapTextureID() {
		return m_SpecularMap.getTextureID();
	}

	public boolean isUsingSpecularMap() {
		return m_UseSpecularMap;
	}
	
	public int getNormalMapTextureID(){
		return m_NormalMap.getTextureID();
	}
	
	public boolean isUsingNormalMap(){
		return m_UseNormalMap;
	}

	public IShader getShader(){
		return m_Shader;
	}

}
