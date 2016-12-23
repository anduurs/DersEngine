package com.dersgames.engine.graphics.textures;


import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_LOD_BIAS;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;

import com.dersgames.engine.utils.ImageManager;

public class TextureData {
	
	private int m_ID;
	private int m_Width, m_Height;
	public enum TextureType {MODEL, GUI};
	private TextureType m_Type;
	
	public TextureData(String name, TextureType type){
		m_Type = type;
		
		m_ID = glGenTextures();
		
		switch(m_Type){
			case MODEL:
				loadModelTexture(name);
				break;
			case GUI:
				loadGUITexture(name);
				break;
			default:
				break;
		}	
	}
	
	public TextureData(String[] images){
		m_ID = glGenTextures();
		
		glBindTexture(GL_TEXTURE_CUBE_MAP, m_ID);
		
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);  
		
		for(int i = 0; i < images.length; i++){
			ByteBuffer buffer = getImageData(images[i]);
			
			glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 
		        0, GL_RGBA, m_Width, m_Height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			
			buffer = null;
		}
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	private void loadModelTexture(String name){
		glBindTexture(GL_TEXTURE_2D, m_ID);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		
		ByteBuffer buffer = getImageData(name);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, m_Width, m_Height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		glGenerateMipmap(GL_TEXTURE_2D);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_LOD_BIAS, 0.0f);
		float amount = Math.min(4.0f, GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
		glTexParameterf(GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);

		buffer = null;
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	private void loadGUITexture(String name){
		glBindTexture(GL_TEXTURE_2D, m_ID);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		ByteBuffer buffer = getImageData(name);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, m_Width, m_Height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		buffer = null;
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	private ByteBuffer getImageData(String name){
		BufferedImage img = ImageManager.getImage(name);

		m_Width  = img.getWidth();
		m_Height = img.getHeight();
		
		int[] pixels = new int[m_Width * m_Height];
		
		img.getRGB(0, 0, m_Width, m_Height, pixels, 0, m_Width);
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(m_Width * m_Height * 4);
		boolean hasAlpha = img.getColorModel().hasAlpha();
		
		for(int y = 0; y < m_Height; y++){
			for(int x = 0; x < m_Width; x++){
				int pixelData = pixels[x + y * m_Width];
				buffer.put((byte) ((pixelData >> 16) & 0xFF));
				buffer.put((byte) ((pixelData >> 8) & 0xFF));
				buffer.put((byte) (pixelData & 0xFF));
				if(hasAlpha)
					buffer.put((byte) ((pixelData >> 24) & 0xFF));
				else buffer.put((byte) 0xFF);
			}
		}
		
		buffer.flip();
		
		return buffer;
	}
	
	public void bind(){
		bind(0);
	}
	
	public void bind(int samplerSlot){
		assert(samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, m_ID);
	}
	
	public void unbind(){
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void deleteTexture(){
		glDeleteTextures(m_ID);
	}
	
	public int getID() {
		return m_ID;
	}

	public int getWidth() {
		return m_Width;
	}

	public int getHeight() {
		return m_Height;
	}
}
