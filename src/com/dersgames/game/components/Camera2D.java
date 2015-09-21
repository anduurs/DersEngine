package com.dersgames.game.components;

import com.dersgames.game.core.GameObject;
import com.dersgames.game.core.Matrix4f;

public class Camera2D extends GameComponent{
	
	private GameObject m_Player;
	
	private float m_FrustumWidth;
	private float m_FrustumHeight;
	
	private float m_Zoom;
	
	private Matrix4f m_OrthoProjection;
	
	public Camera2D(String tag, GameObject player, float frustumWidth, float frustumHeight){
		super(tag);
		
		m_Player = player;
		
		m_FrustumWidth  = frustumWidth;
		m_FrustumHeight = frustumHeight;
	}
	
	public void init(){
		float x = m_Player.getTransform().getPosition().x;
		float y = m_Player.getTransform().getPosition().y;
		
		int playerWidth =  ((Sprite) m_Player.findComponentByTag("PlayerSprite")).getWidth();
		int playerHeight = ((Sprite) m_Player.findComponentByTag("PlayerSprite")).getHeight();
		
		m_GameObject.getTransform().getPosition().x = 0;
		m_GameObject.getTransform().getPosition().y = 0;
		
		m_Zoom = 1.0f;
		
//		m_GameObject.getTransform().getPosition().x = x + (m_FrustumWidth / 2 - playerWidth);
//		m_GameObject.getTransform().getPosition().y = y + (m_FrustumHeight / 2 - playerHeight);
		
		m_OrthoProjection = new Matrix4f();//.setOrthographicProjection(0, 0, m_FrustumWidth, m_FrustumHeight, -10.0f, 10.0f);
	}

	@Override
	public void update(float dt) {
		float x = m_Player.getTransform().getPosition().x;
		float y = m_Player.getTransform().getPosition().y;
		
		float x1 = m_GameObject.getTransform().getPosition().x;
		float y1 = m_GameObject.getTransform().getPosition().y;
		
		int playerWidth =  ((Sprite) m_Player.findComponentByTag("PlayerSprite")).getWidth();
		int playerHeight = ((Sprite) m_Player.findComponentByTag("PlayerSprite")).getHeight();
		
		m_OrthoProjection = m_OrthoProjection.setOrthographicProjection(
													x1 - m_FrustumWidth  * m_Zoom / 2, 
													x1 + m_FrustumWidth  * m_Zoom / 2, 
													y1 + m_FrustumHeight * m_Zoom / 2,
													y1 - m_FrustumHeight * m_Zoom / 2, 
													-10.0f, 
													10.0f);
		
		
		m_GameObject.getTransform().getPosition().x = x;
		m_GameObject.getTransform().getPosition().y = y;
	}
	
	public Matrix4f getProjection(){
		return m_OrthoProjection;
	}
	
	public float getCamX(){
		return m_GameObject.getTransform().getPosition().x;
	}
	
	public float getCamY(){
		return m_GameObject.getTransform().getPosition().y;
	}
	
	public float getCamZ(){
		return m_GameObject.getTransform().getPosition().z;
	}
	
}
