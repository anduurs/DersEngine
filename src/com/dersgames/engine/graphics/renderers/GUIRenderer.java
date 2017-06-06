package com.dersgames.engine.graphics.renderers;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.List;

import com.dersgames.engine.components.GUIComponent;
import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.shaders.GUIShader;

public class GUIRenderer {
	
	private final Model m_Quad;
	private GUIShader m_Shader;
	
	private List<GUIComponent> m_GUIComponents;
	
	public GUIRenderer(){
		float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
		m_Quad = Loader.loadModel(positions, 2);
		
		m_Shader = new GUIShader();
		m_GUIComponents = new ArrayList<>();
	}
	
	public void addGUIComponent(GUIComponent guiComponent){
		m_GUIComponents.add(guiComponent);
	}
	
	public void begin(){
		m_Shader.enable();
		RenderEngine.disableFaceCulling();
		glBindVertexArray(m_Quad.getVaoID());
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_DEPTH_TEST);
	}
	
	public void render(){
		for(GUIComponent gui : m_GUIComponents) {
			m_Shader.loadUsingColor(gui.isUsingColor());
			if (!gui.isUsingColor()) {
				glActiveTexture(GL_TEXTURE0);
				glBindTexture(GL_TEXTURE_2D, gui.getTexture().getTextureID());
			}

			m_Shader.loadModelMatrix(gui.getModelMatrix());
			m_Shader.loadColor(gui.getColor());

			glDrawArrays(GL_TRIANGLE_STRIP, 0, m_Quad.getVertexCount());
		}
	}

	public void end(){
		RenderEngine.enableFaceCulling();
		glEnable(GL_DEPTH_TEST);
		glDisable(GL_BLEND);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindVertexArray(0);
		m_Shader.disable();
		clear();
	}
	
	private void clear(){
		m_GUIComponents.clear();
	}
	
	public void dispose(){
		m_Shader.deleteShaderProgram();
	}
	
	public GUIShader getShader(){
		return m_Shader;
	}

}
