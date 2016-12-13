package com.dersgames.examplegame.entities;

import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.components.lights.PointLight;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.Material;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.models.TexturedMesh;
import com.dersgames.engine.graphics.textures.TextureAtlas;

public class PointLightLamp extends Entity{
	
	private PointLight m_PointLight;
	
	public PointLightLamp(String tag, float x, float y, float z){
		super(tag, x, y, z);
	
		Entity entity = new Entity("PointLight1", x, y + 25.0f, z);
		m_PointLight = new PointLight("PointLight4", new Vector3f(2.0f, 2.0f, 2.0f), 
												     new Vector3f(1.0f, 0.01f, 0.002f),
												     2.0f,
												     500.0f);
		
		m_PointLight.speed = 0;
		entity.addComponent(m_PointLight);
		
		TextureAtlas texture = new TextureAtlas(Loader.loadModelTexture("lamp"), 1);
		Material material = new Material(texture, 1.0f, 0, 0, 1.0f,
				RenderEngine.getEntityRenderer().getShader()); 
		TexturedMesh lampMesh = new TexturedMesh(Loader.loadObjFile("lamp"), material); 
		
		addComponent(new StaticMesh("LampMesh", lampMesh));
	}

	public PointLight getPointLight() {
		return m_PointLight;
	}

}
