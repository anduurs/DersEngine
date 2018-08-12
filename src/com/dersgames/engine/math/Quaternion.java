package com.dersgames.engine.math;

public class Quaternion{
	
	public float x;
	public float y;
	public float z;
	public float w;

	public Quaternion(){
		this(0,0,0,1);
	}
	
	public Quaternion(float x, float y, float z, float w){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Quaternion(Vector3f axis, float angle){
		float sinHalfAngle = (float)Math.sin(Math.toRadians(angle) / 2.0f);
		float cosHalfAngle = (float)Math.cos(Math.toRadians(angle) / 2.0f);

		x = axis.x * sinHalfAngle;
		y = axis.y * sinHalfAngle;
		z = axis.z * sinHalfAngle;
		w = cosHalfAngle;
	}

	public float length(){
		return (float)Math.sqrt(x * x + y * y + z * z + w * w);
	}
	
	public Quaternion normalize(){
		float length = length();
		
		return new Quaternion(x / length, y / length, z / length, w / length);
	}
	
	public Quaternion conjugate(){
		return new Quaternion(-x, -y, -z, w);
	}

	public Quaternion mul(float r){
		return new Quaternion(x * r, y * r, z * r, w * r);
	}

	public Quaternion mul(Quaternion r){
		float newW = w * r.w - x * r.x - y * r.y - z * r.z;
		float newX = x * r.w + w * r.x + y * r.z - z * r.y;
		float newY = y * r.w + w * r.y + z * r.x - x * r.z;
		float newZ = z * r.w + w * r.z + x * r.y - y * r.x;
		
		return new Quaternion(newX, newY, newZ, newW);
	}
	
	public Quaternion mul(Vector3f r){
		float newW = -x * r.x - y * r.y - z * r.z;
		float newX =  w * r.x + y * r.z - z * r.y;
		float newY =  w * r.y + z * r.x - x * r.z;
		float newZ =  w * r.z + x * r.y - y * r.x;
		
		return new Quaternion(newX, newY, newZ, newW);
	}

	public Quaternion sub(Quaternion r){
		return new Quaternion(x - r.x, y - r.y, z - r.z, w - r.w);
	}

	public Quaternion add(Quaternion r){
		return new Quaternion(x + r.x, y + r.y, z + r.z, w + r.w);
	}
	
	public float dot(Quaternion r){
		return x * r.x + y * r.y + z * r.z + w * r.w;
	}

	public Matrix4f toRotationMatrix(){
		Vector3f forward =  new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().setBasis(forward, up, right);
	}

	public Vector3f getForward(){
		return new Vector3f(0,0,1).rotate(this).normalize();
	}

	public Vector3f getBack(){
		return new Vector3f(0,0,-1).rotate(this).normalize();
	}

	public Vector3f getUp(){
		return new Vector3f(0,1,0).rotate(this).normalize();
	}

	public Vector3f getDown(){
		return new Vector3f(0,-1,0).rotate(this).normalize();
	}

	public Vector3f getRight(){
		return new Vector3f(1,0,0).rotate(this).normalize();
	}

	public Vector3f getLeft(){
		return new Vector3f(-1,0,0).rotate(this).normalize();
	}

	public boolean equals(Quaternion r){
		return x == r.x && y == r.y && z == r.z && w == r.w;
	}
}
