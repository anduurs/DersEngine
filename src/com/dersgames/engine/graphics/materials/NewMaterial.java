package com.dersgames.engine.graphics.materials;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.util.ArrayList;
import java.util.List;

import com.dersgames.engine.graphics.shaders.GLShader;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.math.Matrix4f;
import com.dersgames.engine.math.Vector2f;
import com.dersgames.engine.math.Vector3f;
import com.dersgames.engine.util.Pair;

public class NewMaterial {
	
	protected GLShader m_Shader;
	protected MaterialProperties m_Properties;
	
	public List<Pair<String, Texture>>  m_Textures;
	public List<Pair<String, Integer>>  m_UniformInts;
	public List<Pair<String, Float>>    m_UniformFloats;
	public List<Pair<String, Vector2f>> m_UniformVec2s;
	public List<Pair<String, Vector3f>> m_UniformVec3s;
	public List<Pair<String, Matrix4f>> m_UniformMat4s;

	public NewMaterial(GLShader shader, MaterialProperties properties) {
		m_Shader 	 = shader;
		m_Properties = properties;
		
		m_Textures 		= new ArrayList<>();
		m_UniformInts 	= new ArrayList<>();
		m_UniformFloats = new ArrayList<>();
		m_UniformVec2s  = new ArrayList<>();
		m_UniformVec3s  = new ArrayList<>();
		m_UniformMat4s  = new ArrayList<>();
	}
	
	public void addTexture(String uniformName, Texture texture) {
		m_Textures.add(Pair.of(uniformName, texture));
	}
	

	
	public void updateMaterialProperties() {
		
		
		for (int samplerSlot = 0; samplerSlot < m_Textures.size(); samplerSlot++) {
			Pair<String, Texture> uniform = m_Textures.get(samplerSlot);
			m_Shader.loadInteger("material." + uniform.first, samplerSlot);
			glActiveTexture(GL_TEXTURE0 + samplerSlot);
			glBindTexture(GL_TEXTURE_2D, uniform.second.getTextureID());
		}
	}
	
	public void setTexture(String name, Texture texture) {
		
	}
	
	public void setFloat(String name, float value) {
		m_Shader.loadFloat(name, value);
	}
	
	public void setVector3f(String name, Vector3f value) {
		m_Shader.loadVector3f(name, value);
	}

}
