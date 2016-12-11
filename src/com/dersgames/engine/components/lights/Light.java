package com.dersgames.engine.components.lights;

import com.dersgames.engine.components.Component;
import com.dersgames.engine.core.Vector3f;

public class Light extends Component{
	
	protected Vector3f m_AmbientLight;
	protected Vector3f m_DiffuseLight;
	protected Vector3f m_SpecularLight;
	protected float m_Intensity;
	
	public Light(String tag, Vector3f ambient, Vector3f diffuse, Vector3f specular, float intensity) {
		super(tag);
		
		m_AmbientLight = ambient;
		m_DiffuseLight = diffuse;
		m_SpecularLight = specular;
		m_Intensity = intensity;
	}
	
	@Override
	public void init() {}
	@Override
	public void update(float dt) {}


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

	public float getIntensity() {
		return m_Intensity;
	}

	public void setIntensity(float intensity) {
		m_Intensity = intensity;
	}

}
