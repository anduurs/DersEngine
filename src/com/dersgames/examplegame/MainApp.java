package com.dersgames.examplegame;

import com.dersgames.engine.core.GameApplication;
import com.dersgames.examplegame.states.ExampleState;

public class MainApp {
	
	public static void main(String[] args){
		GameApplication game = new GameApplication(800, 600, "DersEngine", false, false);
		game.pushStartingState(new ExampleState(game.getGameStateManager()));
		game.start();
	}
	
}
