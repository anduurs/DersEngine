package com.dersgames.examplegame;

import com.dersgames.engine.core.StateBased3DGame;
import com.dersgames.examplegame.states.ExampleState;

public class Main {
	
	public static void main(String[] args){
		StateBased3DGame game = new StateBased3DGame(800, 600, "TestGame", true);
		game.addStartingState(new ExampleState(game.getGameStateManager()));
		game.start();
	}
	
}
