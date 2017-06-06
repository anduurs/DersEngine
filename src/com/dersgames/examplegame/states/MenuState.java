package com.dersgames.examplegame.states;

import org.lwjgl.glfw.GLFW;

import com.dersgames.engine.core.Debug;
import com.dersgames.engine.core.GameState;
import com.dersgames.engine.core.GameStateManager;
import com.dersgames.engine.input.KeyInput;

public class MenuState extends GameState{

	public MenuState(GameStateManager gsm) {
		super(gsm);
		
	}

	@Override
	public void init() {
		
		
	}

	@Override
	public void update(float dt) {
		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_V)){
			gsm.pushState(new DayScene(gsm));
		}
		
	}

	@Override
	public void render() {
		
		
	}

	@Override
	public void dispose() {
		Debug.log("MENU STATE DISPOSED");
		
	}

}
