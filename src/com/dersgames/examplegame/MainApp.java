package com.dersgames.examplegame;

import com.dersgames.engine.core.GameApplication;
import com.dersgames.examplegame.states.DayScene;
import com.dersgames.examplegame.states.NightScene;

public class MainApp {
	
	public static void main(String[] args){
		GameApplication game = new GameApplication(800, 600, "DersEngine", true, false);
		//game.pushStartingState(new DayScene(game.getGameStateManager()));
		game.pushStartingState(new NightScene(game.getGameStateManager()));
		game.start();
	}
	
}
