package com.dersgames.engine.core;

public class Vertex {
	
	private Vector3f m_Position;
	private Vector2f m_TexCoord;
	
	//total number of floats in a vertex
	public static final int VERTEX_SIZE = 5;
	
	public Vertex(Vector3f position){
		this(position, new Vector2f(0,0));
	}
	
	public Vertex(Vector3f position, Vector2f texCoord){
		m_Position = position;
		m_TexCoord = texCoord;
	}

	public Vector3f getPosition() {
		return m_Position;
	}

	public Vector2f getTexCoord() {
		return m_TexCoord;
	}

}
