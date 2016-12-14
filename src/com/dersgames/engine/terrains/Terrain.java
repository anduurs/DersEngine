package com.dersgames.engine.terrains;

import java.awt.image.BufferedImage;

import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.core.Debug;
import com.dersgames.engine.core.Vector2f;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.models.Mesh;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.graphics.textures.TerrainTexturePack;
import com.dersgames.engine.utils.ImageManager;
import com.dersgames.engine.utils.MathUtil;

public class Terrain extends Renderable{
	
	private static final float SIZE = 800.0f;
	private static final int MAX_HEIGHT = 40;
	private static final int MAX_PIXEL_COLOR = 256 * 256 * 256;
	
	private float[][] m_Heights;
	
	private float x, z;
	private Mesh m_Mesh;

	private TerrainTexturePack m_TexturePack;
	private Texture m_BlendMap;
	
	public Terrain(String tag, int gridX, int gridZ, 
			Loader loader, TerrainTexturePack texturePack, Texture blendMap, String heightmap){
		super(tag);
		m_TexturePack = texturePack;
		m_BlendMap = blendMap;
		x = gridX * SIZE;
		z = gridZ * SIZE;
		m_Mesh = generateTerrain(loader, heightmap);
	}
	
	public Terrain(String tag, int gridX, int gridZ, 
			Loader loader, TerrainTexturePack texturePack, Texture blendMap){
		super(tag);
		m_TexturePack = texturePack;
		m_BlendMap = blendMap;
		x = gridX * SIZE;
		z = gridZ * SIZE;
		m_Mesh = generateTerrain(loader);
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void render(RenderEngine renderer) {
		renderer.submit(this);
	}
	
	private Mesh generateTerrain(Loader loader, String heightmap){
		BufferedImage image = ImageManager.getImage(heightmap);
		int VERTEX_COUNT = image.getHeight();
		
		m_Heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		
		int count = VERTEX_COUNT * VERTEX_COUNT;
		
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				
				float height = getHeight(j, i, image);
				m_Heights[j][i] = height;
				
				float x = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				float z = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				
				Vector3f normal = calculateNormal(j, i, image);
				
				vertices[vertexPointer*3]   = x;
				vertices[vertexPointer*3+1] = height;
				vertices[vertexPointer*3+2] = z;
				
				normals[vertexPointer*3]   = normal.x;
				normals[vertexPointer*3+1] = normal.y;
				normals[vertexPointer*3+2] = normal.z;
				
				textureCoords[vertexPointer*2]   = (float)j/((float)VERTEX_COUNT - 1);
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
		
		return Loader.loadMesh(vertices, textureCoords, normals, indices);
	}
	
	private Mesh generateTerrain(Loader loader){
		int VERTEX_COUNT = 256;
		
		int count = VERTEX_COUNT * VERTEX_COUNT;
		
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				float x = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				float z = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				
				vertices[vertexPointer*3]   = x;
				vertices[vertexPointer*3+1] = 0;
				vertices[vertexPointer*3+2] = z;
	
				normals[vertexPointer*3]   = 0;
				normals[vertexPointer*3+1] = 1;
				normals[vertexPointer*3+2] = 0;
				
				textureCoords[vertexPointer*2]   = (float)j/((float)VERTEX_COUNT - 1);
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
		
		return loader.loadMesh(vertices, textureCoords, normals, indices);
	}
	
	public float getHeightOfTerrain(float worldX, float worldZ){
		float terrainX = worldX - x;
		float terrainZ = worldZ - z;
		
		float gridSquareSize = SIZE / ((float) m_Heights.length - 1);
		
		//find the gridsquare xz-coordinates of the grid that the player is in
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		
		//check so the gridsquare is on the terrain
		if(gridX < 0 || gridX >= m_Heights.length - 1 || gridZ < 0 || gridZ >= m_Heights.length - 1)
			return 0;
		
		//find the xz-coordinates inside the gridsquare that the player is in
		float xPos = (terrainX % gridSquareSize);
		float zPos = (terrainZ % gridSquareSize);
		
		//get the height at the x-z coordinate that the pålayer is on inside the gridsquare
		//need to check two cases one for each triangle of the gridsquare that the player might be on
		float height = 0;
		if(xPos <= (1 - zPos)){
			Vector3f p1 = new Vector3f(0, m_Heights[gridX][gridZ], 0);
			Vector3f p2 = new Vector3f(1, m_Heights[gridX + 1][gridZ], 0);
			Vector3f p3 = new Vector3f(0, m_Heights[gridX][gridZ + 1], 1);;
			height = MathUtil.barryCentric(p1, p2, p3, new Vector2f(xPos, zPos));
		}else{
			Vector3f p1 = new Vector3f(1, m_Heights[gridX + 1][gridZ], 0);
			Vector3f p2 = new Vector3f(1, m_Heights[gridX + 1][gridZ + 1], 1);
			Vector3f p3 = new Vector3f(0, m_Heights[gridX][gridZ + 1], 1);
			height = MathUtil.barryCentric(p1, p2, p3, new Vector2f(xPos, zPos));
		}
		
		return height;
	}
	
	private Vector3f calculateNormal(int x, int z, BufferedImage image){
		float hL = getHeight(x-1, z, image);
		float hR = getHeight(x+1, z, image);
		float hD = getHeight(x, z-1, image);
		float hU = getHeight(x, z+1, image);
		
		return new Vector3f(hL-hR, 2.0f, hD-hU).normalize();
	}
	
	private float getHeight(int x, int z, BufferedImage image){
		if(x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight())
			return 0;
		
		float height = image.getRGB(x, z);
		
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

	public Mesh getMesh() {
		return m_Mesh;
	}

	public TerrainTexturePack getTexturePack() {
		return m_TexturePack;
	}

	public Texture getBlendMap() {
		return m_BlendMap;
	}
}
