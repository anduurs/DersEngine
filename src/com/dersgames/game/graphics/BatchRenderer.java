package com.dersgames.game.graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.GL_MAP_UNSYNCHRONIZED_BIT;
import static org.lwjgl.opengl.GL30.GL_MAP_WRITE_BIT;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glMapBufferRange;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import com.dersgames.game.components.Renderable;
import com.dersgames.game.core.Vector3f;
import com.dersgames.game.core.Vertex;

public class BatchRenderer {
	
	private final int MAX_SPRITES;
	private final int INDICES_SIZE;
	private final int VBO_SIZE;
	
	private int m_VBO;
	private int m_IBO;
	private int m_VAO;
	
	//keeps track of the current number of sprites in the batch
	private int m_SpriteCount;
	
	private FloatBuffer m_VertexBuffer;
	
	public BatchRenderer(){
		this(10000);
	}
	
	public BatchRenderer(int size){
		MAX_SPRITES = size;
		INDICES_SIZE = MAX_SPRITES * 6;
		
		VBO_SIZE = MAX_SPRITES * Vertex.VERTEX_SIZE * 16;
		m_VertexBuffer = BufferUtils.createFloatBuffer(VBO_SIZE);
		
		m_VBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, m_VBO);
		glBufferData(GL_ARRAY_BUFFER, VBO_SIZE, null, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		int[] indices = new int[INDICES_SIZE];
		int offset = 0;
		
		for(int i = 0; i < indices.length; i+=6){
			indices[i  ] = offset + 0;
			indices[i+1] = offset + 1;
			indices[i+2] = offset + 2;
			indices[i+3] = offset + 2;
			indices[i+4] = offset + 3;
			indices[i+5] = offset + 0;
			
			offset += 4;
		}
		
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices.length * 4);
		indexBuffer.put(indices);
		indexBuffer.flip();
		
		m_IBO = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_IBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		indexBuffer = null;
		
		m_VAO = glGenVertexArrays();
		glBindVertexArray(m_VAO);
		
		glBindBuffer(GL_ARRAY_BUFFER, m_VBO);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 
				Vertex.VERTEX_SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 
				Vertex.VERTEX_SIZE * 4, 12);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glBindVertexArray(0);
	}
	
	public void begin(){
		glBindBuffer(GL_ARRAY_BUFFER, m_VBO);
		m_VertexBuffer = glMapBufferRange(GL_ARRAY_BUFFER, 0, 
				VBO_SIZE, GL_MAP_WRITE_BIT | GL_MAP_UNSYNCHRONIZED_BIT).asFloatBuffer();
	}
	
	public void submit(Renderable renderable){
		Vertex[] vertices = renderable.getVertices();
		
		Vector3f pos = new Vector3f(0,0,0);
		
		for(int i = 0; i < vertices.length; i++){
			pos.x = vertices[i].getPosition().x;
			pos.y = vertices[i].getPosition().y;
			pos.z = vertices[i].getPosition().z;
			
			Vector3f v = renderable.getGameObject().
					getTransform().getModelMatrix().mul(pos);
			
			m_VertexBuffer.put(v.x);
			m_VertexBuffer.put(v.y);
			m_VertexBuffer.put(v.z);
			
			m_VertexBuffer.put(vertices[i].getTexCoord().x);
			m_VertexBuffer.put(vertices[i].getTexCoord().y);
		}
		
		m_SpriteCount++;
	}
	
	public void end(){
		m_VertexBuffer.flip();
		
		glUnmapBuffer(GL_ARRAY_BUFFER);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void flush(){
		glBindVertexArray(m_VAO);
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_IBO);
		glDrawElements(GL_TRIANGLES, m_SpriteCount * 6, GL_UNSIGNED_INT, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		
		glBindVertexArray(0);
		
		m_SpriteCount = 0;
	}
	

}
