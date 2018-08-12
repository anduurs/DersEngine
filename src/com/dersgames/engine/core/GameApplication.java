package com.dersgames.engine.core;

import org.lwjgl.glfw.GLFW;

import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.Window;
import com.dersgames.engine.graphics.models.ModelManager;
import com.dersgames.engine.graphics.textures.TextureManager;
import com.dersgames.engine.input.KeyInput;

public abstract class GameApplication implements Runnable {
	
	private volatile boolean m_Running = false;
	private Thread m_Thread;

	public static Scene currentScene;
	
	private Window m_Window;
	
	private int m_Width;
	private int m_Height;
	private String m_Title;
	private boolean m_Vsync;
	private boolean m_FullScreen;

	public static float deltaTime;

	public GameApplication(){
		m_Width = 800;
		m_Height = 600;
		m_Title = "Untitled Game v0.01";
		m_Vsync = false;
		m_FullScreen = false;
		initResources();
		initGame();
	}
	
	public void setWindowWidthAndHeight(int width, int height) {
		m_Width = width;
		m_Height = height;
	}
	
	public void setGameTitle(String title) {
		m_Title = title;
	}
	
	public void setVsync(boolean enabled) {
		m_Vsync = enabled;
	}
	
	public void setFullScreen(boolean fullScreen) {
		m_FullScreen = fullScreen;
	}
	
	public static void loadScene(Scene scene) {
		if (currentScene != null) {
			currentScene.onSceneDestroyed();
		}
		
		currentScene = scene;
	}
	
	public abstract void initResources();
	public abstract void initGame();
	
	public synchronized void start(){
		m_Running = true;
		
		if(m_Thread == null){
			m_Thread = new Thread(this, "Game-Thread");
			m_Thread.start();
		}
	}
	
	private void onGameApplicationStarted(){
		m_Window = new Window(m_Width, m_Height, m_Title, m_Vsync, m_FullScreen);
		RenderEngine.getInstance().init();
		currentScene.onSceneLoaded();
	}
	
	private void onGameApplicationExit(){
		RenderEngine.getInstance().dispose();
		TextureManager.getInstance().dispose();
		ModelManager.getInstance().dispose();
		currentScene.onSceneDestroyed();
	}
	
	private void update(float dt){
		m_Window.update();
		currentScene.update(dt);
	}
	
	private void render(){
		currentScene.render();
		m_Window.swapBuffers();
	}
	
	@Override
	public void run() {
		onGameApplicationStarted();
		
		double previousTime = System.nanoTime();
		double currentTime;
		double passedTime;
		
		double accumulator = 0;
		double frameCounter = 0;
		
		int fps = 0;
		
		final double TARGET_UPS = 60.0;
		final double SECONDS_PER_UPDATE = 1.0 / TARGET_UPS;
		float dt = (float) SECONDS_PER_UPDATE;
		deltaTime = dt;

		while(m_Running){
			currentTime = System.nanoTime();
			passedTime = (currentTime - previousTime) / 1000000000.0;
			
			if(passedTime > 0.25) 
				passedTime = 0.25;
			
			accumulator += passedTime;
			frameCounter += passedTime;
			previousTime = currentTime;
			
			while(accumulator >= SECONDS_PER_UPDATE){
				update(dt);
				accumulator -= SECONDS_PER_UPDATE;
			}
			
			render();
			fps++;
				
			if(frameCounter >= 1){
				m_Window.setTitle(m_Title + " || " + fps + " fps");
//				Debug.log(fps + " fps");
				fps = 0;
				frameCounter = 0;
			}
			
			if(KeyInput.isKeyDown(GLFW.GLFW_KEY_ESCAPE))
				m_Running = false;
			
			if(m_Window.isWindowClosed())
				m_Running = false;
		}
		
		onGameApplicationExit();

		m_Window.destroy();
	}
}
