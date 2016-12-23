package com.dersgames.engine.graphics.gui;

import com.dersgames.engine.core.Quaternion;
import com.dersgames.engine.core.Transform;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.graphics.Window;

public class GUIEntity extends Entity{
	
	public GUIEntity(String tag, float x, float y, float sizeX, float sizeY){
		super(tag);
		
		
		
		Transform transform = new Transform(new Vector3f(x, y, 1), 
							  new Quaternion(new Vector3f(0,0,0),0), 
							  new Vector3f(sizeX / Window.getWidth(), sizeY / Window.getHeight(), 1));
	
		m_Transform = transform;
	}

}
