package com.dersgames.engine.graphics.shaders;

import java.util.HashMap;
import java.util.Map;

import com.dersgames.engine.core.Debug;

public class ShaderManager {
	public static final String DEFAULT_ENTITY_SHADER 	= "default_entity_shader";
	public static final String DEFAULT_NORMALMAP_SHADER = "default_normalmap_shader";
	public static final String DEFAULT_SKYBOX_SHADER 	= "default_skybox_shader";
	public static final String DEFAULT_GUI_SHADER 		= "default_gui_shader";
	public static final String DEFAULT_TERRAIN_SHADER 	= "default_terrain_shader";
	public static final String DEFAULT_WATER_SHADER 	= "default_water_shader";
	
	private Map<String, Shader> m_Shaders;
	
	private static ShaderManager instance;
	
	public static ShaderManager getInstance() {
		if (instance == null) {
			instance = new ShaderManager();
		}
		
		return instance;
	}
	
	private ShaderManager() {
		m_Shaders = new HashMap<>();
		
		m_Shaders.put(DEFAULT_ENTITY_SHADER, new EntityShader());
		m_Shaders.put(DEFAULT_NORMALMAP_SHADER, new NormalMapShader());
		m_Shaders.put(DEFAULT_SKYBOX_SHADER, new SkyboxShader());
		m_Shaders.put(DEFAULT_GUI_SHADER, new GUIShader());
		m_Shaders.put(DEFAULT_TERRAIN_SHADER, new TerrainShader());
		m_Shaders.put(DEFAULT_WATER_SHADER, new WaterShader());
	}
	
	public void addShader(String id, Shader shader) {
		if (m_Shaders.containsKey(id)) {
			Debug.log("A shader with id: " + id + " already exists.");
			return;
		}
		
		m_Shaders.put(id, shader);
	}
	
	public Shader getShader(String id) {
		if (m_Shaders.containsKey(id)) {
			return m_Shaders.get(id);
		}
		
		return null;
	}
}
