package com.dersgames.game.core;

import org.lwjgl.glfw.GLFW;

import com.dersgames.game.input.Input;

public class Camera{
	
	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private Vector3f m_Position;
	private Vector3f m_Forward;
	private Vector3f m_Up;
	
	public Camera(){
		this(new Vector3f(0,0,0), new Vector3f(0,0,1), new Vector3f(0,1,0));
	}
	
	public Camera(Vector3f position, Vector3f forward, Vector3f up){
		m_Position = position;
		m_Forward = forward;
		m_Up = up;
		
		m_Forward.normalize();
		m_Up.normalize();
	}
	
	public void update(float dt){
		float movAmt = (float)(10.0f * dt);
		float rotAmt = (float)(100.0f * dt);
		
		//TRANSLATION
		
		if(Input.isKeyDown(GLFW.GLFW_KEY_W)){
			move(m_Forward, movAmt);
		}
		
		if(Input.isKeyDown(GLFW.GLFW_KEY_S)){
			move(m_Forward, -movAmt);
		}
		
		if(Input.isKeyDown(GLFW.GLFW_KEY_D)){
			move(getRight(), movAmt);
		}
		
		if(Input.isKeyDown(GLFW.GLFW_KEY_A)){
			move(getLeft(), movAmt);
		}
		
		//ROTATION
		
		if(Input.isKeyDown(GLFW.GLFW_KEY_UP)){
			rotateX(-rotAmt);
		}

		if(Input.isKeyDown(GLFW.GLFW_KEY_DOWN)){
			rotateX(rotAmt);
		}
		
		if(Input.isKeyDown(GLFW.GLFW_KEY_LEFT)){
			rotateY(-rotAmt);
		}

		if(Input.isKeyDown(GLFW.GLFW_KEY_RIGHT)){
			rotateY(rotAmt);
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
