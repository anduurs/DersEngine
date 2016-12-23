package com.dersgames.engine.graphics.renderers;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.List;

import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.gui.GUIComponent;
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
	
	private void begin(){
		RenderEngine.disableCulling();
		glBindVertexArray(m_Quad.getVaoID());
		glEnableVertexAttribArray(0);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_DEPTH_TEST);
	}
	
	public void render(){
		begin();
		
		for(GUIComponent gui : m_GUIComponents){
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, gui.getTexture().getTextureID());
			m_Shader.loadModelMatrix(gui.getEntity());
			glDrawArrays(GL_TRIANGLE_STRIP, 0, m_Quad.getVertexCount());
		}
		
		end();
	}

	private void end(){
		RenderEngine.enableCulling();
		glEnable(GL_DEPTH_TEST);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
	
	public void clear(){
		m_GUIComponents.clear();
	}
	
	public void dispose(){
		m_Shader.deleteShaderProgram();
	}
	
	public GUIShader getShader(){
		return m_Shader;
	}

}
