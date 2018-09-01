package com.dersgames.engine.graphics.renderers;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.GL_TEXTURE3;
import static org.lwjgl.opengl.GL13.GL_TEXTURE4;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.List;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.graphics.FrameBuffer;
import com.dersgames.engine.graphics.GLRenderUtils;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.models.ModelManager;
import com.dersgames.engine.graphics.shaders.ShaderManager;
import com.dersgames.engine.graphics.shaders.WaterShader;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.graphics.textures.TextureManager;
import com.dersgames.engine.graphics.water.WaterTile;
import com.dersgames.engine.graphics.water.WaterUpdate;
import com.dersgames.examplegame.Game;

public class WaterRenderer implements IRenderer<WaterTile>{
	
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

		m_Quad = ModelManager.getInstance().loadModel(m_Vertices, 2);
		m_Shader = (WaterShader) ShaderManager.getInstance().getShader(ShaderManager.DEFAULT_WATER_SHADER);
		m_WaterTiles = new ArrayList<>();

		m_DuDvMap = new Texture(TextureManager.getInstance().loadModelTexture("waterDUDV"));
		m_NormalMap = new Texture(TextureManager.getInstance().loadModelTexture("waterNormalMap"));

		m_Shader.enable();
		m_Shader.connectTextureUnits();
		m_Shader.disable();
	}
	
	@Override
	public void submit(WaterTile tile){
		m_WaterTiles.add(tile);
	}
	
	@Override
	public void begin() {
		m_Shader.enable();
		Camera camera = RenderEngine.getInstance().getCamera();
		m_Shader.loadViewMatrix(camera);
		m_Shader.loadProjectionMatrix(camera.getProjectionMatrix());
		m_Shader.loadCameraPosition(camera.getPosition());
		m_Shader.loadLightSources(Game.currentScene.getDirectionalLight().getPosition(), 
				Game.currentScene.getDirectionalLight().getLightColor());
	}

	@Override
	public void render(){
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

		for(WaterTile tile : m_WaterTiles){
			WaterUpdate waterUpdate = (WaterUpdate)tile.getEntity().findComponentByTag("WaterUpdate");
			m_Shader.loadMoveFactor(waterUpdate.getMoveFactor());
			m_Shader.loadModelMatrix(tile.getEntity());
			GLRenderUtils.drawArrays(m_Quad.getVertexCount());
		}

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, 0);
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, 0);
		glActiveTexture(GL_TEXTURE2);
		glBindTexture(GL_TEXTURE_2D, 0);
		glActiveTexture(GL_TEXTURE3);
		glBindTexture(GL_TEXTURE_2D, 0);
		glActiveTexture(GL_TEXTURE4);
		glBindTexture(GL_TEXTURE_2D, 0);
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
	
	@Override
	public void dispose(){
		m_Shader.deleteShaderProgram();
	}
	
	@Override
	public WaterShader getShader() {
		return m_Shader;
	}

	public WaterTile getWaterTile(){
		if(m_WaterTiles.size() > 0)
			return m_WaterTiles.get(0);
		else return null;
	}

	public int getNumberOfWaterTiles(){
		return m_WaterTiles.size();
	}
	
	public void clear(){
		m_WaterTiles.clear();
	}
}
