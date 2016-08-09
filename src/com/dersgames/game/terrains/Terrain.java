package com.dersgames.game.terrains;

import com.dersgames.game.components.Renderable3D;
import com.dersgames.game.graphics.ModelLoader;
import com.dersgames.game.graphics.Renderer3D;
import com.dersgames.game.graphics.models.Model;
import com.dersgames.game.graphics.textures.TerrainTexture;
import com.dersgames.game.graphics.textures.TerrainTexturePack;

public class Terrain extends Renderable3D{
	
	private static final float SIZE = 800;
	private static final int VERTEX_COUNT = 128;
	
	private float x, z;
	private Model m_Model;

	private TerrainTexturePack m_TexturePack;
	private TerrainTexture m_BlendMap;
	
	public Terrain(String tag, int gridX, int gridY, 
			ModelLoader loader, TerrainTexturePack texturePack, TerrainTexture blendMap){
		super(tag);
		m_TexturePack = texturePack;
		m_BlendMap = blendMap;
		x = gridX * SIZE;
		z = gridY * SIZE;
		m_Model = generateTerrain(loader);
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void render(Renderer3D renderer) {
		renderer.submit(this);
	}
	
	private Model generateTerrain(ModelLoader loader){
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer*3+1] = 0;
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
