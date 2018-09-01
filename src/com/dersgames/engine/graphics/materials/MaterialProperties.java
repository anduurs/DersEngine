package com.dersgames.engine.graphics.materials;

import com.dersgames.engine.math.Vector3f;

public class MaterialProperties {
	public Vector3f baseColor;
	public Vector3f specularColor;
	public Vector3f emissiveColor;
	public float shininess;
	public boolean hasTransparency;
	public boolean useFakeLighting;
	
	public MaterialProperties(Vector3f baseColor, Vector3f specularColor, Vector3f emissiveColor, float shininess,
			boolean hasTransparency, boolean useFakeLighting) {
	
		this.baseColor = baseColor;
		this.specularColor = specularColor;
		this.emissiveColor = emissiveColor;
		this.shininess = shininess;
		this.hasTransparency = hasTransparency;
		this.useFakeLighting = useFakeLighting;
	}
	
	
}
