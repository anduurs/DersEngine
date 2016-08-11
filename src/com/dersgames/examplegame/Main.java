package com.dersgames.examplegame;

import com.dersgames.engine.core.GameApplication;
import com.dersgames.examplegame.states.ExampleState;

public class Main {
	
	public static void main(String[] args){
		GameApplication game = new GameApplication(800, 600, "TestGame", true);
		game.addStartingState(new ExampleState(game.getGameStateManager()));
		game.start();
	}
	
}
