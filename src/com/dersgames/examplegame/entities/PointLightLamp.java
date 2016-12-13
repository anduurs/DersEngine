package com.dersgames.examplegame.entities;

import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.components.lights.PointLight;
import com.dersgames.engine.core.Debug;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.Material;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.models.TexturedMesh;
import com.dersgames.engine.graphics.textures.TextureAtlas;
import com.dersgames.engine.utils.Randomizer;

public class PointLightLamp extends Entity{
	
	private PointLight m_PointLight;
	
	public PointLightLamp(String tag, float x, float y, float z){
		super(tag, x, y, z);
		
		int randomNum = Randomizer.getInt(0, 3);
		Vector3f color = new Vector3f(0,0,0);
		if(randomNum == 0){
			color = new Vector3f(Randomizer.getFloat(0,1), Randomizer.getFloat(0,1), Randomizer.getFloat(0,1));
		}else if(randomNum == 1){
			color = new Vector3f(Randomizer.getFloat(0,1), Randomizer.getFloat(0,1), Randomizer.getFloat(0,1));
		}else if(randomNum == 2){
			color = new Vector3f(Randomizer.getFloat(0,1), Randomizer.getFloat(0,1), Randomizer.getFloat(0,1));
		}
		
		Entity entity = new Entity("PointLight1", x, y, z);
		m_PointLight = new PointLight("PointLight4", color , 
												     new Vector3f(0, 0.014f, 0.0007f),
												     1.8f,
												     500.0f);
		
		m_PointLight.speed = Randomizer.getFloat(0,1);
		entity.addComponent(m_PointLight);
		
		TextureAtlas texture = new TextureAtlas(Loader.loadModelTexture("lamp"), 1);
		Material material = new Material(texture, 1.0f, 0, 0f, 0f,
				RenderEngine.getEntityRenderer().getShader()); 
		TexturedMesh lampMesh = new TexturedMesh(Loader.loadObjFile("lamp"), material); 
		
//		addComponent(new StaticMesh("LampMesh", lampMesh));
	}

	public PointLight getPointLight() {
		return m_PointLight;
	}

}
