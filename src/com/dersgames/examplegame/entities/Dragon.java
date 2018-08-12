package com.dersgames.examplegame.entities;

import com.dersgames.engine.core.Entity;
import com.dersgames.engine.core.Transform;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.graphics.textures.TextureManager;

public class Dragon extends Entity{
	
	public Dragon(String tag, Transform transform){
		super(tag, transform);
		
		Texture dragonTexture  = new Texture(TextureManager.getInstance().loadModelTexture("dragontexture"), 1);
//		TexturedModel dragonMesh    = new TexturedModel(Loader.getInstance().loadModelFromObjFile("dragon", false), 
//						                                new Material(dragonTexture, 
//						                                		 	new Vector3f(0.6f, 0.6f, 0.6f),
//																   new Vector3f(0.8f, 0.8f, 0.8f),
//																   new Vector3f(0.02f, 0.02f, 0.02f),
//																   8.0f, false, false,
//																   RenderEngine.getInstance().getEntityRenderer().getShader())); 
		
//		addComponent(new StaticMesh("DragonStaticMesh", dragonMesh));
		
	}

}
