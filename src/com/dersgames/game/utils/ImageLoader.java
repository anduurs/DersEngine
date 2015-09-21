package com.dersgames.game.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	private static HashMap<String, BufferedImage> m_ImageLib = 
			new HashMap<String, BufferedImage>();
	
	public ImageLoader(){
		m_ImageLib.put("atlas", loadImage("texture_atlas.png"));
		m_ImageLib.put("tilemap", loadBitmap("tilemap0.png"));
	}
	
	private synchronized BufferedImage loadImage(String path){
		BufferedImage img = null;
		try {
			img = ImageIO.read(ImageLoader.class.getResource("/textures/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return img;
	}
	
	private synchronized BufferedImage loadBitmap(String path){
		BufferedImage img = null;
		try {
			img = ImageIO.read(ImageLoader.class.getResource("/bitmaps/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return img;
	}
	
	public static BufferedImage getImage(String name){
		return m_ImageLib.get(name);
	}

}
