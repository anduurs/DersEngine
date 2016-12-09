package com.dersgames.engine.core;

import org.lwjgl.glfw.GLFW;

import com.dersgames.engine.input.KeyInput;
import com.dersgames.engine.input.MouseCursor;
import com.dersgames.examplegame.entities.Player;

public class CameraOld{
	
	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private Player m_Player;
	
	private Vector3f m_Position;
	private Vector3f m_Forward;
	private Vector3f m_Up;
	
	private float m_Sensitivity;
	private boolean m_MouseLocked = true;
	
	private float m_DistanceFromPlayer;
	private float m_AngleAroundPlayer;
	private float m_Pitch;
	
	private final float MAX_PITCH_ANGLE = 90.0f;
	private final float MIN_PITCH_ANGLE = -90.0f;
	
	private float previousMouseX, previousMouseY = 0;
	private float m_CameraOffsetY = 12.0f;
	
	public CameraOld(Player player){
		this(player, new Vector3f(0,0,0), new Vector3f(0,0,1), new Vector3f(0,1,0));
	}
	
	public CameraOld(Player player, Vector3f position){
		this(player, position, new Vector3f(0,0,1), new Vector3f(0,1,0));
	}
	
	public CameraOld(Player player, Vector3f position, Vector3f forward, Vector3f up){
		m_Player = player;
		m_Position = position;
		m_Forward = forward;
		m_Up = up;
		m_Forward.normalize();
		m_Up.normalize();
		
		m_Sensitivity = 2.0f;
		m_DistanceFromPlayer = -3;
		m_AngleAroundPlayer = 0;
		m_Pitch = 0;
		
		rotateAroundX(m_Pitch);
	}
	
	public void update(float dt){
//		updateCameraPositionRelativeToPlayer(dt);
		freeLookCamera(dt, 100.0f);
	}
	
	private void updateCameraPositionRelativeToPlayer(float dt){
		float horizontalDistance = (float) (m_DistanceFromPlayer * Math.cos(Math.toRadians(m_Pitch)));
		float verticalDistance   = (float) (m_DistanceFromPlayer * Math.sin(Math.toRadians(m_Pitch)));
		float theta = m_Player.getTransform().getRotation().y - m_AngleAroundPlayer;
	
		m_Position.x = (float) (m_Player.getPosition().x - (horizontalDistance * Math.sin(Math.toRadians(theta))));
		m_Position.y = m_Player.getPosition().y + verticalDistance + m_CameraOffsetY;
		m_Position.z = (float) (m_Player.getPosition().z - (horizontalDistance * Math.cos(Math.toRadians(theta))));
		
		float currentMouseX = MouseCursor.getX();
		float currentMouseY = MouseCursor.getY();
	
		float deltaMouseX = currentMouseX - previousMouseX;
		float deltaMouseY = currentMouseY - previousMouseY;
			
		boolean shouldRotateAroundX = currentMouseY != previousMouseY;
		boolean shouldRotateAroundY = currentMouseX != previousMouseX;
			
		float pitchChange = deltaMouseY * m_Sensitivity * dt;
			
		m_Pitch += pitchChange;
			
		if(m_Pitch >= MAX_PITCH_ANGLE){
			m_Pitch = MAX_PITCH_ANGLE;
			shouldRotateAroundX = false;
		}
			
		if(m_Pitch <= MIN_PITCH_ANGLE){
			m_Pitch = MIN_PITCH_ANGLE;
			shouldRotateAroundX = false;
		}
			
		float yawChange = deltaMouseX * m_Sensitivity * dt;
		m_AngleAroundPlayer -= yawChange;
			
		if(shouldRotateAroundX) rotateAroundX(pitchChange);
			
		if(shouldRotateAroundY){
			rotateAroundY(yawChange);
			float yRotAmount = m_Player.getTransform().getRotation().y - yawChange;
			m_Player.getTransform().rotate(0, yRotAmount, 0);
		}
		
		previousMouseX = currentMouseX;
		previousMouseY = currentMouseY;
	}
	
	private void freeLookCamera(float dt, float speed){
		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_W)){
			m_Position = m_Position.add(m_Forward.mul(speed * dt));
		}else if(KeyInput.isKeyDown(GLFW.GLFW_KEY_S))
			m_Position = m_Position.add(m_Forward.mul(-speed * dt));
		
//		if(Mouse.isRightMouseButtonPressed()){
//			m_MouseLocked = true;
//		}else m_MouseLocked = false;
		
		float currentMouseX = MouseCursor.getX();
		float currentMouseY = MouseCursor.getY();
		
//		if(m_MouseLocked){
			float deltaMouseX = currentMouseX - previousMouseX;
			float deltaMouseY = currentMouseY - previousMouseY;
				
			boolean shouldRotateAroundX = currentMouseY != previousMouseY;
			boolean shouldRotateAroundY = currentMouseX != previousMouseX;
				
			float pitchChange = deltaMouseY * m_Sensitivity * dt;
			float yawChange = deltaMouseX * m_Sensitivity * dt;
			
			if(shouldRotateAroundX) rotateAroundX(pitchChange);
			if(shouldRotateAroundY) rotateAroundY(yawChange);
//		}
		
		previousMouseX = currentMouseX;
		previousMouseY = currentMouseY;
	}
	
	public void rotateAroundY(float angle){
		Vector3f hAxis = yAxis.cross(m_Forward).normalize();
		m_Forward = m_Forward.rotate(yAxis, angle).normalize();
		m_Up = m_Forward.cross(hAxis).normalize();
	}
	
	public void rotateAroundX(float angle){
		Vector3f hAxis = yAxis.cross(m_Forward).normalize();
		m_Forward = m_Forward.rotate(hAxis, angle).normalize();
		m_Up = m_Forward.cross(hAxis).normalize();
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
