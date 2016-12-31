package com.dersgames.examplegame.states;

import org.lwjgl.glfw.GLFW;

import com.dersgames.engine.core.Debug;
import com.dersgames.engine.core.GameState;
import com.dersgames.engine.core.GameStateManager;
import com.dersgames.engine.input.KeyInput;

public class PauseState extends GameState{

	public PauseState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		m_BlockRendering = false;
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_M)){
			gsm.popState();
		}
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		Debug.log("PAUSE STATE DISPOSED");
		
	}

}
