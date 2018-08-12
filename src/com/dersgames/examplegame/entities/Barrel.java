package com.dersgames.examplegame.entities;

import com.dersgames.engine.core.Entity;
import com.dersgames.engine.core.Transform;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.graphics.textures.TextureManager;
import com.dersgames.engine.graphics.textures.lightingmaps.NormalMap;
import com.dersgames.engine.graphics.textures.lightingmaps.SpecularMap;

public class Barrel extends Entity{
	
	public Barrel(String tag, Transform transform){
		super(tag, transform);
		
		Texture barrelTexture = new Texture(TextureManager.getInstance().loadModelTexture("barrel"));
		SpecularMap barrelSpecularMap  = new SpecularMap(TextureManager.getInstance().loadModelTexture("barrelSpecularMap"));
		NormalMap barrelNormalMap = new NormalMap(TextureManager.getInstance().loadModelTexture("barrelNormalMap"));
		
//		TexturedModel barrelMesh = new TexturedModel(Loader.getInstance().loadModelFromObjFile("barrel", true),
//						                               new Material(barrelTexture, barrelSpecularMap, barrelNormalMap,
//																   new Vector3f(0.6f, 0.6f, 0.6f),
//																   new Vector3f(0.8f, 0.8f, 0.8f),
//																   new Vector3f(0.02f, 0.02f, 0.02f),
//																   16.0f, false, false,
//																   RenderEngine.getInstance().getNormalMapRenderer().getShader()));
		

	}

}
