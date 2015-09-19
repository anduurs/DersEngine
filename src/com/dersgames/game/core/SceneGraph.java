package com.dersgames.game.core;

import com.dersgames.game.graphics.BatchRenderer;

public class SceneGraph {
	
	private static GameObject m_Root;

	public SceneGraph(){
		m_Root = new GameObject("Root");
	}
	
	public void addChild(GameObject gameObject){
		m_Root.addChild(gameObject);
	}
	
	public void destroy(){
		m_Root.destroy();
	}
	
	public void update(float dt){
		m_Root.updateAll(dt);
		m_Root.clearDeadGameObjects();
	}
	
	public void render(BatchRenderer batch){
		m_Root.renderAll(batch);
	}
	
	public static GameObject findChildByTag(String tag){
		return m_Root.findChildByTag(tag);
	}
	
	public static GameObject getRoot() {
		return m_Root;
	}

}
