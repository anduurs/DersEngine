package com.dersgames.game.core;

import org.lwjgl.glfw.GLFW;

import com.dersgames.game.graphics.Window;
import com.dersgames.game.input.KeyInput;
import com.dersgames.game.input.Mouse;
import com.dersgames.game.input.MouseCursor;

public class Camera{
	
	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private Vector3f m_Position;
	private Vector3f m_Forward;
	private Vector3f m_Up;
	private Vector2f m_CenterPosition;
	
	private float m_Sensitivity;
	private float m_Speed;
	private boolean m_MouseLocked = false;
	
	public Camera(){
		this(new Vector3f(0,0,0), new Vector3f(0,0,1), new Vector3f(0,1,0));
	}
	
	public Camera(Vector3f position){
		this(position, new Vector3f(0,0,1), new Vector3f(0,1,0));
	}
	
	public Camera(Vector3f position, Vector3f forward, Vector3f up){
		m_Position = position;
		m_Forward = forward;
		m_Up = up;
		
		m_Forward.normalize();
		m_Up.normalize();
		
		m_CenterPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
		m_Sensitivity = 2.0f;
		m_Speed = 50.0f;
	}
	
	public void update(float dt){
		float movAmt = (float)(m_Speed * dt);

		//TRANSLATION

		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_W)){
			move(m_Forward, movAmt);
		}
		
		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_S)){
			move(m_Forward, -movAmt);
		}
		
		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_D)){
			move(getRight(), movAmt);
		}
		
		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_A)){
			move(getLeft(), movAmt);
		}
		
		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_SPACE)){
			m_MouseLocked = false;
			MouseCursor.setVisibility(true);
		}
		
		//FREE LOOK
		if(Mouse.isMouseButtonPressed(GLFW.GLFW_MOUSE_BUTTON_RIGHT) && !m_MouseLocked){
			m_MouseLocked = true;
			MouseCursor.setVisibility(false);
			MouseCursor.setPosition(m_CenterPosition.x, m_CenterPosition.y);
		}
		
		if(m_MouseLocked){
			Vector2f deltaPos = MouseCursor.getPosition().sub(m_CenterPosition);
			
			boolean rotY = deltaPos.x != 0;
			boolean rotX = deltaPos.y != 0;
			
			if(rotY) rotateY((float)Math.toRadians(deltaPos.x * m_Sensitivity));
			if(rotX) rotateX((float)Math.toRadians(deltaPos.y * m_Sensitivity));
			if(rotY || rotX) MouseCursor.setPosition(m_CenterPosition.x, m_CenterPosition.y);
		}
	}
	
	public void move(Vector3f direction, float amt){
		m_Position = m_Position.add(direction.mul(amt));
	}
	
	public void rotateY(float angle){
		Vector3f hAxis = yAxis.cross(m_Forward);
		hAxis.normalize();
		
		m_Forward.rotate(yAxis, angle);
		m_Forward.normalize();
		
		m_Up = m_Forward.cross(hAxis);
		m_Up.normalize();
	}
	
	public void rotateX(float angle){
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
