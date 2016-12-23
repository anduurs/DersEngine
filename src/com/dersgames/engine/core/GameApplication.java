package com.dersgames.engine.core;

import org.lwjgl.glfw.GLFW;

import com.dersgames.engine.graphics.Window;
import com.dersgames.engine.input.KeyInput;

public class GameApplication implements Runnable{
	
	private volatile boolean m_Running = false;
	
	private Thread m_Thread;
	private Window m_Window;
	private GameStateManager m_StateManager;
	private GameState m_StartState;
	
	private int m_Width;
	private int m_Height;
	private String m_Title;
	private boolean m_Vsync;
	private boolean m_FullScreen;
	
	public GameApplication(int width, int height, String title, boolean vsync, boolean fullscreen){
		m_Width = width;
		m_Height = height;
		m_Title = title;
		m_Vsync = vsync;
		m_FullScreen = fullscreen;
		
		m_StateManager = new GameStateManager();
	}
	
	public void pushStartingState(GameState startingState){
		m_StartState = startingState;
		m_StateManager.push(startingState);
	}
	
	private void init(){
		m_Window = new Window(m_Width, m_Height, m_Title, m_Vsync, m_FullScreen);
		m_StartState.init();
	}
	
	public synchronized void start(){
		m_Running = true;
		if(m_Thread == null){
			m_Thread = new Thread(this, "Game-Thread");
			m_Thread.start();
		}
	}
	
	private void update(float dt){
		m_Window.update();
		m_StateManager.update(dt);
	}
	
	private void render(){
		m_StateManager.render();
		m_Window.swapBuffers();
	}
	
	@Override
	public void run() {
		init();
		
		double previousTime = System.nanoTime();
		double currentTime;
		double passedTime;
		
		double accumulator = 0;
		double frameCounter = 0;
		
		int fps = 0;
		
		final double TARGET_UPS = 60.0;
		final double SECONDS_PER_UPDATE = 1.0 / TARGET_UPS;
		float dt = (float) SECONDS_PER_UPDATE;
	
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
				Debug.log(fps + " fps");
				fps = 0;
				frameCounter = 0;
			}
			
			if(KeyInput.isKeyDown(GLFW.GLFW_KEY_ESCAPE))
				m_Running = false;
			
			if(m_Window.isWindowClosed())
				m_Running = false;
		}
		
		m_StateManager.getCurrentState().dispose();
		m_Window.destroy();
	}
	
	public GameStateManager getGameStateManager(){
		return m_StateManager;
	}
}
