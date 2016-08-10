package com.dersgames.engine.graphics.models;

public class Model {
	
	private int m_vaoID;
	private int m_VertexCount;
	
	public Model(int vao, int vertexCount){
		m_vaoID = vao;
		m_VertexCount = vertexCount;
	}

	public int getVaoID() {
		return m_vaoID;
	}

	public int getVertexCount() {
		return m_VertexCount;
	}

}
