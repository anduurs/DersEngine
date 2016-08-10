package com.dersgames.game.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import com.dersgames.game.core.Vector2f;

public class MouseCursor extends GLFWCursorPosCallback{
	
	private static float x, y;
	private static long window;

	@Override
	public void invoke(long window, double xpos, double ypos) {
		x = (float)xpos;
		y = (float)ypos;
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
		return new Vector2f(x, y);
	}
	
	public static float getX(){
		return x;
	}

	public static float getY(){
		return y;
	}
}
