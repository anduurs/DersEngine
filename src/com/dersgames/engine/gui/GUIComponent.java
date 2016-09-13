package com.dersgames.engine.gui;

import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.core.Debug;
import com.dersgames.engine.core.Scene;
import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.Material;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.models.TexturedMesh;

public class GUIComponent extends Renderable{
	
	private int m_Width;
	private int m_Height;
	
	private Loader m_Loader;

	public GUIComponent(String tag, Loader loader, Material material, int width, int height) {
		this(tag, loader, material, width, height, 0);
	}
	
	public GUIComponent(String tag, Loader loader, Material material, int width, int height, int index) {
		super(tag);
		
		m_Material = material;
		m_TextureIndex = index;
		
		m_Width = width;
		m_Height = height;
		
		m_Loader = loader;
		
		float[] vertices = {0 , 0, 0,
							0, 0 + m_Height, 0,
							0 + m_Width, 0 + m_Height, 0,
							0 + m_Width, 0, 0};

		float[] texCoords = {0,0,
				             0,1,
				             1,1,
				             1,0};

		int[] indices = {0,1,2, 
					    2,3,0};

		m_TexturedMesh = new TexturedMesh(m_Loader.loadMesh(vertices, texCoords, indices), m_Material);
	}
	
	@Override
	public void render(RenderEngine renderer) {
		float x = getEntity().getPosition().x + Scene.getCamera().getPosition().x;
		float y = getEntity().getPosition().y + Scene.getCamera().getPosition().y;
		float z = getEntity().getPosition().z + Scene.getCamera().getPosition().z;
		getEntity().getTransform().translate(x, y, z);

		renderer.submit(this);
	}

}
