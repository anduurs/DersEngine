package com.dersgames.game.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageManager {
	
	private static HashMap<String, BufferedImage> m_ImageLib = 
			new HashMap<String, BufferedImage>();
	
	public ImageManager(){
		
	}
	
	public static void addImage(String tag, String fileName){
		m_ImageLib.put(tag, loadImage(fileName));
	}
	
	public static void addBitmap(String tag, String fileName){
		m_ImageLib.put(tag, loadBitmap(fileName));
	}
	
	private static synchronized BufferedImage loadImage(String path){
		BufferedImage img = null;
		try {
			img = ImageIO.read(ImageManager.class.getResource("/textures/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return img;
	}
	
	private static synchronized BufferedImage loadBitmap(String path){
		BufferedImage img = null;
		try {
			img = ImageIO.read(ImageManager.class.getResource("/bitmaps/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return img;
	}
	
	public static BufferedImage getImage(String name){
		return m_ImageLib.get(name);
	}

}
