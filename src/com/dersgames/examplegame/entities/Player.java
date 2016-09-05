package com.dersgames.examplegame.entities;

import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.Material;
import com.dersgames.engine.graphics.models.TexturedMesh;
import com.dersgames.engine.graphics.textures.TextureAtlas;
import com.dersgames.examplegame.components.player.PlayerInput;
import com.dersgames.examplegame.components.player.PlayerMovement;

public class Player extends Entity{
	
	public Player(Loader loader, float x, float y, float z){
		super("Player", x, y, z);
	
//		TextureAtlas atlas = new TextureAtlas(loader.loadModelTexture("player"), 1);
//		Material material = new Material(atlas, 10.0f, 1.0f, false, false);
//		TexturedMesh playerMesh = new TexturedMesh(loader.loadObjFile("player"), material);
		
//		addComponent(new StaticMesh("PlayerMesh", playerMesh));
		PlayerMovement playerMovement = new PlayerMovement();
		addComponent(new PlayerInput(playerMovement));
		addComponent(playerMovement);
	}

}
