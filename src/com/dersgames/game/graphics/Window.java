package com.dersgames.game.graphics;

import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_ALPHA;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

import com.dersgames.game.input.Input;

public class Window {
	
	private static int m_Width;
	private static int m_Height;
	
	private long window;
	
	private GLFWKeyCallback keyCallback;
	
	public Window(int width, int height, String title, boolean vsync){
		m_Width  = width;
		m_Height = height;
		
		if(glfwInit() != GL_TRUE)
			System.err.println("GLFW initialization failed!");
		
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
				
		window = glfwCreateWindow(width, height, title, NULL, NULL);

		if(window == NULL)
			System.err.println("Could not create our Window!");
		 
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2,
		          (GLFWvidmode.height(vidmode) - height) / 2);
		
		glfwSetKeyCallback(window, keyCallback = new Input());
		
		glfwMakeContextCurrent(window);
		
		if(vsync)
			glfwSwapInterval(1);
		else glfwSwapInterval(0);
		
		glfwShowWindow(window);
		
		GLContext.createFromCurrent();
		
		glClearColor(0f,0f,0f,0f);
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_ALPHA);
		glEnable(GL_BLEND);
		glEnable(GL_CULL_FACE);
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glCullFace(GL_BACK);
	}
	
	public void clearBuffers(){
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public void update(){
		glfwPollEvents();
	}
	
	public void swapBuffers(){
		glfwSwapBuffers(window);
	}
	
	public boolean isWindowClosed(){
		if(glfwWindowShouldClose(window) == GL_TRUE)
			return true;
		return false;
	}
	
	public void destroy(){
		glfwDestroyWindow(window);
		keyCallback.release();
		glfwTerminate();
	}
	
	public static int getWidth(){
		return m_Width;
	}
	
	public static int getHeight(){
		return m_Height;
	}

}
