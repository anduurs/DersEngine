package com.dersgames.game.core;

public class Transform{
	
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
	
	public Matrix4f getTransformationMatrix(){
		Matrix4f T = getTranslationMatrix();
		Matrix4f R = getRotationMatrix();
		Matrix4f S = getScalingMatrix();
		
		Matrix4f transformationMatrix = T.mul(R.mul(S));
		
		return transformationMatrix;
	}
	
	public void translate(float x, float y, float z) {
		m_Position.x = x;
		m_Position.y = y;
		m_Position.z = z;
	}
	
	public void rotate(float x, float y, float z) {
		m_Rotation.x = x;
		m_Rotation.y = y;
		m_Rotation.z = z;
	}
	
	public void scale(float x, float y, float z) {
		m_Scaling.x = x;
		m_Scaling.y = y;
		m_Scaling.z = z;
	}
	
	public Vector3f getPosition() {return m_Position;}
	public Vector3f getRotation() {return m_Rotation;}
	public Vector3f getScaling() {return m_Scaling;}
	
	public void setTranslationVector(Vector3f translation) {
		m_Position = translation;
	}

	public void setRotationVector(Vector3f rotation) {
		m_Rotation = rotation;
	}

	public void setScalingVector(Vector3f scaling) {
		m_Scaling = scaling;
	}
}
