package com.dersgames.examplegame.entities;

import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.core.Transform;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.Material;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.models.TexturedModel;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.graphics.textures.TextureAtlas;
import com.dersgames.examplegame.components.BarrelMovement;

public class Barrel extends Entity{
	
	public Barrel(String tag, Transform transform){
		super(tag, transform);
		
		TextureAtlas barrelTexture = new TextureAtlas(Loader.loadModelTexture("barrel"), 1);
		Texture barrelSpecularMap  = new Texture(Loader.loadGUITexture("barrelSpecularMap"));
		
		TexturedModel barrelMesh   = new TexturedModel(Loader.loadModelFromObjFile("barrel", false), 
						                               new Material(barrelTexture, barrelSpecularMap,
																   new Vector3f(0.6f, 0.6f, 0.6f),
																   new Vector3f(0.8f, 0.8f, 0.8f),
																   new Vector3f(0.0f, 0.0f, 0.0f),
																   10.0f,false,false,
																   RenderEngine.getEntityRenderer().getShader())); 
		
		addComponent(new StaticMesh("BarrelStaticMesh", barrelMesh));
		addComponent(new BarrelMovement("BarrelMovement", 15.0f));
	}

}
