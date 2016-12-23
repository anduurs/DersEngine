package com.dersgames.engine.graphics.gui;

import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.core.Debug;
import com.dersgames.engine.core.Matrix4f;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.Window;
import com.dersgames.engine.graphics.textures.Texture;

public class GUIComponent extends Renderable{
	
	protected Texture m_Texture;
	protected Vector3f m_Color;
	protected boolean m_UsingColor;
	
	public GUIComponent(String tag, Texture texture) {
		super(tag);
		m_Texture = texture;
		m_Color = new Vector3f(0,0,0);
		m_UsingColor = false;
	}
	
	public GUIComponent(String tag, Vector3f color) {
		super(tag);
		m_Color = color;
		m_UsingColor = true;
	}
	
	public void update(float dt){
//		getPosition().x *= 1.001f;
//		getScale().x = getScale().x * 1.01f;
	}

	@Override
	public void render(RenderEngine renderer) {
		renderer.submit(this);
	}
	
	public boolean isUsingColor(){
		return m_UsingColor;
	}
	
	public Vector3f getColor(){
		return m_Color;
	}
	
	public Texture getTexture(){
		return m_Texture;
	}

}
