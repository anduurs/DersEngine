package com.dersgames.examplegame.entities;

import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.graphics.ModelLoader;
import com.dersgames.engine.graphics.models.TexturedModel;
import com.dersgames.engine.graphics.textures.ModelTexture;
import com.dersgames.examplegame.components.player.InputComponent;
import com.dersgames.examplegame.components.player.PhysicsComponent;

public class Player extends Entity{
	
	public Player(ModelLoader loader, float x, float y, float z){
		super("Player", x, y, z);
		
		TexturedModel playerModel = new TexturedModel(loader.loadObjModel("player"), 
				new ModelTexture(loader.loadTexture("player")));
		
		playerModel.getModelTexture().setShineDamper(10.0f);
		playerModel.getModelTexture().setReflectivity(1.0f);
		
		addComponent(new StaticMesh("PlayerModel", playerModel));
		PhysicsComponent physics = new PhysicsComponent("Physics");
		addComponent(new InputComponent(physics));
		addComponent(physics);
	}

}
