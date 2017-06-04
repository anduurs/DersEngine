package com.dersgames.examplegame;

import com.dersgames.engine.core.GameApplication;
import com.dersgames.examplegame.states.MenuState;
import com.dersgames.examplegame.states.PlayState;

public class MainApp {
	
	public static void main(String[] args){
		GameApplication game = new GameApplication(800, 600, "DersEngine", true, false);
		game.pushStartingState(new PlayState(game.getGameStateManager()));
		game.start();
	}
	
}
