package com.dersgames.examplegame.entities;

import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.core.Transform;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.models.TexturedModel;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.examplegame.components.DragonMovement;

public class Dragon extends Entity{
	
	public Dragon(String tag, Transform transform){
		super(tag, transform);
		
		Texture dragonTexture  = new Texture(Loader.loadModelTexture("dragontexture"), 1);
		TexturedModel dragonMesh    = new TexturedModel(Loader.loadModelFromObjFile("dragon", false), 
						                                new Material(dragonTexture, 
																   new Vector3f(0.6f, 0.6f, 0.6f),
																   new Vector3f(0.5f, 0.5f, 0.5f),
																   new Vector3f(0.01f, 0.01f, 0.01f),
																   16.0f,
																   RenderEngine.getEntityRenderer().getShader())); 
		
		addComponent(new StaticMesh("DragonStaticMesh", dragonMesh));
		addComponent(new DragonMovement("DragonMovement", 10.0f));
	}

}
