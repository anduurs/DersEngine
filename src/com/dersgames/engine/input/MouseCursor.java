package com.dersgames.engine.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import com.dersgames.engine.core.Vector2f;

public class MouseCursor extends GLFWCursorPosCallback{

	private static float mouseX = 0, mouseY = 0;
	private static int mouseDX = 0, mouseDY = 0;
	
	private static long window;

	@Override
	public void invoke(long window, double xpos, double ypos) {
		mouseDX += (float)(xpos - mouseX);
		mouseDY += (float)(ypos - mouseY);
	
		mouseX = (float)xpos;
		mouseY = (float)ypos;
		
		MouseCursor.window = window;
	
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
	}
	
	public static void setVisibility(boolean visible){
		//Debug.log("CURSOR");
		if(!visible){
			GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
		}else{
			GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
		}
	}
	
	
	
	public static void setPosition(float x, float y){
		GLFW.glfwSetCursorPos(window, x, y);
	}
	
	public static Vector2f getPosition(){
		return new Vector2f(mouseX, mouseY);
	}
	
	public static float getX(){
		return mouseX;
	}

	public static float getY(){
		return mouseY;
	}
	
	public static int getDX(){
		 return mouseDX | (mouseDX = 0);
	}

	public static int getDY(){
		 return mouseDY | (mouseDY = 0);
	}
}
