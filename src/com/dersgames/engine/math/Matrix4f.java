package com.dersgames.engine.math;

/**
 * Class for representing a 4x4 matrix
 * @author Anders
 */
public class Matrix4f {
	
	private float[][]  m_Matrix;
	
	/**
	 * Creates a new 4x4 matrix
	 */
	public Matrix4f(){
		m_Matrix = new float[4][4];
	}
	
	/**
	 * Sets the current matrix to the identity matrix
	 * @return this matrix transformed to the identity matrix
	 */
	public Matrix4f setIdentityMatrix(){
		m_Matrix[0][0] = 1;	m_Matrix[0][1] = 0;	m_Matrix[0][2] = 0;	m_Matrix[0][3] = 0;
		m_Matrix[1][0] = 0;	m_Matrix[1][1] = 1;	m_Matrix[1][2] = 0;	m_Matrix[1][3] = 0;
		m_Matrix[2][0] = 0;	m_Matrix[2][1] = 0;	m_Matrix[2][2] = 1;	m_Matrix[2][3] = 0;
		m_Matrix[3][0] = 0;	m_Matrix[3][1] = 0;	m_Matrix[3][2] = 0;	m_Matrix[3][3] = 1;
		
		return this;
	}
	
	/**
	 * Sets the current matrix to the translation matrix
	 * @param x amount of translation on the x-axis
	 * @param y amount of translation on the y-axis
	 * @param z amount of translation on the z-axis
	 * @return this matrix transformed to the translation matrix
	 */
	public Matrix4f setTranslationMatrix(float x, float y, float z){
		m_Matrix[0][0] = 1;	m_Matrix[0][1] = 0;	m_Matrix[0][2] = 0;	m_Matrix[0][3] = x;
		m_Matrix[1][0] = 0;	m_Matrix[1][1] = 1;	m_Matrix[1][2] = 0;	m_Matrix[1][3] = y;
		m_Matrix[2][0] = 0;	m_Matrix[2][1] = 0;	m_Matrix[2][2] = 1;	m_Matrix[2][3] = z;
		m_Matrix[3][0] = 0;	m_Matrix[3][1] = 0;	m_Matrix[3][2] = 0;	m_Matrix[3][3] = 1;
		
		return this;
	}
	
	/**
	 * Sets the current matrix to the scaling matrix
	 * @param x amount of scaling on the x-axis
	 * @param y amount of scaling on the y-axis
	 * @param z amount of scaling on the z-axis
	 * @return this matrix transformed to the scaling matrix
	 */
	public Matrix4f setScalingMatrix(float x, float y, float z){
		m_Matrix[0][0] = x;	m_Matrix[0][1] = 0;	m_Matrix[0][2] = 0;	m_Matrix[0][3] = 0;
		m_Matrix[1][0] = 0;	m_Matrix[1][1] = y;	m_Matrix[1][2] = 0;	m_Matrix[1][3] = 0;
		m_Matrix[2][0] = 0;	m_Matrix[2][1] = 0;	m_Matrix[2][2] = z;	m_Matrix[2][3] = 0;
		m_Matrix[3][0] = 0;	m_Matrix[3][1] = 0;	m_Matrix[3][2] = 0;	m_Matrix[3][3] = 1;
		
		return this;
	}
	
	/**
	 * Sets the current matrix to the rotation matrix
	 * @param x the angle in degrees which will be rotated around the x-axis
	 * @param y the angle in degrees which will be rotated around the y-axis
	 * @param z the angle in degrees which will be rotated around the z-axis
	 * @return this matrix transformed to the rotation matrix
	 */
	public Matrix4f setRotationMatrix(float x, float y, float z){
		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();

		x = (float)Math.toRadians(x);
		y = (float)Math.toRadians(y);
		z = (float)Math.toRadians(z);

		rz.m_Matrix[0][0] = (float)Math.cos(z);		rz.m_Matrix[0][1] = -(float)Math.sin(z);	rz.m_Matrix[0][2] = 0;				    	rz.m_Matrix[0][3] = 0;
		rz.m_Matrix[1][0] = (float)Math.sin(z);		rz.m_Matrix[1][1] = (float)Math.cos(z);		rz.m_Matrix[1][2] = 0;						rz.m_Matrix[1][3] = 0;
		rz.m_Matrix[2][0] = 0;						rz.m_Matrix[2][1] = 0;						rz.m_Matrix[2][2] = 1;						rz.m_Matrix[2][3] = 0;
		rz.m_Matrix[3][0] = 0;						rz.m_Matrix[3][1] = 0;						rz.m_Matrix[3][2] = 0;						rz.m_Matrix[3][3] = 1;

		rx.m_Matrix[0][0] = 1;						rx.m_Matrix[0][1] = 0;						rx.m_Matrix[0][2] = 0;						rx.m_Matrix[0][3] = 0;
		rx.m_Matrix[1][0] = 0;						rx.m_Matrix[1][1] = (float)Math.cos(x);		rx.m_Matrix[1][2] = -(float)Math.sin(x);	rx.m_Matrix[1][3] = 0;
		rx.m_Matrix[2][0] = 0;						rx.m_Matrix[2][1] = (float)Math.sin(x);		rx.m_Matrix[2][2] = (float)Math.cos(x);		rx.m_Matrix[2][3] = 0;
		rx.m_Matrix[3][0] = 0;						rx.m_Matrix[3][1] = 0;						rx.m_Matrix[3][2] = 0;						rx.m_Matrix[3][3] = 1;

		ry.m_Matrix[0][0] = (float)Math.cos(y);		ry.m_Matrix[0][1] = 0;						ry.m_Matrix[0][2] = -(float)Math.sin(y);	ry.m_Matrix[0][3] = 0;
		ry.m_Matrix[1][0] = 0;						ry.m_Matrix[1][1] = 1;						ry.m_Matrix[1][2] = 0;						ry.m_Matrix[1][3] = 0;
		ry.m_Matrix[2][0] = (float)Math.sin(y);		ry.m_Matrix[2][1] = 0;						ry.m_Matrix[2][2] = (float)Math.cos(y);		ry.m_Matrix[2][3] = 0;
		ry.m_Matrix[3][0] = 0;						ry.m_Matrix[3][1] = 0;						ry.m_Matrix[3][2] = 0;						ry.m_Matrix[3][3] = 1;

		m_Matrix = rz.mul(ry.mul(rx)).getMatrix();

		return this;
	}
	
