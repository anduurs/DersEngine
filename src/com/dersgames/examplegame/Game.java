package com.dersgames.examplegame;

import com.dersgames.engine.core.GameApplication;
import com.dersgames.engine.graphics.ImageManager;
import com.dersgames.examplegame.scenes.TestScene;

public class Game extends GameApplication {
	
	@Override
	public void initResources() {
		//ENTITIES TEXTURES
		ImageManager.getInstance().addImage("dragontexture", "dragontexture.png");
		ImageManager.getInstance().addImage("lamp", "lamp.png");
		ImageManager.getInstance().addImage("barrel", "barrel.png");
		ImageManager.getInstance().addImage("crate", "crate.png");
		ImageManager.getInstance().addImage("lantern", "lantern.png");
		
		//GUI TEXTURES
		ImageManager.getInstance().addImage("socuwan", "socuwan.png");
		
		//SPECULAR MAPS
		ImageManager.getInstance().addImage("barrelSpecularMap", "barrelS.png");
		ImageManager.getInstance().addImage("lanternSpecular", "lanternS.png");
		
		//NORMAL MAPS
		ImageManager.getInstance().addImage("barrelNormalMap", "barrelNormal.png");
		ImageManager.getInstance().addImage("crateNormalMap", "crateNormal.png");
		ImageManager.getInstance().addImage("bricksNormal", "bricksNormal.png");
		//ImageManager.addImage("waterNormalMap", "waterNormalMap.png");
		ImageManager.getInstance().addImage("waterNormalMap", "waterNormalMap2.png");

		//DUDV MAPS
		ImageManager.getInstance().addImage("waterDUDV", "waterDUDV.png");
		
		//CUBE MAPS
		ImageManager.getInstance().addImage("top", "cubemaps/top.png");
		ImageManager.getInstance().addImage("bottom", "cubemaps/bottom.png");
		ImageManager.getInstance().addImage("right", "cubemaps/right.png");
		ImageManager.getInstance().addImage("left", "cubemaps/left.png");
		ImageManager.getInstance().addImage("front", "cubemaps/front.png");
		ImageManager.getInstance().addImage("back", "cubemaps/back.png");
		
		ImageManager.getInstance().addImage("nightTop", "cubemaps/nightTop.png");
		ImageManager.getInstance().addImage("nightBottom", "cubemaps/nightBottom.png");
		ImageManager.getInstance().addImage("nightRight", "cubemaps/nightRight.png");
		ImageManager.getInstance().addImage("nightLeft", "cubemaps/nightLeft.png");
		ImageManager.getInstance().addImage("nightFront", "cubemaps/nightFront.png");
		ImageManager.getInstance().addImage("nightBack", "cubemaps/nightBack.png");
		
		//TERRAIN TEXTURES
		ImageManager.getInstance().addImage("grassy", "grassy2.png");
		ImageManager.getInstance().addImage("grassFlowers", "grassFlowers.png");
		ImageManager.getInstance().addImage("mud", "mud.png");
		ImageManager.getInstance().addImage("path", "path.png");
		ImageManager.getInstance().addImage("blendMap", "blendMap.png");
		ImageManager.getInstance().addImage("heightmap", "heightmap.png");
		ImageManager.getInstance().addImage("bricks", "bricks.png");
		
		//PLAYER TEXTURE
		ImageManager.getInstance().addImage("player", "playerTexture.png");
	}
	
	@Override
	public void initGame() {
		setWindowWidthAndHeight(800, 600);
		setGameTitle("Engine test");
		setVsync(false);
		setFullScreen(false);
		loadScene(new TestScene("TestScene"));
	}
	
	public static void main(String[] args){
		new Game().start();
	}
}
