package com.dersgames.engine.graphics.renderers;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.core.GameApplication;
import com.dersgames.engine.core.Scene;
import com.dersgames.engine.graphics.FrameBuffer;
import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.shaders.WaterShader;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.graphics.water.WaterTile;
import com.dersgames.engine.maths.Vector4f;

public class WaterRenderer implements Renderer3D{
	
	private float[] m_Vertices = { -1, -1, -1, 1, 1, -1, 1, -1, -1, 1, 1, 1 };
	
	private Model m_Quad;
	private WaterShader m_Shader;
	private List<WaterTile> m_WaterTiles;

	private FrameBuffer m_ReflectionFBO;
	private FrameBuffer m_RefractionFBO;

	private Texture m_DuDvMap;
	private Texture m_NormalMap;
	
	public WaterRenderer(FrameBuffer reflectionFBO, FrameBuffer refractionFBO){
		m_ReflectionFBO = reflectionFBO;
		m_RefractionFBO = refractionFBO;

		m_Quad = Loader.loadModel(m_Vertices, 2);
		m_Shader = new WaterShader();
		m_WaterTiles = new ArrayList<>();

		m_DuDvMap = new Texture(Loader.loadModelTexture("waterDUDV"));
		m_NormalMap = new Texture(Loader.loadModelTexture("waterNormalMap"));

		m_Shader.enable();
		m_Shader.connectTextureUnits();
		m_Shader.disable();
	}
	
	public void addWaterTile(WaterTile tile){
		m_WaterTiles.add(tile);
	}
	
	private void bind(){
		glBindVertexArray(m_Quad.getVaoID());

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, m_ReflectionFBO.getColorTexture());
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, m_RefractionFBO.getColorTexture());
		glActiveTexture(GL_TEXTURE2);
		glBindTexture(GL_TEXTURE_2D, m_DuDvMap.getTextureID());
		glActiveTexture(GL_TEXTURE3);
		glBindTexture(GL_TEXTURE_2D, m_NormalMap.getTextureID());
		glActiveTexture(GL_TEXTURE4);
		glBindTexture(GL_TEXTURE_2D, m_RefractionFBO.getDepthTexture());

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void begin(Camera camera, Vector4f clippingPlane) {
		m_Shader.enable();
		m_Shader.loadViewMatrix(camera);
		m_Shader.loadCameraPosition(camera.getPosition());
		m_Shader.loadLightSources(Scene.getDirectionalLight().getPosition(), Scene.getDirectionalLight().getLightColor());
	}

	@Override
	public void render(){
		bind();

		for(WaterTile tile : m_WaterTiles){
			m_Shader.loadMoveFactor(tile.getMoveFactor());
			m_Shader.loadModelMatrix(tile.getEntity());
			glDrawArrays(GL_TRIANGLES, 0, m_Quad.getVertexCount());
		}

		unbind();
	}
	
	private void unbind(){
		glBindVertexArray(0);
	}

	@Override
	public void end(boolean lastRenderPass){
		m_Shader.disable();
		glDisable(GL_BLEND);
		if(lastRenderPass){
			clear();
		}
	}

	public WaterTile getWaterTile(){
		if(m_WaterTiles.size() > 0)
			return m_WaterTiles.get(0);
		else return null;
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
