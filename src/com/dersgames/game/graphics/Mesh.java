package com.dersgames.game.graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import com.dersgames.game.core.Vertex;

public class Mesh {
	
	private int m_VBO;
	private int m_IBO;
	private int m_VAO;
	
	private int m_Indices;
	
	public Mesh(Vertex[] vertices, int[] indices){
		m_Indices = indices.length;
		FloatBuffer vertexBuffer = 
				BufferUtils.createFloatBuffer(vertices.length * Vertex.VERTEX_SIZE * 4);
		
		for(int i = 0; i < vertices.length; i++){
			vertexBuffer.put(vertices[i].getPosition().x);
			vertexBuffer.put(vertices[i].getPosition().y);
			vertexBuffer.put(vertices[i].getPosition().z);
			
			vertexBuffer.put(vertices[i].getTexCoord().x);
			vertexBuffer.put(vertices[i].getTexCoord().y);
		}
		
		vertexBuffer.flip();
		
		m_VBO = glGenBuffers();
		
		glBindBuffer(GL_ARRAY_BUFFER, m_VBO);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices.length * 4);
		indexBuffer.put(indices);
		indexBuffer.flip();
		
		m_IBO = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_IBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		m_VAO = glGenVertexArrays();
		
		glBindVertexArray(m_VAO);
		glBindBuffer(GL_ARRAY_BUFFER, m_VBO);
		
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 
				Vertex.VERTEX_SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 
				Vertex.VERTEX_SIZE * 4, 12);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	public void render(){
		glBindVertexArray(m_VAO);
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_IBO);
		glDrawElements(GL_TRIANGLES, m_Indices, GL_UNSIGNED_INT, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		
		glBindVertexArray(0);
	}

}
