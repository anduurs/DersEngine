package com.dersgames.game.core;

public abstract class GameState {
	
	protected GameStateManager gsm;
	protected boolean m_BlockUpdating = true;
	protected boolean m_BlockRendering = true;
	
	public GameState(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	public abstract void init();
	public abstract void update(float dt);
	public abstract void render();
	public abstract void cleanUp();
	
	public boolean isUpdatingBlocked() {
		return m_BlockUpdating;
	}
	
	public boolean isRenderingBlocked() {
		return m_BlockRendering;
	}
	
	public void setUpdatingBlocked(boolean blockTicking) {
		m_BlockUpdating = blockTicking;
	}
	
	public void setRenderingBlocked(boolean blockRendering) {
		m_BlockRendering = blockRendering;
	}

}
