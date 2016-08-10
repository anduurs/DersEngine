package com.dersgames.testgame.entities;

import com.dersgames.game.components.StaticMesh;
import com.dersgames.game.entities.Entity;
import com.dersgames.game.graphics.ModelLoader;
import com.dersgames.game.graphics.models.TexturedModel;
import com.dersgames.game.graphics.textures.ModelTexture;
import com.dersgames.testgame.components.player.InputComponent;
import com.dersgames.testgame.components.player.PhysicsComponent;

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
