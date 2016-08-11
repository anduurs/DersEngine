package com.dersgames.examplegame.components.player;

import org.lwjgl.glfw.GLFW;

import com.dersgames.engine.components.Component;
import com.dersgames.engine.core.Debug;
import com.dersgames.engine.input.KeyInput;

public class PlayerInput extends Component{
	
	private PlayerMovement m_PlayerMovement;
	
	public PlayerInput(PlayerMovement playerMovement){
		super("PlayerInput");
		m_PlayerMovement = playerMovement;
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update(float dt) {
		
		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_W)){
			m_PlayerMovement.setCurrentSpeed(m_PlayerMovement.getRunSpeed());
			
		}else if(KeyInput.isKeyDown(GLFW.GLFW_KEY_S)){
			m_PlayerMovement.setCurrentSpeed(-m_PlayerMovement.getRunSpeed());
			
		}else{
		
			m_PlayerMovement.setCurrentSpeed(0);
		}
		
//		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_D)){
//			m_PlayerMovement.setCurrentSpeed(m_PlayerMovement.getRunSpeed());
//			m_PlayerMovement.setStrafeRight(true);
//			m_PlayerMovement.setStrafeLeft(false);
////			m_PlayerMovement.setCurrentRotationSpeed(-m_PlayerMovement.getRotationSpeed());
//		}else if(KeyInput.isKeyDown(GLFW.GLFW_KEY_A)){
//			m_PlayerMovement.setCurrentSpeed(-m_PlayerMovement.getRunSpeed());
//			m_PlayerMovement.setStrafeLeft(true);
//			m_PlayerMovement.setStrafeRight(false);
////			m_PlayerMovement.setCurrentRotationSpeed(m_PlayerMovement.getRotationSpeed());
//		}else{
//			m_PlayerMovement.setStrafeRight(false);
//			m_PlayerMovement.setStrafeLeft(false);
//			m_PlayerMovement.setCurrentSpeed(0);
////			m_PlayerMovement.setCurrentRotationSpeed(0);
//		}
		
		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_SPACE)){
			m_PlayerMovement.jump();
		}
	}

}
