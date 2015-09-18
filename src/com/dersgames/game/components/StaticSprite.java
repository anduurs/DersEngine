package com.dersgames.game.components;

import com.dersgames.game.core.Vector2f;
import com.dersgames.game.core.Vector3f;
import com.dersgames.game.core.Vertex;
import com.dersgames.game.graphics.BatchRenderer;
import com.dersgames.game.graphics.Mesh;
import com.dersgames.game.graphics.TextureRegion;
import com.dersgames.game.graphics.shaders.Shader;

public class StaticSprite extends Renderable{
	
	private Mesh m_Mesh;
	private Shader m_Shader;
	
	public StaticSprite(String tag, Vector3f pos, Shader shader, TextureRegion region){
		super(tag);
		
		m_Shader = shader;
		
		m_Width = (int)region.width;
		m_Height = (int)region.height;
		
		float xLow  = region.u1;
		float xHigh = region.u2;
		float yLow  = region.v1;
		float yHigh = region.v2;
		
		Vertex[] vertices = {new Vertex(new Vector3f(pos.x, pos.y, 0), 					     new Vector2f(xLow, yLow)), 
							 new Vertex(new Vector3f(pos.x, pos.y + m_Height, 0), 		     new Vector2f(xLow, yHigh)),
							 new Vertex(new Vector3f(pos.x + m_Width, pos.y + m_Height, 0),  new Vector2f(xHigh, yHigh)), 
							 new Vertex(new Vector3f(pos.x + m_Width, pos.y, 0), 			 new Vector2f(xHigh, yLow))};
		
		m_Vertices = new Vertex[vertices.length];
		m_Vertices = vertices;
		
		int[] indices = {0,1,2,2,3,0};
		
		m_Mesh = new Mesh(vertices, indices);
	}
	
	public void render(BatchRenderer batch){
		m_Shader.setUniform("model_matrix", m_GameObject.getTransform().getModelMatrix());
		m_Mesh.render();
	}

}
