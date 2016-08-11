package com.dersgames.engine.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class Mouse extends GLFWMouseButtonCallback{
	
	public static int[] buttons = new int[12];

	@Override
	public void invoke(long window, int button, int action, int mods) {
		buttons[button] = action;
	}
	
	public static boolean isRightMouseButtonPressed(){
		if(buttons[GLFW.GLFW_MOUSE_BUTTON_RIGHT] == GLFW.GLFW_PRESS) return true;
		return false;
	}
	
	public static boolean isLeftMouseButtonPressed(){
		if(buttons[GLFW.GLFW_MOUSE_BUTTON_LEFT] == GLFW.GLFW_PRESS) return true;
		return false;
	}

}
