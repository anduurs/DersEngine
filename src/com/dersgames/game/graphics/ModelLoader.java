package com.dersgames.game.graphics;

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

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

import com.dersgames.game.graphics.models.Model;
import com.dersgames.game.graphics.textures.Texture;

public class ModelLoader {
	
	private List<Integer> m_vaoIDs;
	private List<Integer> m_vboIDs;
	private List<Integer> m_TextureIDs;
	
	public ModelLoader(){
		m_vaoIDs = new ArrayList<Integer>();
		m_vboIDs = new ArrayList<Integer>();
		m_TextureIDs = new ArrayList<Integer>();
	}
	
	public Model loadToVAO(float[] positions, float[] texCoords, int[] indices){
		int vaoID = createVAO();
		bindIndexBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, texCoords);
		unbindVAO();
		return new Model(vaoID, indices.length);
	}
	
	public int loadTexture(String name){
		Texture texture = new Texture(name);
		int textureID = texture.getID();
		m_TextureIDs.add(textureID);
		return textureID;
	}
	
	public void cleanUp(){
		for(Integer i : m_vaoIDs)
			glDeleteVertexArrays(i);
		for(Integer i : m_vboIDs)
			glDeleteBuffers(i);
		for(Integer i : m_TextureIDs)
			glDeleteTextures(i);
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
	
	private Model loadObjFile(String fileName){
		
		
		return null;
	}
	
	private void storeDataInAttributeList(int attributeNumber, int numOfFloats, float[] data){
		int vboID = glGenBuffers();
		m_vboIDs.add(vboID);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, numOfFloats, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	private void bindIndexBuffer(int[] indices){
		int vboID = glGenBuffers();
		m_vboIDs.add(vboID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}

	private FloatBuffer storeDataInFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

}
