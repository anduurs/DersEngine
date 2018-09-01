package com.dersgames.engine.components;

import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.core.Debug;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.Window;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.input.MouseCursor;
import com.dersgames.engine.math.Matrix4f;
import com.dersgames.engine.math.Vector3f;

public class GUIComponent extends Renderable{
	
	protected Texture m_Texture;
	protected Vector3f m_Color;
	protected boolean m_UsingColor;
	
	public GUIComponent(String tag, Texture texture) {
		super(tag);
		m_Texture = texture;
		m_Color = new Vector3f(0,0,0);
		m_UsingColor = false;
	}
	
	public GUIComponent(String tag, Vector3f color) {
		super(tag);
		m_Color = color;
		m_UsingColor = true;
	}
	
	// TODO: Move this logic to a new component
	public void update(float dt){
		if(mouseHoovering(MouseCursor.getX(), MouseCursor.getY())){
			m_Color = new Vector3f(0,0,1);
		}else m_Color = new Vector3f(1,0,0);
	}
	
	private boolean mouseHoovering(float mouseX, float mouseY){
		boolean inboundX = mouseX >= getPosition().x && mouseX <= getPosition().x + getScale().x;
		boolean inboundY = mouseY <= (float)Window.getHeight() - getPosition().y 
				&& mouseY >= (float)Window.getHeight() - getPosition().y - getScale().y;
//		Debug.log(mouseY);
		return inboundX && inboundY;
	}
	
	public Matrix4f getModelMatrix(){
		float newXPos = convertXPosToNDC(getPosition().x);
		float newYPos = convertYPosToNDC(getPosition().y);
		float newXScale = convertXScaleToNDC(getScale().x);
		float newYScale = convertYScaleToNDC(getScale().y);
		
		Matrix4f translationMatrix = new Matrix4f().setTranslationMatrix(newXPos, newYPos, 1.0f);
		Matrix4f scalingMatrix = new Matrix4f().setScalingMatrix(newXScale, newYScale, 1.0f);
		
		return translationMatrix.mul(scalingMatrix);
	}

	//Only works for 800x600 resolution (0.75 = magic number)
	private float convertXPosToNDC(float xPos){
		return xPos;
	}
	
	//Only works for 800x600 resolution (0.95 = magic number)
	private float convertYPosToNDC(float yPos){
		return yPos;
	}

	private float convertXScaleToNDC(float xScale){
		return xScale / Window.getWidth(); 
	}

	private float convertYScaleToNDC(float yScale){
		return yScale / Window.getHeight(); 
	}
	
	@Override
	public void render() {
		m_RenderEngine.getGUIRenderer().submit(this);
	}
	
	public boolean isUsingColor(){
		return m_UsingColor;
	}
	
	public Vector3f getColor(){
		return m_Color;
	}
	
	public Texture getTexture(){
		return m_Texture;
	}

}
