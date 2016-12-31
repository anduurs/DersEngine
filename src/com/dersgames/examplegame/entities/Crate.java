package com.dersgames.examplegame.entities;

import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.core.Transform;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.models.TexturedModel;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.graphics.textures.lightingmaps.NormalMap;
import com.dersgames.engine.maths.Vector3f;
import com.dersgames.examplegame.components.CrateMovement;

public class Crate extends Entity{
	
	public Crate(String tag, Transform transform){
		super(tag, transform);
		
		Texture crateTexture = new Texture(Loader.loadModelTexture("crate"), 1);
		NormalMap crateNormalMap = new NormalMap(Loader.loadModelTexture("crateNormalMap"));
		
		TexturedModel crateMesh   = new TexturedModel(Loader.loadModelFromObjFile("crate", true), 
						                               new Material(crateTexture, crateNormalMap,
																   new Vector3f(0.6f, 0.6f, 0.6f),
																   new Vector3f(0.8f, 0.8f, 0.8f),
																   new Vector3f(0.02f, 0.02f, 0.02f),
																   8.0f, false, false,
																   RenderEngine.getEntityRenderer().getShader())); 
		
		addComponent(new StaticMesh("CrateStaticMesh", crateMesh));
		addComponent(new CrateMovement("CrateMovement", 15.0f));
	}

}
