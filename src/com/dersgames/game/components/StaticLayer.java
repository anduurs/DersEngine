package com.dersgames.game.components;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import com.dersgames.game.core.Vector2f;
import com.dersgames.game.core.Vector3f;
import com.dersgames.game.core.Vertex;
import com.dersgames.game.graphics.ColorRGBA;
import com.dersgames.game.graphics.Mesh;
import com.dersgames.game.graphics.TextureRegion;
import com.dersgames.game.graphics.shaders.Shader;
import com.dersgames.game.utils.ImageLoader;

public class StaticLayer extends Renderable{
	
	private int m_Width;
	private int m_Height;
	private int m_TextureSize;
	
	private int[] m_Pixels;
	
	private Shader m_Shader;
	private Mesh m_Mesh;
	private HashMap<Integer, TextureRegion> m_ColorToTextureMap;
	
	public StaticLayer(String tag, int width, int height, int textureSize, Shader shader){
		super(tag);
		
		m_Static = true;
		m_Width = width;
		m_Height = height;
		m_TextureSize = textureSize;
		m_Shader = shader;
		
		m_ColorToTextureMap = new HashMap<Integer, TextureRegion>();
		m_Pixels = new int[width * height];
		
		for(int i = 0; i < m_Pixels.length; i++)
			m_Pixels[i] = ColorRGBA.GREEN;
	}
	
	public StaticLayer(String tag, String name, int textureSize, Shader shader){
		super(tag);
		
		BufferedImage img = ImageLoader.getImage(name);
		
		m_Static = true;
		m_Width = img.getWidth();
		m_Height = img.getHeight();
		m_TextureSize = textureSize;
		m_Shader = shader;
		
		m_ColorToTextureMap = new HashMap<Integer, TextureRegion>();
		m_Pixels = new int[m_Width * m_Height];
		
		img.getRGB(0, 0, m_Width, m_Height, m_Pixels, 0, m_Width);
	}
	
	public void generateLayer(){
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		float xOffset = 7f;
		float yOffset = 5f;
		
		for(int y = 0; y < m_Height; y++){
			for(int x = 0; x < m_Width; x++){
				int index = x + y * m_Width;
				
				if(m_Pixels[index] == -16777216) continue;
				
			
				
				float xLow  = m_ColorToTextureMap.get(m_Pixels[index]).u1;
				float xHigh = m_ColorToTextureMap.get(m_Pixels[index]).u2;
				float yLow  = m_ColorToTextureMap.get(m_Pixels[index]).v1;
				float yHigh = m_ColorToTextureMap.get(m_Pixels[index]).v2;
				
				float xPos = x * m_TextureSize;
				float yPos = y * m_TextureSize;
				
				indices.add(vertices.size() + 0);
				indices.add(vertices.size() + 1);
				indices.add(vertices.size() + 2);
				indices.add(vertices.size() + 2);
				indices.add(vertices.size() + 3);
				indices.add(vertices.size() + 0);
				
				vertices.add(new Vertex(new Vector3f(xPos, yPos , 0), new Vector2f(xLow, yLow)));
				vertices.add(new Vertex(new Vector3f(xPos, yPos + m_TextureSize + yOffset, 0), new Vector2f(xLow, yHigh)));
				vertices.add(new Vertex(new Vector3f(xPos + m_TextureSize + xOffset, 
						yPos + m_TextureSize + yOffset, 0), new Vector2f(xHigh, yHigh)));
				vertices.add(new Vertex(new Vector3f(xPos + m_TextureSize + xOffset, yPos, 0), new Vector2f(xHigh, yLow)));
			}
		}
		
		Vertex[] vertexDataArray = new Vertex[vertices.size()];
		vertexDataArray = vertices.toArray(vertexDataArray);
		
		int[] indexDataArray = new int[indices.size()];
		
		for(int i = 0; i < indexDataArray.length; i++)
			indexDataArray[i] = indices.get(i);
		
		m_Mesh = new Mesh(vertexDataArray, indexDataArray);
	}
	
	public void addTexture(int colorID, TextureRegion region){
		m_ColorToTextureMap.put(colorID, region);
	}
	
	public void render(){
		//m_GameObject.getTransform().translate(0, 0, 0);
		m_Shader.setUniformi("is_Static", 1);
		m_Shader.setUniform("model_matrix", m_GameObject.getTransform().getModelMatrix());
		m_Mesh.render();
	}
	
}
