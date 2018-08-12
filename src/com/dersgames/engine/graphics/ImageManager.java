package com.dersgames.engine.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageManager {
	
	private HashMap<String, BufferedImage> m_ImageLib = 
			new HashMap<String, BufferedImage>();
	
	private static ImageManager instance;
	
	public static ImageManager getInstance() {
		if (instance == null) {
			instance = new ImageManager();
		}
		
		return instance;
	}
	
	private ImageManager(){
		
	}
	
	public void addImage(String tag, String fileName){
		m_ImageLib.put(tag, loadImage(fileName));
	}
	
	private synchronized BufferedImage loadImage(String path){
		BufferedImage img = null;
		try {
			img = ImageIO.read(Class.class.getResourceAsStream("/textures/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return img;
	}
	
	public BufferedImage getImage(String name){
		return m_ImageLib.get(name);
	}

}
