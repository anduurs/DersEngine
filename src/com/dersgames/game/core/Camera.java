package com.dersgames.game.core;

import org.lwjgl.glfw.GLFW;

import com.dersgames.game.graphics.Window;
import com.dersgames.game.input.KeyInput;
import com.dersgames.game.input.Mouse;
import com.dersgames.game.input.MouseCursor;
import com.dersgames.testgame.components.player.PhysicsComponent;
import com.dersgames.testgame.entities.Player;

public class Camera{
	
	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private Vector3f m_Position;
	private Vector3f m_Forward;
	private Vector3f m_Up;
	private Vector2f m_CenterPosition;
	
	private float m_Sensitivity;
	private float m_Speed;
	private boolean m_MouseLocked = false;
	
	private float m_DistanceFromPlayer = 70;
	private float m_AngleAroundPlayer = 0;
	private float m_Pitch = 30;
	
	private Player m_Player;
	
	public Camera(Player player){
		this(player, new Vector3f(0,0,0), new Vector3f(0,0,1), new Vector3f(0,1,0));
	}
	
	public Camera(Player player, Vector3f position){
		this(player, position, new Vector3f(0,0,1), new Vector3f(0,1,0));
	}
	
	public Camera(Player player, Vector3f position, Vector3f forward, Vector3f up){
		m_Player = player;
		m_Position = position;
		m_Forward = forward;
		m_Up = up;
		
		m_Forward.normalize();
		m_Up.normalize();
		
		m_CenterPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
		m_Sensitivity = 0.06f;
		m_Speed = 50.0f;
		
		rotateAroundX(m_Pitch);
	}
	
	public void update(float dt){
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		
		calculateCameraPosition(horizontalDistance, verticalDistance, dt);
		
		if(Mouse.isMouseButtonPressed(GLFW.GLFW_MOUSE_BUTTON_RIGHT)){
			MouseCursor.setPosition(m_CenterPosition.x, m_CenterPosition.y);
			Vector2f deltaPos = MouseCursor.getPosition().sub(m_CenterPosition);
			
			boolean rotX = deltaPos.y != 0;
			
			float pitchChange = deltaPos.y * m_Sensitivity;
			
			m_Pitch -= pitchChange;
			
			if(rotX){
				rotateAroundX(-pitchChange);
			}
			if(rotX) MouseCursor.setPosition(m_CenterPosition.x, m_CenterPosition.y);
			
		}else if(Mouse.isMouseButtonPressed(GLFW.GLFW_MOUSE_BUTTON_LEFT)){
			
//			MouseCursor.setPosition(m_CenterPosition.x, m_CenterPosition.y);
//			Vector2f deltaPos = MouseCursor.getPosition().sub(m_CenterPosition);
//			
//			boolean rotY = deltaPos.x != 0;
//			
//			float angleChange = deltaPos.x * m_Sensitivity;
//			m_AngleAroundPlayer -= angleChange;
//			
//			if(rotY) rotateAroundY(angleChange);
//			if(rotY) MouseCursor.setPosition(m_CenterPosition.x, m_CenterPosition.y);
		}
	}
	
	private float calculateHorizontalDistance(){
		return (float) (m_DistanceFromPlayer * Math.cos(Math.toRadians(m_Pitch)));
	}
	
	private float calculateVerticalDistance(){
		return (float) (m_DistanceFromPlayer * Math.sin(Math.toRadians(m_Pitch)));
	}
	
	private void calculateCameraPosition(float horizontalDistance, float verticalDistance, float dt){
		float theta = -m_Player.getTransform().getRotation().y;
		
		m_Position.x = m_Player.getPosition().x - (float)(horizontalDistance * Math.sin(Math.toRadians(theta)));
		m_Position.y = m_Player.getPosition().y + verticalDistance;
		m_Position.z = m_Player.getPosition().z - (float)(horizontalDistance * Math.cos(Math.toRadians(theta)));
		
		PhysicsComponent p = (PhysicsComponent)m_Player.findComponentByTag("Physics");

		if(p.getCurrentRotationSpeed() != 0){
			float angleChange = p.getCurrentRotationSpeed() * dt;
			m_AngleAroundPlayer -= angleChange;
			rotateAroundY(-angleChange);
		}
	}
	
	public void rotateAroundY(float angle){
		Vector3f hAxis = yAxis.cross(m_Forward);
		hAxis.normalize();
		
		m_Forward.rotate(yAxis, angle);
		m_Forward.normalize();
		
		m_Up = m_Forward.cross(hAxis);
		m_Up.normalize();
	}
	
	public void rotateAroundX(float angle){
		Vector3f hAxis = yAxis.cross(m_Forward);
		hAxis.normalize();
		
		m_Forward.rotate(hAxis, angle);
		m_Forward.normalize();
		
		m_Up = m_Forward.cross(hAxis);
		m_Up.normalize();
	}
	
	public Vector3f getLeft(){
		Vector3f left = m_Forward.cross(m_Up);
		left.normalize();
		return left;
	}
	
	public Vector3f getRight(){
		Vector3f right = m_Up.cross(m_Forward);
		right.normalize();
		return right;
	}
	
	public Matrix4f getViewMatrix(){
		Matrix4f cameraRotation = new Matrix4f().setRotationMatrix(m_Forward, m_Up);
		Matrix4f cameraTranslation = new Matrix4f().setTranslationMatrix(-m_Position.x, -m_Position.y, -m_Position.z);
		
		return cameraRotation.mul(cameraTranslation);
	}
	
	public void setSensitivity(float sensitivity){
		m_Sensitivity = sensitivity;
	}
	
	public Vector3f getPosition() {
		return m_Position;
	}

	public Vector3f getForward() {
		return m_Forward;
	}

	public Vector3f getUp() {
		return m_Up;
	}
}
