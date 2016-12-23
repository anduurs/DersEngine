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
import com.dersgames.engine.graphics.textures.lightingmaps.NormalMap;
import com.dersgames.engine.graphics.textures.lightingmaps.SpecularMap;
import com.dersgames.examplegame.components.BarrelMovement;

public class Barrel extends Entity{
	
	public Barrel(String tag, Transform transform){
		super(tag, transform);
		
		Texture barrelTexture = new Texture(Loader.loadModelTexture("barrel"));
		SpecularMap barrelSpecularMap  = new SpecularMap(Loader.loadModelTexture("barrelSpecularMap"));
		NormalMap barrelNormalMap = new NormalMap(Loader.loadModelTexture("barrelNormalMap"));
		
		TexturedModel barrelMesh   = new TexturedModel(Loader.loadModelFromObjFile("barrel", true), 
						                               new Material(barrelTexture, barrelSpecularMap, barrelNormalMap,
																   new Vector3f(0.6f, 0.6f, 0.6f),
																   new Vector3f(0.8f, 0.8f, 0.8f),
																   new Vector3f(0.02f, 0.02f, 0.02f),
																   8.0f, false, false,
																   RenderEngine.getEntityRenderer().getShader())); 
		
		addComponent(new StaticMesh("BarrelStaticMesh", barrelMesh));
		addComponent(new BarrelMovement("BarrelMovement", 15.0f));
	}

}
