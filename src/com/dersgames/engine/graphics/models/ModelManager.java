package com.dersgames.engine.graphics.models;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dersgames.engine.math.Vector2f;
import com.dersgames.engine.math.Vector3f;

public class ModelManager {
	
	private static ModelManager instance;
	
	private List<Integer> m_vaoIDs;
	private List<Integer> m_vboIDs;
	
	public int vertexCounter = 0;
	
	public static ModelManager getInstance() {
		if (instance == null) {
			instance = new ModelManager();
		}
		
		return instance;
	}
	
	private ModelManager() {
		m_vaoIDs = new ArrayList<Integer>();
		m_vboIDs = new ArrayList<Integer>();
	}
	
	public Model loadModel(float[] positions, float[] textureCoords, float[] normals, float[] tangents, int[] indices){
		vertexCounter += positions.length / 3;
		int vaoID = createVAO();
		m_vboIDs.add(ModelUtils.createIndexBuffer(indices));
		m_vboIDs.add(ModelUtils.storeDataInAttributeList(0, 3, positions));
		m_vboIDs.add(ModelUtils.storeDataInAttributeList(1, 2, textureCoords));
		m_vboIDs.add(ModelUtils.storeDataInAttributeList(2, 3, normals));
		m_vboIDs.add(ModelUtils.storeDataInAttributeList(3, 3, tangents));
		unbindVAO();
		return new Model(vaoID, indices.length);
	}

	public Model loadModel(float[] positions, float[] textureCoords, float[] normals, int[] indices){
		vertexCounter += positions.length / 3;
		int vaoID = createVAO();
		m_vboIDs.add(ModelUtils.createIndexBuffer(indices));
		m_vboIDs.add(ModelUtils.storeDataInAttributeList(0, 3, positions));
		m_vboIDs.add(ModelUtils.storeDataInAttributeList(1, 2, textureCoords));
		m_vboIDs.add(ModelUtils.storeDataInAttributeList(2, 3, normals));
		unbindVAO();
		return new Model(vaoID, indices.length);
	}

	public Model loadModel(float[] positions, float[] textureCoords, int[] indices){
		vertexCounter += positions.length / 3;
		int vaoID = createVAO();
		m_vboIDs.add(ModelUtils.createIndexBuffer(indices));
		m_vboIDs.add(ModelUtils.storeDataInAttributeList(0, 3, positions));
		m_vboIDs.add(ModelUtils.storeDataInAttributeList(1, 2, textureCoords));
		unbindVAO();
		return new Model(vaoID, indices.length);
	}

	public Model loadModel(float[] positions, int dimensions){
		vertexCounter += positions.length / 3;
		int vaoID = createVAO();
		m_vboIDs.add(ModelUtils.storeDataInAttributeList(0, dimensions, positions));
		unbindVAO();
		return new Model(vaoID, positions.length / dimensions);
	}

	public Model loadModelFromObjFile(String fileName, boolean usingNormalMap){
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
			InputStream in = Class.class.getResourceAsStream("/models/" + fileName + ".obj");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
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

					Vertex v0 = ModelUtils.generateVertex(v0Indices, vertexPositions, texCoords, normals);
					Vertex v1 = ModelUtils.generateVertex(v1Indices, vertexPositions, texCoords, normals);
					Vertex v2 = ModelUtils.generateVertex(v2Indices, vertexPositions, texCoords, normals);

					Vector3f tangent = null;

					if(usingNormalMap) {
						tangent = ModelUtils.calculateTangent(v0, v1, v2);
					}

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

							if(usingNormalMap)
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

		if(usingNormalMap){
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

		if(usingNormalMap)
			model = loadModel(verticesArray, texCoordsArray, normalsArray, tangentsArray, indicesArray);
		else model = loadModel(verticesArray, texCoordsArray, normalsArray, indicesArray);

		return model;
	}
	
	public void dispose(){
		for(Integer i : m_vaoIDs)
			glDeleteVertexArrays(i);
		for(Integer i : m_vboIDs)
			glDeleteBuffers(i);
	}
	
	private int createVAO(){
		int vaoID = glGenVertexArrays();
		m_vaoIDs.add(vaoID);
		glBindVertexArray(vaoID);
		return vaoID;
	}

	private void unbindVAO(){
		glBindVertexArray(0);
	}
}
