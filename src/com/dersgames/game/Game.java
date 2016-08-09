package com.dersgames.game;

import com.dersgames.game.core.GameStateManager;
import com.dersgames.game.graphics.Window;
import com.dersgames.game.states.PlayState;

public class Game implements Runnable{
	
	private volatile boolean m_Running = false;
	
	private Thread m_Thread;
	private Window m_Window;
	private GameStateManager m_StateManager;
	
	boolean vsync = true;
	
	public Game(){}
	
	private void init(){
		m_Window = new Window(800, 600, "DersEngine", vsync);
		m_StateManager = new GameStateManager();
		m_StateManager.push(new PlayState(m_StateManager));
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
		
		double lag = 0;
		double frameCounter = 0;
		
		int fps = 0;
		
		final double TARGET_UPS = 60.0;
		final double SEC_PER_UPDATE = 1.0 / TARGET_UPS;
		float dt = (float) SEC_PER_UPDATE;
		
		boolean shouldRender = !vsync;
	
		while(m_Running){
			shouldRender = !vsync;
			currentTime = System.nanoTime();
			passedTime = (currentTime - previousTime) / 1000000000.0;
			
			if(passedTime > 0.25) 
				passedTime = 0.25;
			
			lag += passedTime;
			frameCounter += passedTime;
			
			previousTime = currentTime;
			
			while(lag >= SEC_PER_UPDATE){
				update(dt);
				lag -= SEC_PER_UPDATE;
				shouldRender = true;
			}

			if(shouldRender){
				render();
				fps++;
			}
				
			if(frameCounter >= 1){
				m_Window.setTitle("DersEngine || " + fps + " fps");
				//Debug.log(fps + " fps");
				fps = 0;
				frameCounter = 0;
			}
			
			if(m_Window.isWindowClosed())
				m_Running = false;
			
		}
		m_StateManager.getCurrentState().cleanUp();
		m_Window.destroy();
	}
}
