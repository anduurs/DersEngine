package com.dersgames.engine.graphics.models;

public class Model {
	
	private int m_VaoID;
	private int m_VertexCount;
	
	public Model(int vaoID, int vertexCount){
		m_VaoID = vaoID;
		m_VertexCount = vertexCount;
	}

	public int getVaoID() {
		return m_VaoID;
	}

	public int getVertexCount() {
		return m_VertexCount;
	}

}
