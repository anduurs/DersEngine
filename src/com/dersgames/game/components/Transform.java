package com.dersgames.game.components;

import com.dersgames.game.core.Matrix4f;
import com.dersgames.game.core.Vector3f;

public class Transform extends GameComponent{
	
	private Vector3f m_Position, m_Rotation, m_Scaling;
	
	private Matrix4f m_TranslationMatrix;
	private Matrix4f m_RotationMatrix;
	private Matrix4f m_ScalingMatrix;
	
	public Transform(){
		m_Position = new Vector3f(0,0,0);
		m_Rotation = new Vector3f(0,0,0);
		m_Scaling  = new Vector3f(1,1,1);
		
		m_TranslationMatrix = new Matrix4f().setTranslationMatrix(m_Position.x, m_Position.y, m_Position.z);
		m_RotationMatrix    = new Matrix4f().setRotationMatrix(m_Rotation.x, m_Rotation.y, m_Rotation.z);
		m_ScalingMatrix 	= new Matrix4f().setScalingMatrix(m_Scaling.x, m_Scaling.y, m_Scaling.z);
	}
	
	public Matrix4f getTranslationMatrix(){
		return m_TranslationMatrix.setTranslationMatrix(m_Position.x, m_Position.y, m_Position.z);
	}
	
	public Matrix4f getRotationMatrix(){
		return m_RotationMatrix.setRotationMatrix(m_Rotation.x, m_Rotation.y, m_Rotation.z);
	}
	
	public Matrix4f getScalingMatrix(){
		return m_ScalingMatrix.setScalingMatrix(m_Scaling.x, m_Scaling.y, m_Scaling.z);
	}
	
	public Matrix4f getModelMatrix(){
		Matrix4f T = getTranslationMatrix();
		Matrix4f R = getRotationMatrix();
		Matrix4f S = getScalingMatrix();
		
		Matrix4f modelMatrix = T.mul(R.mul(S));
		
		return modelMatrix;
	}
	
	public void translate(float x, float y, float z) {
		m_Position.setX(x);
		m_Position.setY(y);
		m_Position.setZ(z);
	}
	
	public void rotate(float x, float y, float z) {
		m_Rotation.setX(x);
		m_Rotation.setY(y);
		m_Rotation.setZ(z);
	}
	
	public void scale(float x, float y, float z) {
		m_Scaling.setX(x);
		m_Scaling.setY(y);
		m_Scaling.setZ(z);
	}
	
	public Vector3f getPosition() {return m_Position;}
	public Vector3f getRotationVector() {return m_Rotation;}
	public Vector3f getScalingVector() {return m_Scaling;}
	
	public void setTranslationVector(Vector3f translation) {
		m_Position = translation;
	}

	public void setRotationVector(Vector3f rotation) {
		m_Rotation = rotation;
	}

	public void setScalingVector(Vector3f scaling) {
		m_Scaling = scaling;
	}

	@Override
	public void update(float dt) {}

}
