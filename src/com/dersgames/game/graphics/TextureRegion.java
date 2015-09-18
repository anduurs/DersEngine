package com.dersgames.game.graphics;

public class TextureRegion {
	
	public final float u1, v1;
	public final float u2, v2;
	
	public float width;
	public float height;
	
	private final Texture m_Texture;
	
	/**
	 * Defines a region in a given texture with a starting x and y coordinate (which are in pixels) 
	 * and the width and height of the region
	 * @param texture the texture atlas
	 * @param x the starting x coordinate of the region in pixels 
	 * @param y the starting y coordinate of the region in pixels
	 * @param width the width of the region
	 * @param height the height of the region
	 */
	public TextureRegion(Texture texture, float x, float y, float width, float height){
		this.width = width;
		this.height = height;
		
		u1 = x / texture.getWidth();
		v1 = y / texture.getHeight();
		u2 = u1 + width / texture.getWidth();
		v2 = v1 + height / texture.getHeight();
		
		m_Texture = texture;
	}

}
