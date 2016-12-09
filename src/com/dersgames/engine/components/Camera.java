package com.dersgames.engine.components;

import org.lwjgl.glfw.GLFW;

import com.dersgames.engine.core.Matrix4f;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.graphics.Window;
import com.dersgames.engine.input.KeyInput;
import com.dersgames.engine.input.MouseCursor;

public class Camera extends Component{

	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private Matrix4f m_PerspectiveProjection;
	
	private float m_Sensitivity = 1.5f;
	private float previousMouseX, previousMouseY = 0;
	
	public Camera(){
		this(70.0f, (float)Window.getWidth() / (float)Window.getHeight(), 0.01f, 10000.0f);
	}
	
	public Camera(float fov, float aspect, float zNear, float zFar){
		m_PerspectiveProjection = new Matrix4f().setPerspectiveProjection(fov, 
				aspect, zNear, zFar);
	}
	
	@Override
	public void init() {}
	
	public void update(float dt){
		freeLookCamera(dt, 100.0f);
	}
	
	private void freeLookCamera(float dt, float speed){
		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_W)){
			getTransform().setTranslationVector(getTransform().getPosition().add(getTransform().getRotation().getForward().mul(speed * dt)));
		}else if(KeyInput.isKeyDown(GLFW.GLFW_KEY_S))
			getTransform().setTranslationVector(getTransform().getPosition().add(getTransform().getRotation().getForward().mul(-speed * dt)));
		
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
			getTransform().rotate(getTransform().getRotation().getRight(), pitchChange);
		
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
