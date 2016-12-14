package com.dersgames.engine.graphics.shaders;

public class TerrainShader extends PhongShader{

	public TerrainShader() {
		super("terrainVertexShader", "terrainFragmentShader");
	
		addUniform("backgroundTexture");
		addUniform("rTexture");
		addUniform("gTexture");
		addUniform("bTexture");
		addUniform("blendMap");
	}

	public void connectTextureUnits(){
		loadInteger("backgroundTexture", 0);
		loadInteger("rTexture", 1);
		loadInteger("gTexture", 2);
		loadInteger("bTexture", 3);
		loadInteger("blendMap", 4);
	}

}
