package com.dersgames.engine.graphics.textures;

import static org.lwjgl.opengl.GL11.glDeleteTextures;

import java.util.ArrayList;
import java.util.List;

import com.dersgames.engine.graphics.textures.TextureData.TextureType;

public class TextureManager {

	private static TextureManager instance;
	
	private List<Integer> m_TextureIDs;
	
	public static TextureManager getInstance() {
		if (instance == null) {
			instance = new TextureManager();
		}
		
		return instance;
	}
	
	private TextureManager() {
		m_TextureIDs = new ArrayList<Integer>();
	}
	
	public int loadModelTexture(String name){
		TextureData texture = new TextureData(name, TextureType.MODEL);
		int textureID = texture.getID();
		m_TextureIDs.add(textureID);
		return textureID;
	}

	public int loadGUITexture(String name){
		TextureData texture = new TextureData(name, TextureType.GUI);
		int textureID = texture.getID();
		m_TextureIDs.add(textureID);
		return textureID;
	}

	public int loadCubeMapTexture(String[] images){
		TextureData cubeMapTexture = new TextureData(images);
		int textureID = cubeMapTexture.getID();
		m_TextureIDs.add(textureID);
		return textureID;
	}
	
	public void dispose(){
		for(Integer i : m_TextureIDs)
			glDeleteTextures(i);
	}
}
