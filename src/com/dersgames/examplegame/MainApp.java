package com.dersgames.examplegame;

import java.io.File;

import com.dersgames.engine.core.GameApplication;
import com.dersgames.examplegame.states.ExampleState;

public class MainApp {
	
	public static void main(String[] args){
		System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
		
		GameApplication game = new GameApplication(800, 600, "TestGame", true);
		game.pushStartingState(new ExampleState(game.getGameStateManager()));
		game.start();
	}
	
}
