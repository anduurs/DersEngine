package com.dersgames.game.components;

import com.dersgames.game.input.Input;

import org.lwjgl.glfw.GLFW;

public class InputComponent extends Component{
	
	float speed = 2.5f;
	
	public void update(float dt){
		if(Input.isKeyDown(GLFW.GLFW_KEY_W)){
			m_GameObject.getTransform().getPosition().y -= speed;
		}
		
		if(Input.isKeyDown(GLFW.GLFW_KEY_S)){
			m_GameObject.getTransform().getPosition().y += speed;
		}
		
		if(Input.isKeyDown(GLFW.GLFW_KEY_D)){
			m_GameObject.getTransform().getPosition().x += speed;
		}
		
		if(Input.isKeyDown(GLFW.GLFW_KEY_A)){
			m_GameObject.getTransform().getPosition().x -= speed;
		}
	}

}
