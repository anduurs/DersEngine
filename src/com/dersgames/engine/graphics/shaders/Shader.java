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
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;

import com.dersgames.engine.core.Matrix4f;
import com.dersgames.engine.core.Vector2f;
import com.dersgames.engine.core.Vector3f;

public abstract class Shader {
	
	private int m_ShaderProgram;
	private HashMap<String, Integer> m_Uniforms;
	
	public Shader(String vertShader, String fragShader){
		m_Uniforms = new HashMap<String, Integer>();
		m_ShaderProgram = glCreateProgram();
		
		addVertexShader(vertShader);
		addFragmentShader(fragShader);
		bindAttributes();
		createShaderProgram();
	}
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute, String name){
		glBindAttribLocation(m_ShaderProgram, attribute, name);
	}
	
	private void addVertexShader(String fileName){
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		String vertexShaderSource = loadShader(fileName + ".vert");
		
		glShaderSource(vertexShader, vertexShaderSource);
		glCompileShader(vertexShader);
		
		System.out.println(":: " + fileName);
		
		if(glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE)
			System.err.println("Couldn't compile vertexshader: " + fileName +  " correctly");
		
		glAttachShader(m_ShaderProgram, vertexShader);
		glDeleteShader(vertexShader);
	}
	
	private void addFragmentShader(String fileName){
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		String fragmentShaderSource = loadShader(fileName + ".frag");
		
		glShaderSource(fragmentShader, fragmentShaderSource);
		glCompileShader(fragmentShader);
		
		System.out.println(":: " + fileName);
		
		if(glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE)
			System.err.println("Couldn't compile fragmentShader: "  + fileName + " correctly");
		
		glAttachShader(m_ShaderProgram, fragmentShader);
		glDeleteShader(fragmentShader);
	}
	
	private void createShaderProgram(){
		glLinkProgram(m_ShaderProgram);
		glValidateProgram(m_ShaderProgram);
	}
	
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
	
	public void loadInteger(String uniformName, int value){
		glUniform1i(m_Uniforms.get(uniformName), value);
	}
	
	public void loadFloat(String uniformName, float value){
		glUniform1f(m_Uniforms.get(uniformName), value);
	}
	
	public void loadVector2f(String uniformName, Vector2f v){
		glUniform2f(m_Uniforms.get(uniformName), v.x, v.y);
	}
	
	public void loadVector3f(String uniformName, Vector3f v){
		glUniform3f(m_Uniforms.get(uniformName), v.x, v.y, v.z);
	}
	
	public void loadMatrix4f(String uniformName, Matrix4f value){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				buffer.put(value.get(i, j));
		buffer.flip();
		
		glUniformMatrix4fv(m_Uniforms.get(uniformName), true, buffer);
	}
	
	public void enable(){
		glUseProgram(m_ShaderProgram);
	}
	
	public void disable(){
		glUseProgram(0);
	}
	
	public void deleteShaderProgram(){
		glDeleteProgram(m_ShaderProgram);
	}
	
	private static String loadShader(String fileName){
		StringBuilder shaderSource = new StringBuilder();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("res/shaders/" + fileName));
			String line;
			while((line = reader.readLine()) != null)
				shaderSource.append(line).append('\n');
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return shaderSource.toString();
	}

}
