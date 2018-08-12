package com.dersgames.examplegame.entities;

import com.dersgames.engine.core.Entity;
import com.dersgames.engine.core.Transform;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.graphics.textures.TextureManager;
import com.dersgames.engine.graphics.textures.lightingmaps.NormalMap;

public class Crate extends Entity{
	
	public Crate(String tag, Transform transform){
		super(tag, transform);
		
		Texture crateTexture = new Texture(TextureManager.getInstance().loadModelTexture("crate"), 1);
		NormalMap crateNormalMap = new NormalMap(TextureManager.getInstance().loadModelTexture("crateNormalMap"));
		
//		TexturedModel crateMesh   = new TexturedModel(Loader.getInstance().loadModelFromObjFile("crate", true), 
//						                               new Material(crateTexture, crateNormalMap,
//																   new Vector3f(0.6f, 0.6f, 0.6f),
//																   new Vector3f(0.8f, 0.8f, 0.8f),
//																   new Vector3f(0.02f, 0.02f, 0.02f),
//																   8.0f, false, false,
//																   RenderEngine.getInstance().getNormalMapRenderer().getShader()));
//		
////		addComponent(new StaticMesh("CrateStaticMesh", crateMesh));
//		addComponent(new RotatorComponent("CrateMovement", 15.0f));
	}

}
