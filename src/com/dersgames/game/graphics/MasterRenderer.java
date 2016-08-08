package com.dersgames.game.graphics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dersgames.game.components.lights.Light;
import com.dersgames.game.core.Camera;
import com.dersgames.game.entities.Entity;
import com.dersgames.game.graphics.models.TexturedModel;
import com.dersgames.game.graphics.shaders.StaticShader;

public class MasterRenderer {
	
	private StaticShader m_Shader;
	private Renderer3D m_Renderer;
	
	private Map<TexturedModel, List<Entity>> m_Renderables;
	
	public MasterRenderer(StaticShader shader, Renderer3D renderer){
		m_Shader = shader;
		m_Renderer = renderer;
		
		m_Renderables = new HashMap<TexturedModel, List<Entity>>();
	}
	
	public void render(Light sun, Camera camera){
		m_Shader.enable();
		m_Shader.loadLight(sun);
		m_Shader.loadViewMatrix(camera);
		
		m_Shader.disable();
		m_Renderables.clear();
	}
	
	public void cleanUp(){
		m_Shader.deleteShader();
	}

}
