package com.dersgames.engine.graphics.shaders;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.nio.FloatBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.core.Entity;
import com.dersgames.engine.math.Matrix4f;
import com.dersgames.engine.math.Vector2f;
import com.dersgames.engine.math.Vector3f;
import com.dersgames.engine.math.Vector4f;

public abstract class GLShader implements IShader {
	
	private int m_ShaderProgram;
	private HashMap<String, Integer> m_Uniforms;
	
	public GLShader(String vertShader, String fragShader){
		m_Uniforms = new HashMap<String, Integer>();
		m_ShaderProgram = glCreateProgram();
		
		addVertexShader(vertShader);
		addFragmentShader(fragShader);
		bindAttributes();
		createShaderProgram();
	}
	
	protected abstract void bindAttributes();
	
	@Override
	public void bindAttribute(int attribute, String name){
		glBindAttribLocation(m_ShaderProgram, attribute, name);
	}
	
	@Override
	public void addVertexShader(String fileName){
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		String vertexShaderSource = ShaderParser.loadShader(fileName + ".vert");
		
		glShaderSource(vertexShader, vertexShaderSource);
		glCompileShader(vertexShader);
		
		System.out.println("Compiling shader: " + fileName + ".vert");
		
		if(glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE){
			System.err.println("Couldn't compile the vertexshader: '" + fileName + ".vert" + "' correctly.\nError log:\n" + 
					GL20.glGetShaderInfoLog(vertexShader, GL20.glGetShaderi(vertexShader, GL20.GL_INFO_LOG_LENGTH)));
			System.exit(0);
		}
		
		glAttachShader(m_ShaderProgram, vertexShader);
		glDeleteShader(vertexShader);
	}
	
	@Override
	public void addFragmentShader(String fileName){
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		String fragmentShaderSource = ShaderParser.loadShader(fileName + ".frag");
		
		glShaderSource(fragmentShader, fragmentShaderSource);
		glCompileShader(fragmentShader);
		
		System.out.println("Compiling shader: " + fileName + ".frag");
		
		if(glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE){
			System.err.println("Couldn't compile the fragmentshader: '" + fileName + ".frag" +  "' correctly.\nError log:\n" + 
					GL20.glGetShaderInfoLog(fragmentShader, GL20.glGetShaderi(fragmentShader, GL20.GL_INFO_LOG_LENGTH)));
			System.exit(0);
		}
		
		glAttachShader(m_ShaderProgram, fragmentShader);
		glDeleteShader(fragmentShader);
	}
	
	@Override
	public void createShaderProgram(){
		glLinkProgram(m_ShaderProgram);
		if(GL20.glGetProgrami(m_ShaderProgram, GL20.GL_LINK_STATUS) == GL_FALSE){
			System.err.println("Failed to link shaderprogram.\nError log: " + 
					GL20.glGetProgramInfoLog(m_ShaderProgram, GL20.glGetProgrami(m_ShaderProgram, GL20.GL_INFO_LOG_LENGTH)));
			System.exit(0);
		}
		glValidateProgram(m_ShaderProgram);
	}
	
	@Override
	public void addUniform(String uniform){
		int uniformLocation = glGetUniformLocation(m_ShaderProgram, uniform);
		m_Uniforms.put(uniform, uniformLocation);
	}
	
	public void addUniform(String uniform, int arraySize){
		int[] uniformLocation = new int[arraySize];
		for(int i = 0; i < arraySize; i++){
			uniformLocation[i] = glGetUniformLocation(m_ShaderProgram, uniform + "[" + i + "]");
			m_Uniforms.put(uniform+i, uniformLocation[i]);
		}
	}
	
	@Override
	public void loadInteger(String uniformName, int value){
		glUniform1i(m_Uniforms.get(uniformName), value);
	}
	
	@Override
	public void loadFloat(String uniformName, float value){
		glUniform1f(m_Uniforms.get(uniformName), value);
	}
	
	@Override
	public void loadVector2f(String uniformName, Vector2f v){
		glUniform2f(m_Uniforms.get(uniformName), v.x, v.y);
	}
	
	@Override
	public void loadVector3f(String uniformName, Vector3f v){
		glUniform3f(m_Uniforms.get(uniformName), v.x, v.y, v.z);
	}

	@Override
	public void loadVector4f(String uniformName, Vector4f v){
		glUniform4f(m_Uniforms.get(uniformName), v.x, v.y, v.z, v.w);
	}
	
	@Override
	public void loadMatrix4f(String uniformName, Matrix4f value){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				buffer.put(value.get(i, j));
		buffer.flip();
		
		glUniformMatrix4fv(m_Uniforms.get(uniformName), true, buffer);
	}
	
	public void loadModelMatrix(Entity entity){
		loadMatrix4f("modelMatrix", entity.getTransform().getModelMatrix());
	}
	
	public void loadViewMatrix(Camera camera){
		loadMatrix4f("viewMatrix", camera.getViewMatrix());
	}
	
	public void loadProjectionMatrix(Matrix4f projection){
		loadMatrix4f("projectionMatrix", projection);
	}
	
	@Override
	public void enable(){
		glUseProgram(m_ShaderProgram);
	}
	
	@Override
	public void disable(){
		glUseProgram(0);
	}
	
	@Override
	public void deleteShaderProgram(){
		glDeleteProgram(m_ShaderProgram);
	}
	
	@Override
	public int getShaderProgramID() {
		return m_ShaderProgram;
	}
}
