package com.dersgames.examplegame.components.player;

import org.lwjgl.glfw.GLFW;

import com.dersgames.engine.components.Component;
import com.dersgames.engine.input.KeyInput;

public class PlayerInput extends Component{
	
	private PlayerMovement m_PhysicsComponent;
	
	public PlayerInput(PlayerMovement physicsComponent){
		super("PlayerInput");
		m_PhysicsComponent = physicsComponent;
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update(float dt) {
		
		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_W)){
			m_PhysicsComponent.setCurrentSpeed(m_PhysicsComponent.getRunSpeed());
		}else if(KeyInput.isKeyDown(GLFW.GLFW_KEY_S)){
			m_PhysicsComponent.setCurrentSpeed(-m_PhysicsComponent.getRunSpeed());
		}else{
			m_PhysicsComponent.setCurrentSpeed(0);
		}
		
		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_D)){
			m_PhysicsComponent.setCurrentRotationSpeed(-m_PhysicsComponent.getRotationSpeed());
		}else if(KeyInput.isKeyDown(GLFW.GLFW_KEY_A)){
			m_PhysicsComponent.setCurrentRotationSpeed(m_PhysicsComponent.getRotationSpeed());
		}else{
			m_PhysicsComponent.setCurrentRotationSpeed(0);
		}
		
		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_SPACE)){
			m_PhysicsComponent.jump();
		}
	}

}
