package com.dersgames.testgame.components.player;

import org.lwjgl.glfw.GLFW;

import com.dersgames.game.components.Component;
import com.dersgames.game.input.KeyInput;

public class InputComponent extends Component{
	
	private PhysicsComponent m_PhysicsComponent;
	
	public InputComponent(PhysicsComponent physicsComponent){
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
