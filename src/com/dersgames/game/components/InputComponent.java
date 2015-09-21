package com.dersgames.game.components;

import com.dersgames.game.input.Input;

import org.lwjgl.glfw.GLFW;

public class InputComponent extends GameComponent{
	
	float speed = 80.0f;
	
	public void update(float dt){
		if(Input.isKeyDown(GLFW.GLFW_KEY_W)){
			m_GameObject.getTransform().getPosition().y -= speed*dt;
		}
		
		if(Input.isKeyDown(GLFW.GLFW_KEY_S)){
			m_GameObject.getTransform().getPosition().y += speed*dt;
		}
		
		if(Input.isKeyDown(GLFW.GLFW_KEY_D)){
			m_GameObject.getTransform().getPosition().x += speed*dt;
		}
		
		if(Input.isKeyDown(GLFW.GLFW_KEY_A)){
			m_GameObject.getTransform().getPosition().x -= speed*dt;
		}
	}

}
