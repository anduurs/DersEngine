package com.dersgames.game.components;

import com.dersgames.game.core.GameObject;
import com.dersgames.game.core.Matrix4f;

public class Camera2D extends Component{
	
	private GameObject m_Player;
	
	private float m_FrustumWidth;
	private float m_FrustumHeight;
	
	private Matrix4f m_ViewMatrix;
	
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
		
		m_GameObject.getTransform().getPosition().x = x - (m_FrustumWidth / 2 - playerWidth);
		m_GameObject.getTransform().getPosition().y = y - (m_FrustumHeight / 2 - playerHeight);
		
		m_ViewMatrix = new Matrix4f().setTranslationMatrix(-m_GameObject.getTransform().getPosition().x, 
				-m_GameObject.getTransform().getPosition().y, -m_GameObject.getTransform().getPosition().z);
	}

	@Override
	public void update(float dt) {
		float x = m_Player.getTransform().getPosition().x;
		float y = m_Player.getTransform().getPosition().y;
		
		int playerWidth = ((Sprite) m_Player.findComponentByTag("PlayerSprite")).getWidth();
		int playerHeight = ((Sprite) m_Player.findComponentByTag("PlayerSprite")).getHeight();
		
		m_GameObject.getTransform().getPosition().x = x - (m_FrustumWidth / 2 - playerWidth);
		m_GameObject.getTransform().getPosition().y = y - (m_FrustumHeight / 2 - playerHeight);
		
		m_ViewMatrix = m_ViewMatrix.setTranslationMatrix(-m_GameObject.getTransform().getPosition().x, 
				-m_GameObject.getTransform().getPosition().y, -m_GameObject.getTransform().getPosition().z);
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
	
	public Matrix4f getViewMatrix(){
		return m_ViewMatrix;
	}
	
}
