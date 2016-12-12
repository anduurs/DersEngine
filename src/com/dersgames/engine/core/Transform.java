package com.dersgames.engine.core;

public class Transform{
	
	private Vector3f m_Position;
	private Quaternion m_Rotation;
	private Vector3f m_Scale;
	
	private Matrix4f m_TranslationMatrix;
	private Matrix4f m_ScalingMatrix;
	
	public Transform(){
		this(new Vector3f(0,0,0), new Quaternion(), new Vector3f(0,0,0));
	}
	
	public Transform(Vector3f position, Quaternion rotation, Vector3f scale){
		m_Position = position;
		m_Rotation = rotation;
		m_Scale  = scale;
		
		m_TranslationMatrix = new Matrix4f().setTranslationMatrix(m_Position.x, m_Position.y, m_Position.z);
		m_ScalingMatrix 	= new Matrix4f().setScalingMatrix(m_Scale.x, m_Scale.y, m_Scale.z);
	}
	
	public Matrix4f getTranslationMatrix(){
		return m_TranslationMatrix.setTranslationMatrix(m_Position.x, m_Position.y, m_Position.z);
	}
	
	public Matrix4f getRotationMatrix(){
		return m_Rotation.toRotationMatrix();
	}
	
	public Matrix4f getScalingMatrix(){
		return m_ScalingMatrix.setScalingMatrix(m_Scale.x, m_Scale.y, m_Scale.z);
	}
	
	public Matrix4f getModelMatrix(){
		Matrix4f T = getTranslationMatrix();
		Matrix4f R = getRotationMatrix();
		Matrix4f S = getScalingMatrix();
		
		Matrix4f modelMatrix = T.mul(R.mul(S));
		
		return modelMatrix;
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
		m_Scale.x = x;
		m_Scale.y = y;
		m_Scale.z = z;
	}
	
	public void translate(Vector3f direction, float speed){
		m_Position = m_Position.add(direction.mul(speed));
	}
	
	/**
	 * rotates around the given axis with the specified angle in degrees
	 * @param axis the axis to rotate around (must be normalized)
	 * @param angle the rotation angle in degrees
	 */
	public void rotate(Vector3f axis, float angle) {
		m_Rotation = new Quaternion(axis, angle).mul(m_Rotation).normalize();
	}
	
	public Vector3f getPosition() {return m_Position;}
	public Quaternion getRotation() {return m_Rotation;}
	public Vector3f getScale() {return m_Scale;}
	
	public void setTranslationVector(Vector3f translation) {
		m_Position = translation;
	}

	public void setRotationVector(Quaternion rotation) {
		m_Rotation = rotation;
	}

	public void scale(Vector3f scaling) {
		m_Scale = scaling;
	}
}
