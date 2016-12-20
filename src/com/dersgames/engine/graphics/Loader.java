package com.dersgames.engine.graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;

import com.dersgames.engine.core.Debug;
import com.dersgames.engine.core.Vector2f;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.core.Vertex;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.textures.TextureData;
import com.dersgames.engine.graphics.textures.TextureData.TextureType;

public class Loader{
	
	private static List<Integer> m_vaoIDs;
	private static List<Integer> m_vboIDs;
	private static List<Integer> m_TextureIDs;
	
	public Loader(){
		m_vaoIDs = new ArrayList<Integer>();
		m_vboIDs = new ArrayList<Integer>();
		m_TextureIDs = new ArrayList<Integer>();
	}
	
	public static Model loadModel(float[] positions, float[] textureCoords, float[] normals, float[] tangents, int[] indices){
		int vaoID = createVAO();
		createIndexBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normals);
		storeDataInAttributeList(3, 3, tangents);
		unbindVAO();
		return new Model(vaoID, indices.length);
	}
	
	public static Model loadModel(float[] positions, float[] textureCoords, float[] normals, int[] indices){
		int vaoID = createVAO();
		createIndexBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normals);
		unbindVAO();
		return new Model(vaoID, indices.length);
	}
	
	public static Model loadModel(float[] positions, float[] textureCoords, int[] indices){
		int vaoID = createVAO();
		createIndexBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		unbindVAO();
		return new Model(vaoID, indices.length);
	}
	
	public static Model loadModelFromObjFile(String fileName, boolean calculateTangents){
		List<Vector3f> vertexPositions = new ArrayList<>(); 
		List<Vector2f> texCoords = new ArrayList<>(); 
		List<Vector3f> normals = new ArrayList<>();
		
		List<Vector3f> vertexPositionsUnique = new ArrayList<>(); 
		List<Vector2f> texCoordsUnique = new ArrayList<>(); 
		List<Vector3f> normalsUnique = new ArrayList<>();
		List<Vector3f> tangents = new ArrayList<>();
		
		List<Vertex> vertices = new ArrayList<>();
		List<Integer> indices = new ArrayList<>();
		
		Map<String, Integer> indexMap = new HashMap<>();
		
		int totalIndex = 0;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("res/models/" + fileName + ".obj"));
			String line;
			while((line = reader.readLine()) != null){
				String[] lineArray = line.split("\\s+");
				
				if(lineArray[0].equals("v")){
					float x = Float.valueOf(lineArray[1]);
					float y = Float.valueOf(lineArray[2]);
					float z = Float.valueOf(lineArray[3]);
					vertexPositions.add(new Vector3f(x, y, z));
				}else if(lineArray[0].equals("vt")){
					float x = Float.valueOf(lineArray[1]);
					float y = Float.valueOf(lineArray[2]);
					texCoords.add(new Vector2f(x, y));
				}else if(lineArray[0].equals("vn")){
					float x = Float.valueOf(lineArray[1]);
					float y = Float.valueOf(lineArray[2]);
					float z = Float.valueOf(lineArray[3]);
					normals.add(new Vector3f(x, y, z));
				}else if(lineArray[0].equals("f")){
					String[] v0Indices = lineArray[1].split("/");
					String[] v1Indices = lineArray[2].split("/");
					String[] v2Indices = lineArray[3].split("/");
					
					Vertex v0 = generateVertex(v0Indices, vertexPositions, texCoords, normals);
					Vertex v1 = generateVertex(v1Indices, vertexPositions, texCoords, normals);
					Vertex v2 = generateVertex(v2Indices, vertexPositions, texCoords, normals);
					
					Vector3f tangent = null;
					
					if(calculateTangents)
						tangent = calculateTangent(v0, v1, v2);
					
					for(int i = 1; i < lineArray.length; i++){
						String[] vertexIndices = lineArray[i].split("/");
						
						int posIndex = Integer.parseInt(vertexIndices[0]) - 1;
						int texIndex = Integer.parseInt(vertexIndices[1]) - 1;
						int normIndex = Integer.parseInt(vertexIndices[2]) - 1;
						
						String key = posIndex + "" + texIndex + "" + normIndex;
						
						if(!indexMap.containsKey(key)){
							Vector3f vPos = vertexPositions.get(posIndex);
							Vector2f texCoord = texCoords.get(texIndex);
							Vector3f normal = normals.get(normIndex);
							
							vertices.add(new Vertex(vPos, normal, texCoord));
							
							vertexPositionsUnique.add(vPos);
							texCoordsUnique.add(texCoord);
							normalsUnique.add(normal);
							
							if(calculateTangents)
								tangents.add(tangent);
							
							indices.add(totalIndex);
							indexMap.put(key, totalIndex++);
						}else indices.add(indexMap.get(key));
					}
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		float[] verticesArray = new float[vertexPositionsUnique.size() * 3];
		float[] texCoordsArray = new float[texCoordsUnique.size() * 2];
		float[] normalsArray = new float[normalsUnique.size() * 3];
		float[] tangentsArray = new float[tangents.size() * 3];
		
//		Debug.log("VertexPositions: " + vertexPositionsUnique.size());
//		Debug.log("TextureCoordinates: " + texCoordsUnique.size());
//		Debug.log("Normals: " + normalsUnique.size());
//		Debug.log("Tangents: " + tangents.size());
		
		int offset = 0;
		
		for(Vector3f v : vertexPositionsUnique){
			verticesArray[offset++] = v.x;
			verticesArray[offset++] = v.y;
			verticesArray[offset++] = v.z;
		}
		
		offset = 0;
		
		for(Vector2f v : texCoordsUnique){
			texCoordsArray[offset++] = v.x;
			texCoordsArray[offset++] = 1 - v.y;
		}
		
		offset = 0;
		
		for(Vector3f v : normalsUnique){
			normalsArray[offset++] = v.x;
			normalsArray[offset++] = v.y;
			normalsArray[offset++] = v.z;
		}
		
		if(calculateTangents){
			offset = 0;
			
			for(Vector3f v : tangents){
				tangentsArray[offset++] = v.x;
				tangentsArray[offset++] = v.y;
				tangentsArray[offset++] = v.z;
			}
		}
		
		
		int[] indicesArray = new int[indices.size()];
		
		for(int i = 0; i < indicesArray.length; i++)
			indicesArray[i] = indices.get(i);
		
		Model model = null;
		
		if(calculateTangents)
			model = loadModel(verticesArray, texCoordsArray, normalsArray, tangentsArray, indicesArray);
		else model = loadModel(verticesArray, texCoordsArray, normalsArray, indicesArray);
		
		return model;
	}
	
	private static Vertex generateVertex(String[] indexData, List<Vector3f> positions, 
			List<Vector2f> texCoords, List<Vector3f> normals){
		
		int posIndex = Integer.parseInt(indexData[0]) - 1;
		int textureIndex = Integer.parseInt(indexData[1]) - 1;
		int normalIndex = Integer.parseInt(indexData[2]) - 1;
		
		Vector3f pos = positions.get(posIndex);
		Vector2f texCoord = texCoords.get(textureIndex);
		Vector3f normal = normals.get(normalIndex);
		
		return new Vertex(pos, normal, texCoord);
	}
	
	private static Vector3f calculateTangent(Vertex v0, Vertex v1, Vertex v2){
		Vector3f edge1 = v1.getPosition().sub(v0.getPosition());
		Vector3f edge2 = v2.getPosition().sub(v0.getPosition());
		
		float deltaU1 = v1.getTexCoords().x - v0.getTexCoords().x;
		float deltaV1 = v1.getTexCoords().y - v0.getTexCoords().y;
		float deltaU2 = v2.getTexCoords().x - v0.getTexCoords().x;
		float deltaV2 = v2.getTexCoords().y - v0.getTexCoords().y;
		
		float f = 1.0f / (deltaU1 * deltaV2 - deltaU2 * deltaV1);
	
		Vector3f tangent = new Vector3f(f * (deltaV2 * edge1.x - deltaV1 * edge2.x),
										f * (deltaV2 * edge1.y - deltaV1 * edge2.y),
										f * (deltaV2 * edge1.z - deltaV1 * edge2.z));
		
		return tangent;
	}
	
	public static int loadModelTexture(String name){
		TextureData texture = new TextureData(name, TextureType.MODEL);
		int textureID = texture.getID();
		m_TextureIDs.add(textureID);
		return textureID;
	}
	
	public static int loadGUITexture(String name){
		TextureData texture = new TextureData(name, TextureType.GUI);
		int textureID = texture.getID();
		m_TextureIDs.add(textureID);
		return textureID;
	}
	
	public static void dispose(){
		for(Integer i : m_vaoIDs)
			glDeleteVertexArrays(i);
		for(Integer i : m_vboIDs)
			glDeleteBuffers(i);
		for(Integer i : m_TextureIDs)
			glDeleteTextures(i);
	}
		
	private static int createVAO(){
		int vaoID = glGenVertexArrays();
		m_vaoIDs.add(vaoID);
		glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private static void unbindVAO(){
		glBindVertexArray(0);
	}
	
	private static void storeDataInAttributeList(int attributeNumber, int numOfFloats, float[] data){
		int vboID = glGenBuffers();
		m_vboIDs.add(vboID);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, numOfFloats, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	private static void createIndexBuffer(int[] indices){
		int vboID = glGenBuffers();
		m_vboIDs.add(vboID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}

	private static FloatBuffer storeDataInFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private static IntBuffer storeDataInIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

}
