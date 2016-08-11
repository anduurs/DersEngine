package com.dersgames.engine.terrains;

import java.awt.image.BufferedImage;

import com.dersgames.engine.components.Renderable3D;
import com.dersgames.engine.graphics.ModelLoader;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.renderers.Renderer3D;
import com.dersgames.engine.graphics.textures.TerrainTexture;
import com.dersgames.engine.graphics.textures.TerrainTexturePack;
import com.dersgames.engine.utils.ImageManager;

public class Terrain extends Renderable3D{
	
	private static final float SIZE = 800;
	private static final int MAX_HEIGHT = 40;
	private static final int MAX_PIXEL_COLOR = 256 * 256 * 256;
	
	private float x, z;
	private Model m_Model;

	private TerrainTexturePack m_TexturePack;
	private TerrainTexture m_BlendMap;
	
	public Terrain(String tag, int gridX, int gridY, 
			ModelLoader loader, TerrainTexturePack texturePack, TerrainTexture blendMap, String heightmap){
		super(tag);
		m_TexturePack = texturePack;
		m_BlendMap = blendMap;
		x = gridX * SIZE;
		z = gridY * SIZE;
		m_Model = generateTerrain(loader, heightmap);
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void render(Renderer3D renderer) {
		renderer.submit(this);
	}
	
	private Model generateTerrain(ModelLoader loader, String heightmap){
		BufferedImage image = ImageManager.getImage(heightmap);
		int VERTEX_COUNT = image.getHeight();
		
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer*3+1] = getHeight(j, i, image);
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				normals[vertexPointer*3] = 0;
				normals[vertexPointer*3+1] = 1;
				normals[vertexPointer*3+2] = 0;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}
	
	private float getHeight(int x, int y, BufferedImage image){
		if(x < 0 || x >= image.getHeight() || y < 0 || y >= image.getHeight())
			return 0;
		
		float height = image.getRGB(x, y);
		
		//change the height so it lies in the range between -MAX_HEIGHT and MAX_HEIGHT
		height += MAX_PIXEL_COLOR / 2.0f;
		height /= MAX_PIXEL_COLOR / 2.0f;
		height *= MAX_HEIGHT;
		
		return height;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public Model getModel() {
		return m_Model;
	}

	public TerrainTexturePack getTexturePack() {
		return m_TexturePack;
	}

	public TerrainTexture getBlendMap() {
		return m_BlendMap;
	}
}
