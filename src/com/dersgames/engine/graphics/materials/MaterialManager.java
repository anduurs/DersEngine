package com.dersgames.engine.graphics.materials;

public class MaterialManager {
	
	private static MaterialManager instance;
	
	public static MaterialManager getInstance() {
		if (instance == null) {
			instance = new MaterialManager();
		}
		
		return instance;
	}
	
	private MaterialManager() {
		
	}

}
