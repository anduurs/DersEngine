package com.dersgames.engine.graphics.gui;

import com.dersgames.engine.core.Quaternion;
import com.dersgames.engine.core.Transform;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.graphics.Window;

public class GUIEntity extends Entity{
	
	public GUIEntity(String tag, float x, float y, float sizeX, float sizeY){
		super(tag);

//      Conversions between screenspace and normalized device coordinates:
		
//		float posX_screenSpace = (posX_NDC * 0.5 + 0.5) * screenWidth;
//		float posY_screenSpace = (posY_NDC * 0.5 + 0.5) * screenHeight;
		
//      float posX_NDC = (posX_screenSpace / (screenWidth * 0.5)) - 0.5;
//      float posY_NDC = (posY_screenSpace / (screenHeight * 0.5)) - 0.5;
		
//      float sizeX_screenSpace = sizeX_NDC * screenWidth
//      float sizeY_screenSpace = sizeY_NDC * screenHeight
		
//		float sizeX_NDC = sizeX_screenSpace	/ screenWidth;
//		float sizeY_NDC = sizeY_screenSpace	/ screenHeight;
		
		Transform transform = new Transform(new Vector3f(x, y, 1), 
							  new Quaternion(new Vector3f(0,0,0),0), 
							  new Vector3f(sizeX / Window.getWidth(), sizeY / Window.getHeight(), 1));
	
		m_Transform = transform;
	}

}
