package com.dersgames.game.core;

import com.dersgames.game.graphics.Renderer3D;

public class GameStateManager {
	
	private LinkedStack<GameState> states;
	
	public GameStateManager(){
		states = new LinkedStack<GameState>();
	}

	/**
	 * Adds a state at the top of the state stack
	 * @param state the game state that will be added to the top of the state stack
	 */
	public void push(GameState state){
		states.push(state);
	}
	
	/**
	 * Deletes the state at the top of the state stack
	 */
	public void pop(){
		states.pop();
	}
	
	public GameState next(){
		return states.top.next.data;
	}
	
	/**
	 * Returns the current state
	 */
	public GameState getCurrentState(){
		return states.peek();
	}
	
	public void update(float dt){
		update(states.top, dt);
	}
	
	private void update(Node<GameState> state, float dt){
		state.data.update(dt);
		if(!state.data.isUpdatingBlocked())
			update(state.next, dt);
	}
	
	public void render(){
		render(states.top);
	}
	
	private void render(Node<GameState> state){
		if(!state.data.isRenderingBlocked())
			render(state.next);
		state.data.render();
	}

}
