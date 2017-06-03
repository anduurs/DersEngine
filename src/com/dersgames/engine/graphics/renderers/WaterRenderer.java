package com.dersgames.engine.graphics.renderers;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.List;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.shaders.WaterShader;
import com.dersgames.engine.graphics.water.WaterTile;

public class WaterRenderer implements Renderer3D{
	
	private float[] m_Vertices = { -1, -1, -1, 1, 1, -1, 1, -1, -1, 1, 1, 1 };
	
	private Model m_Quad;
	private WaterShader m_Shader;
	private List<WaterTile> m_WaterTiles;
	
	public WaterRenderer(){
		m_Quad = Loader.loadModel(m_Vertices, 2);
		m_Shader = new WaterShader();
		m_WaterTiles = new ArrayList<>();
	}
	
	public void addWaterTile(WaterTile tile){
		m_WaterTiles.add(tile);
	}
	
	private void bind(){
		glBindVertexArray(m_Quad.getVaoID());
	}

	@Override
	public void begin(Camera camera) {
		m_Shader.enable();
		m_Shader.loadViewMatrix(camera);
	}

	@Override
	public void render(){
		bind();
		
		for(WaterTile tile : m_WaterTiles){
			m_Shader.loadModelMatrix(tile.getEntity());
			glDrawArrays(GL_TRIANGLES, 0, m_Quad.getVertexCount());
		}

		unbind();
	}
	
	private void unbind(){
		glBindVertexArray(0);
	}

	@Override
	public void end(){
		m_Shader.disable();
		clear();
	}
	
	public void clear(){
		m_WaterTiles.clear();
	}
	
	public void dispose(){
		m_Shader.deleteShaderProgram();
	}
	
	public WaterShader getShader() {
		return m_Shader;
	}

}
