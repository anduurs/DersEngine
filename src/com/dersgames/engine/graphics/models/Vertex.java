package com.dersgames.engine.graphics.models;

import com.dersgames.engine.maths.Vector2f;
import com.dersgames.engine.maths.Vector3f;

public class Vertex {
	
	private Vector3f m_Position;
	private Vector3f m_Normal;
	private Vector2f m_TexCoord;
	
	//total number of floats in a vertex
	public static final int VERTEX_SIZE = 8;
	
	public Vertex(Vector3f position){
		m_Position = position;
	}
	
	public Vertex(Vector3f position, Vector2f texCoord){
		m_Position = position;
		m_TexCoord = texCoord;
	}
	
	public Vertex(Vector3f position, Vector3f normal, Vector2f texCoord){
		m_Position = position;
		m_Normal = normal;
		m_TexCoord = texCoord;
	}

	public Vector3f getPosition() {
		return m_Position;
	}
	
	public Vector3f getNormal() {
		return m_Normal;
	}
	
	public Vector2f getTexCoords() {
		return m_TexCoord;
	}

}
