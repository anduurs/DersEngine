package com.dersgames.game.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class TextureLoader {
	
	private static HashMap<String, BufferedImage> m_TextureLib;
	
	public TextureLoader(){
		m_TextureLib = new HashMap<String, BufferedImage>();
		
		m_TextureLib.put("atlas", loadTexture("textureatlas.png"));
	}
	
	private synchronized BufferedImage loadTexture(String path){
		BufferedImage img = null;
		try {
			img = ImageIO.read(TextureLoader.class.getResource("/textures/" + path));
		} catch (IOException e) {
			System.err.println("Couldn't read texture from resource folder");
			e.printStackTrace();
		}
		
		return img;
	}
	
	public static BufferedImage getTexture(String name){
		return m_TextureLib.get(name);
	}

}
