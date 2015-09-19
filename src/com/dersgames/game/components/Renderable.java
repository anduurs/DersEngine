package com.dersgames.game.components;

import com.dersgames.game.core.Vertex;
import com.dersgames.game.graphics.BatchRenderer;

public class Renderable extends Component{
	
	protected boolean m_Static;
	
	protected Vertex[] m_Vertices;
	protected int m_Width, m_Height;
	
	public Renderable(String tag){
		super(tag);
	}
	
	@Override
	public void update(float dt) {}

	public void render(){
		
	}
	
	public void render(BatchRenderer batch){
		batch.submit(this);
	}
	
	public Vertex[] getVertices(){
		return m_Vertices;
	}

	public int getWidth() {
		return m_Width;
	}

	public int getHeight() {
		return m_Height;
	}

	public boolean isStatic(){
		return m_Static;
	}
	

}