	public Matrix4f setBasis(Vector3f forward, Vector3f up){
		Vector3f f = forward.normalize();
		
		Vector3f r = up.normalize();
		r = r.cross(f);
		
		Vector3f u = f.cross(r);

		return setBasis(f, u, r);
	}

	public Matrix4f setBasis(Vector3f forward, Vector3f up, Vector3f right){
		Vector3f f = forward;
		Vector3f r = right;
		Vector3f u = up;

		m_Matrix[0][0] = r.x;	m_Matrix[0][1] = r.y;	m_Matrix[0][2] = r.z;	m_Matrix[0][3] = 0;
		m_Matrix[1][0] = u.x;	m_Matrix[1][1] = u.y;	m_Matrix[1][2] = u.z;	m_Matrix[1][3] = 0;
		m_Matrix[2][0] = f.x;	m_Matrix[2][1] = f.y;	m_Matrix[2][2] = f.z;	m_Matrix[2][3] = 0;
		m_Matrix[3][0] = 0;		m_Matrix[3][1] = 0;		m_Matrix[3][2] = 0;		m_Matrix[3][3] = 1;

		return this;
	}
	
	/**
	 * Sets this matrix to the orthographic projection matrix
	 * @param left
	 * @param right
	 * @param bottom
	 * @param top
	 * @param near
	 * @param far
	 * @return this matrix transformed to the orthographic projection matrix
	 */
	public Matrix4f setOrthographicProjection(float left, float right, float bottom, float top, float near, float far){
		m_Matrix[0][0] = 2/(right - left);	m_Matrix[0][1] = 0;					m_Matrix[0][2] = 0;				m_Matrix[0][3] = -(right + left)/(right - left);
		m_Matrix[1][0] = 0;					m_Matrix[1][1] = 2/(top - bottom);	m_Matrix[1][2] = 0;				m_Matrix[1][3] = -(top + bottom)/(top - bottom);
		m_Matrix[2][0] = 0;					m_Matrix[2][1] = 0;					m_Matrix[2][2] = 2/(far - near);	m_Matrix[2][3] = -(far + near)/(far - near);
		m_Matrix[3][0] = 0;					m_Matrix[3][1] = 0;					m_Matrix[3][2] = 0;				m_Matrix[3][3] = 1;

		return this;
	}
	
	public Matrix4f setPerspectiveProjection(float fov, float aspectRatio, float zNear, float zFar){
		float tanHalfFOV = (float)Math.tan(fov / 2);
		float zRange = zNear - zFar;
		
		m_Matrix[0][0] = 1.0f / (tanHalfFOV * aspectRatio);	m_Matrix[0][1] = 0;					m_Matrix[0][2] = 0;						m_Matrix[0][3] = 0;
		m_Matrix[1][0] = 0;									m_Matrix[1][1] = 1.0f / tanHalfFOV;	m_Matrix[1][2] = 0;						m_Matrix[1][3] = 0;
		m_Matrix[2][0] = 0;									m_Matrix[2][1] = 0;					m_Matrix[2][2] = (-zNear -zFar)/zRange;	m_Matrix[2][3] = 2 * zFar * zNear / zRange;
		m_Matrix[3][0] = 0;									m_Matrix[3][1] = 0;					m_Matrix[3][2] = 1;						m_Matrix[3][3] = 0;
		
		return this;
	}
	
	/**
	 * Performs matrix multiplication between this matrix and the given
	 * @param m the matrix which will be multiplied with this matrix
	 * @return the matrix which is the result of the matrix multiplication
	 */
	public Matrix4f mul(Matrix4f m){
		Matrix4f res = new Matrix4f();

		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				res.set(i, j, m_Matrix[i][0] * m.get(0, j) +
							  m_Matrix[i][1] * m.get(1, j) +
							  m_Matrix[i][2] * m.get(2, j) +
							  m_Matrix[i][3] * m.get(3, j));
			}
		}

		return res;
	}
	
	public Vector3f mul(Vector3f v){
		return new Vector3f(m_Matrix[0][0] * v.x + m_Matrix[0][1] * v.y + m_Matrix[0][2] * v.z + m_Matrix[0][3],
		                    m_Matrix[1][0] * v.x + m_Matrix[1][1] * v.y + m_Matrix[1][2] * v.z + m_Matrix[1][3],
		                    m_Matrix[2][0] * v.x + m_Matrix[2][1] * v.y + m_Matrix[2][2] * v.z + m_Matrix[2][3]);
	}
	
	public float[][] getMatrix(){
		float[][] res = new float[4][4];

		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				res[i][j] = m_Matrix[i][j];

		return res;
	}

	public float get(int x, int y){
		return m_Matrix[x][y];
	}
	
	public void setMatrix(float[][] m){
		this.m_Matrix = m;
	}

	public void set(int x, int y, float value){
		m_Matrix[x][y] = value;
	}
}
