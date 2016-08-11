package com.dersgames.engine.core;

import org.lwjgl.glfw.GLFW;

import com.dersgames.engine.input.Mouse;
import com.dersgames.engine.input.MouseCursor;
import com.dersgames.examplegame.components.player.PlayerMovement;
import com.dersgames.examplegame.entities.Player;

public class Camera{
	
	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private Player m_Player;
	
	private Vector3f m_Position;
	private Vector3f m_Forward;
	private Vector3f m_Up;
	
	private float m_Sensitivity;
	private boolean m_MouseLocked = false;
	
	private float m_DistanceFromPlayer;
	private float m_AngleAroundPlayer;
	private float m_Pitch;
	
	private final float MAX_PITCH_ANGLE = 50.0f;
	private final float MIN_PITCH_ANGLE = 0.0f;
	
	private float lastFrameMouseX, lastFrameMouseY = 0;
	
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
		
		m_Sensitivity = 0.06f;
		m_DistanceFromPlayer = 50;
		m_AngleAroundPlayer = 0;
		m_Pitch = 30;
		
		rotateAroundX(m_Pitch);
	}
	
	public void update(float dt){
		updateCameraPosition(dt);
		
		if(Mouse.isMouseButtonPressed(GLFW.GLFW_MOUSE_BUTTON_RIGHT)){
			m_MouseLocked = true;
		}else m_MouseLocked = false;
		
		if(m_MouseLocked){
			float deltaX = MouseCursor.getX() - lastFrameMouseX;
			float deltaY = MouseCursor.getY() - lastFrameMouseY;
			
			boolean rotX = MouseCursor.getY() != lastFrameMouseY;
			boolean rotY = MouseCursor.getX() != lastFrameMouseX;
			
			float pitchChange = deltaY * m_Sensitivity;
			
			m_Pitch -= pitchChange;
			
			if(m_Pitch >= MAX_PITCH_ANGLE){
				m_Pitch = MAX_PITCH_ANGLE;
				rotX = false;
			}
			
			if(m_Pitch <= MIN_PITCH_ANGLE){
				m_Pitch = MIN_PITCH_ANGLE;
				rotX = false;
			}
			
			float angleChange = deltaX * m_Sensitivity;
			m_AngleAroundPlayer -= angleChange;
			
			if(rotX) rotateAroundX(-pitchChange);
			if(rotY) rotateAroundY(angleChange);
		}
		
		lastFrameMouseX = MouseCursor.getX();
		lastFrameMouseY = MouseCursor.getY();
	}
	
	private void updateCameraPosition(float dt){
		float horizontalDistance = (float) (m_DistanceFromPlayer * Math.cos(Math.toRadians(m_Pitch)));
		float verticalDistance   = (float) (m_DistanceFromPlayer * Math.sin(Math.toRadians(m_Pitch)));
		float theta = -m_Player.getTransform().getRotation().y - m_AngleAroundPlayer;
		
		PlayerMovement p = (PlayerMovement) m_Player.findComponentByTag("PlayerMovement");

		if(p.getCurrentRotationSpeed() != 0){
			float angleChange = p.getCurrentRotationSpeed() * dt;
			rotateAroundY(-angleChange);
		}
		
		m_Position.x = m_Player.getPosition().x - (float)(horizontalDistance * Math.sin(Math.toRadians(theta)));
		m_Position.y = m_Player.getPosition().y + verticalDistance + 6;
		m_Position.z = m_Player.getPosition().z - (float)(horizontalDistance * Math.cos(Math.toRadians(theta)));
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
