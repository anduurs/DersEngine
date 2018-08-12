package com.dersgames.examplegame.entities;

import com.dersgames.engine.core.Entity;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.graphics.textures.TextureManager;
import com.dersgames.engine.graphics.textures.lightingmaps.SpecularMap;
import com.dersgames.engine.maths.Vector3f;

public class LampPost extends Entity{
	
	public LampPost(String tag, Vector3f pos){
		super(tag, pos.x, pos.y, pos.z);

		Texture texture = new Texture(TextureManager.getInstance().loadModelTexture("lantern"), 1);
		SpecularMap specularMap = new SpecularMap(TextureManager.getInstance().loadModelTexture("lanternSpecular"));
		Material material = new Material(texture, specularMap, 1.0f, 0.1f, 0.02f, 1.0f, false, false,
				RenderEngine.getInstance().getEntityRenderer().getShader()); 
//		TexturedModel lampMesh = new TexturedModel(Loader.getInstance().loadModelFromObjFile("lantern", false), material);
		
//		addComponent(new StaticMesh("LampMesh", lampMesh));
	}
}
