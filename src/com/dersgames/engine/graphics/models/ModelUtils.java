package com.dersgames.engine.graphics.models;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;

import com.dersgames.engine.math.Vector2f;
import com.dersgames.engine.math.Vector3f;

public class ModelUtils {
	
	public static Vertex generateVertex(String[] indexData, List<Vector3f> positions,
			 List<Vector2f> texCoords, List<Vector3f> normals){

		int posIndex = Integer.parseInt(indexData[0]) - 1;
		int textureIndex = Integer.parseInt(indexData[1]) - 1;
		int normalIndex = Integer.parseInt(indexData[2]) - 1;
		
		Vector3f pos = positions.get(posIndex);
		Vector2f texCoord = texCoords.get(textureIndex);
		Vector3f normal = normals.get(normalIndex);
		
		return new Vertex(pos, normal, texCoord);
	}
	
	public static Vector3f calculateTangent(Vertex v0, Vertex v1, Vertex v2){
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

	public static int storeDataInAttributeList(int attributeNumber, int numOfFloats, float[] data){
		int vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, numOfFloats, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(attributeNumber);
		return vboID;
	}

	public static int createIndexBuffer(int[] indices){
		int vboID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		return vboID;
	}

	public static FloatBuffer storeDataInFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public static IntBuffer storeDataInIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
