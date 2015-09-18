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

/**
 * The batch renderer class is responsible for "batching" all the sprites in the game
 * into one big buffer which will then be sent to the gpu in one go 
 * by mapping the buffer to a vertex buffer object.
 * 
 * To use the batchrenderer you will call 4 methods in the following order:
 * 
 * 1.) begin  - maps the vertexbuffer (cpu side) to the vertex buffer object (gpu side) 
 * 
 * 2.) submit - takes in a renderable object (a sprite) and extracts the vertex data and stores
 *              it in the vertexbuffer
 *  
 * 3.) end    - after you have submitted all the sprites you want rendered you call end which
 *              will unmap the buffers -> the vertex buffer object will be updated with the data 
 *              in the vertexbuffer (all vertex data of all sprites in the batch will be uploaded to the gpu)
 * 
 * 4.) flush -  The actual drawing is done here since all the data that is needed has been uploaded to the vbo
 * 
 * @author Anders
 *
 */
public class BatchRenderer {
	//the total number of sprites which can be contained in the batch
	private final int MAX_SPRITES;
	//the total number of indices in the batch
	private final int INDICES_SIZE;
	//the total size in bytes of the vertex buffer object
	private final int VBO_SIZE;
	
	//the vertex buffer object handle
	private int m_VBO;
	//the index buffer object handle
	private int m_IBO;
	//the vertex array object handle
	private int m_VAO;
	
	//keeps track of the current number of sprites in the batch
	private int m_SpriteCount;
	
	//the vertexbuffer which will hold all the vertex data of all sprites in the batch
	//this buffer will then be mapped to the vertex buffer object
	private FloatBuffer m_VertexBuffer;
	
	/**
	 * Default constructor which initilaizes a new BatchRenderer with a size
	 * of 10 000 sprites.
	 */
	public BatchRenderer(){
		this(10000);
	}
	
	/**
	 * Initiliazes a new BatchRenderer with a given size
	 * @param size the amount of sprites which the BatchRenderer can store
	 */
	public BatchRenderer(int size){
		MAX_SPRITES = size;
		//the total number of indices that can be stored
		INDICES_SIZE = MAX_SPRITES * 6;
		
		//the total size in bytes which can be stored in the vertex buffer object. 
		VBO_SIZE = MAX_SPRITES * Vertex.VERTEX_SIZE * 16;
		//create the vertexbuffer that will be mapped to the vertex buffer object
		m_VertexBuffer = BufferUtils.createFloatBuffer(VBO_SIZE);
		
		//Create the vertex buffer object but note that we only tell opengl how much
		//memory we need allocated on the gpu (video memory), we do not yet send actual data. 
		//That will be done with glMapBuffer later on. And since we will constantly update
		//the data in the vbo we give a hint to opengl via GL_DYNAMIC_DRAW, which will speed things up.
		m_VBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, m_VBO);
		glBufferData(GL_ARRAY_BUFFER, VBO_SIZE, null, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		
		//generate all the indices
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
		
		//create a indexbuffer to hold all index data
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices.length * 4);
		indexBuffer.put(indices);
		indexBuffer.flip();
		
		//create a index buffer object and send the all index data to the gpu
		m_IBO = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_IBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		indexBuffer = null;
		
		//create a vertex array object
		m_VAO = glGenVertexArrays();
		glBindVertexArray(m_VAO);
		
		//here we give a description of the vertex data to opengl
		//so it knows how to interpret it. 
		glBindBuffer(GL_ARRAY_BUFFER, m_VBO);
		//the first attribute we describe is the vertex position, it has index 0 which 
		//was decided in the creation of the shader, it consists of 3 floats and the total size in bytes
		//is given by multiplying the total number of floats in the vertex by the size of a float (4 bytes)
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 
				Vertex.VERTEX_SIZE * 4, 0);
		//the second attribute is the texture or uv coordinates whic has index 1, it consists of 2 floats
		//and the size of the vertex is the same (note that it doesnt ask for the size of the attribute, it wants
		//the total size of the vertex) , then we give an offset so that opengl knows that every 12 bytes 
		//it is a texture coordinates that is being read
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 
				Vertex.VERTEX_SIZE * 4, 12);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glBindVertexArray(0);
	}
	
	/**
	 * Maps the vertexbuffer to the vertex buffer object
	 */
	public void begin(){
		glBindBuffer(GL_ARRAY_BUFFER, m_VBO);
		m_VertexBuffer = glMapBufferRange(GL_ARRAY_BUFFER, 0, 
				VBO_SIZE, GL_MAP_WRITE_BIT | GL_MAP_UNSYNCHRONIZED_BIT).asFloatBuffer();
	}
	
	/**
	 * Extracts the vertex data from the given renderable (sprite) and stores
	 * it in the vertexbuffer
	 * @param renderable the sprite you want to batch
	 */
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
	
	/**
	 * Unmapps the buffers which means that the data stored in the vertexbuffer
	 * will be uploaded to the gpu (the vertex buffer object will receive all vertex data)
	 */
	public void end(){
		m_VertexBuffer.flip();
		
		glUnmapBuffer(GL_ARRAY_BUFFER);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * Draws the batch
	 */
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
