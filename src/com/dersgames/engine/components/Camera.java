package com.dersgames.engine.components;

import org.lwjgl.glfw.GLFW;

import com.dersgames.engine.graphics.Window;
import com.dersgames.engine.input.KeyInput;
import com.dersgames.engine.input.MouseCursor;
import com.dersgames.engine.maths.Matrix4f;
import com.dersgames.engine.maths.Vector3f;

public class Camera extends Component{

	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private Matrix4f m_PerspectiveProjection;
	
	private float m_Sensitivity;
	private float previousMouseX, previousMouseY = 0;
	
	public Camera(float sensitivity){
		this(70.0f, 0.01f, 10000.0f, sensitivity);
	}
	
	public Camera(float fov, float zNear, float zFar, float cameraSensitivity){
		m_PerspectiveProjection = new Matrix4f().setPerspectiveProjection(fov, 
				(float)Window.getWidth() / (float)Window.getHeight(), zNear, zFar);
		m_Sensitivity = cameraSensitivity;
	}
	
	@Override
	public void init() {}
	
	public void update(float dt){
		freeLookCamera(dt, 100.0f);
	}
	
	private void freeLookCamera(float dt, float speed){
		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_W)){
			getTransform().translate(getRotation().getForward(), speed * dt);
		}else if(KeyInput.isKeyDown(GLFW.GLFW_KEY_S))
			getTransform().translate(getRotation().getBack(), speed * dt);
		
		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_D)){
			getTransform().translate(getRotation().getRight(), speed * dt);
		}else if(KeyInput.isKeyDown(GLFW.GLFW_KEY_A))
			getTransform().translate(getRotation().getLeft(), speed * dt);
		
		float currentMouseX = MouseCursor.getX();
		float currentMouseY = MouseCursor.getY();
	
		float deltaMouseX = currentMouseX - previousMouseX;
		float deltaMouseY = currentMouseY - previousMouseY;
				
		boolean shouldRotateAroundX = currentMouseY != previousMouseY;
		boolean shouldRotateAroundY = currentMouseX != previousMouseX;
				
		float pitchChange = deltaMouseY * m_Sensitivity * dt;
		float yawChange   = deltaMouseX * m_Sensitivity * dt;
	
		if(shouldRotateAroundY) 
			getTransform().rotate(yAxis, yawChange);
		
		if(shouldRotateAroundX) 
			getTransform().rotate(getRotation().getRight(), pitchChange);
		
		previousMouseX = currentMouseX;
		previousMouseY = currentMouseY;
	}
	
	public Matrix4f getViewMatrix(){
		Matrix4f cameraRotation = getTransform().getRotation().conjugate().toRotationMatrix();
		
		Matrix4f cameraTranslation = new Matrix4f().setTranslationMatrix(-getTransform().getPosition().x, 
				-getTransform().getPosition().y, -getTransform().getPosition().z);
		
		return cameraRotation.mul(cameraTranslation);
	}
	
	public Matrix4f getProjectionMatrix(){
		return m_PerspectiveProjection;
	}
	
	public void setSensitivity(float sensitivity){
		m_Sensitivity = sensitivity;
	}
	
}
