package com.dersgames.engine.graphics.shaders;

import com.dersgames.engine.math.Matrix4f;
import com.dersgames.engine.math.Vector2f;
import com.dersgames.engine.math.Vector3f;
import com.dersgames.engine.math.Vector4f;

public interface IShader {
	
	/**
	 * Creates a vertex shader by compiling the given source code 
	 * and attaching the shader to a shader program
	 * 
	 * @param fileName the filename of the vertex shader with no file ending and the root folder is assumed to be /shaders/.
	 * Example filepath: "entity/entityVertexShader"
	 */
	public void addVertexShader(String fileName);
	
	/**
	 * Creates a fragment shader by compiling the given source code 
	 * and attaching the shader to a shader program
	 * 
	 * @param fileName the filename of the fragment shader with no file ending and the root folder is assumed to be /shaders/.
	 * Example filepath: "entity/entityFragmentShader"
	 */
	public void addFragmentShader(String fileName);
	
	public void createShaderProgram();
	
	public void bindAttribute(int attribute, String name);
	
	public void addUniform(String uniform);
	
	public void loadInteger(String uniformName, int value);
	
	public void loadFloat(String uniformName, float value);
	
	public void loadVector2f(String uniformName, Vector2f value);
	
	public void loadVector3f(String uniformName, Vector3f value);

	public void loadVector4f(String uniformName, Vector4f value);
	
	public void loadMatrix4f(String uniformName, Matrix4f value);
	
	public void enable();
	
	public void disable();
	
	public void deleteShaderProgram();
	
	public int getShaderProgramID();
	
}
