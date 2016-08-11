package com.dersgames.engine.graphics;

import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import com.dersgames.engine.input.KeyInput;
import com.dersgames.engine.input.Mouse;
import com.dersgames.engine.input.MouseCursor;

public class Window {
	
	private static int m_Width;
	private static int m_Height;
	
	public static long window;
	
	private GLFWKeyCallback keyCallback;
	private GLFWMouseButtonCallback mouseCallback;
	private GLFWCursorPosCallback mouseCursorCallback;
	
	public Window(int width, int height, String title, boolean vsync){
		m_Width  = width;
		m_Height = height;
		
		if(!glfwInit())
			System.err.println("GLFW initialization failed!");
		
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
				
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		
		if(window == NULL)
			System.err.println("Could not create our Window!");
		 
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		glfwSetWindowPos(window, (vidmode.width() - width) / 2,
		          (vidmode.height() - height) / 2);
		
		glfwSetKeyCallback(window, keyCallback = new KeyInput());
		glfwSetMouseButtonCallback(window, mouseCallback = new Mouse());
		glfwSetCursorPosCallback(window, mouseCursorCallback = new MouseCursor());
		
		glfwMakeContextCurrent(window);
		
		if(vsync)
			glfwSwapInterval(1);
		else glfwSwapInterval(0);
		
		glfwShowWindow(window);
		
		GL.createCapabilities();
		
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
	}
	
	public void setTitle(String title){
		GLFW.glfwSetWindowTitle(window, title);
	}
	
	public void update(){
		glfwPollEvents();
	}
	
	public void swapBuffers(){
		glfwSwapBuffers(window);
	}
	
	public boolean isWindowClosed(){
		if(glfwWindowShouldClose(window))
			return true;
		return false;
	}
	
	public void destroy(){
		glfwDestroyWindow(window);
		keyCallback.free();
		mouseCallback.free();
		mouseCursorCallback.free();
		glfwTerminate();
	}
	
	public static int getWidth(){
		return m_Width;
	}
	
	public static int getHeight(){
		return m_Height;
	}

}
