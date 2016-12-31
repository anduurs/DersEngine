package com.dersgames.examplegame.entities;

import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.models.TexturedModel;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.maths.Vector3f;

public class LampPost extends Entity{
	
	public LampPost(String tag, Vector3f pos){
		super(tag, pos.x, pos.y, pos.z);

		Texture texture = new Texture(Loader.loadModelTexture("lamp"), 1);
		Material material = new Material(texture, 1.0f, 0.1f, 0.02f, 1.0f,
				RenderEngine.getEntityRenderer().getShader()); 
		TexturedModel lampMesh = new TexturedModel(Loader.loadModelFromObjFile("lamp", false), material); 
		
		addComponent(new StaticMesh("LampMesh", lampMesh));
	}
}
