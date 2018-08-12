//package com.dersgames.examplegame.states;
//
//import org.lwjgl.glfw.GLFW;
//
//import com.dersgames.engine.components.Camera;
//import com.dersgames.engine.components.GUIComponent;
//import com.dersgames.engine.core.Debug;
//import com.dersgames.engine.core.GameState;
//import com.dersgames.engine.core.GameStateManager;
//import com.dersgames.engine.core.Scene;
//import com.dersgames.engine.core.Transform;
//import com.dersgames.engine.entities.Entity;
//import com.dersgames.engine.entities.lights.DirectionalLight;
//import com.dersgames.engine.entities.lights.PointLight;
//import com.dersgames.engine.entities.lights.SpotLight;
//import com.dersgames.engine.graphics.RenderEngine;
//import com.dersgames.engine.graphics.materials.Material;
//import com.dersgames.engine.graphics.textures.TerrainTexturePack;
//import com.dersgames.engine.graphics.textures.Texture;
//import com.dersgames.engine.graphics.textures.lightingmaps.NormalMap;
//import com.dersgames.engine.graphics.water.WaterTile;
//import com.dersgames.engine.input.KeyInput;
//import com.dersgames.engine.maths.Quaternion;
//import com.dersgames.engine.maths.Vector3f;
//import com.dersgames.engine.resource_management.BitmapManager;
//import com.dersgames.engine.resource_management.Loader;
//import com.dersgames.engine.terrains.Terrain;
//import com.dersgames.examplegame.Game;
//import com.dersgames.examplegame.entities.Barrel;
//import com.dersgames.examplegame.entities.Crate;
//import com.dersgames.examplegame.entities.LampPost;
//
//import java.awt.*;
//
//public class DayScene extends GameState{
//	
//	private RenderEngine m_Renderer;
//	private Loader m_Loader;
//	private Scene m_Scene;
//	
//	public DayScene(GameStateManager gsm) {
//		super(gsm);
//		//init();
//	}
//
//	@Override
//	public void init() {
//		//ENTITIES TEXTURES
//		BitmapManager.getInstance().addImage("dragontexture", "dragontexture.png");
//		BitmapManager.getInstance().addImage("lamp", "lamp.png");
//		BitmapManager.getInstance().addImage("barrel", "barrel.png");
//		BitmapManager.getInstance().addImage("crate", "crate.png");
//		BitmapManager.getInstance().addImage("lantern", "lantern.png");
//		
//		//GUI TEXTURES
//		BitmapManager.getInstance().addImage("socuwan", "socuwan.png");
//		
//		//SPECULAR MAPS
//		BitmapManager.getInstance().addImage("barrelSpecularMap", "barrelS.png");
//		BitmapManager.getInstance().addImage("lanternSpecular", "lanternS.png");
//		
//		//NORMAL MAPS
//		BitmapManager.getInstance().addImage("barrelNormalMap", "barrelNormal.png");
//		BitmapManager.getInstance().addImage("crateNormalMap", "crateNormal.png");
//		BitmapManager.getInstance().addImage("bricksNormal", "bricksNormal.png");
//		//ImageManager.addImage("waterNormalMap", "waterNormalMap.png");
//		BitmapManager.getInstance().addImage("waterNormalMap", "waterNormalMap2.png");
//
//		//DUDV MAPS
//		BitmapManager.getInstance().addImage("waterDUDV", "waterDUDV.png");
//		
//		//CUBE MAPS
//		BitmapManager.getInstance().addImage("top", "cubemaps/top.png");
//		BitmapManager.getInstance().addImage("bottom", "cubemaps/bottom.png");
//		BitmapManager.getInstance().addImage("right", "cubemaps/right.png");
//		BitmapManager.getInstance().addImage("left", "cubemaps/left.png");
//		BitmapManager.getInstance().addImage("front", "cubemaps/front.png");
//		BitmapManager.getInstance().addImage("back", "cubemaps/back.png");
//		
//		BitmapManager.getInstance().addImage("nightTop", "cubemaps/nightTop.png");
//		BitmapManager.getInstance().addImage("nightBottom", "cubemaps/nightBottom.png");
//		BitmapManager.getInstance().addImage("nightRight", "cubemaps/nightRight.png");
//		BitmapManager.getInstance().addImage("nightLeft", "cubemaps/nightLeft.png");
//		BitmapManager.getInstance().addImage("nightFront", "cubemaps/nightFront.png");
//		BitmapManager.getInstance().addImage("nightBack", "cubemaps/nightBack.png");
//		
//		//TERRAIN TEXTURES
//		BitmapManager.getInstance().addImage("grassy", "grassy2.png");
//		BitmapManager.getInstance().addImage("grassFlowers", "grassFlowers.png");
//		BitmapManager.getInstance().addImage("mud", "mud.png");
//		BitmapManager.getInstance().addImage("path", "path.png");
//		BitmapManager.getInstance().addImage("blendMap", "blendMap.png");
//		BitmapManager.getInstance().addImage("heightmap", "heightmap.png");
//		BitmapManager.getInstance().addImage("bricks", "bricks.png");
//		
//		//PLAYER TEXTURE
//		BitmapManager.getInstance().addImage("player", "playerTexture.png");
//		
////		m_Loader   = new Loader();
//
//		
//		createCamera();
//		
//		Terrain terrain = generateTerrain();
//		
//		createLightSources(terrain);
//		createEntities(terrain);
//		
//		
//		Entity guiEntity = new Entity("gui", -0.5f, 0.5f, 200f, 200f);
////		guiEntity.addComponent(new GUIComponent("Gui", new Texture(Loader.loadGUITexture("dragontexture"))));
////		guiEntity.addComponent(new GUIComponent("Gui", new Vector3f(1,0,0)));
//		Game.currentScene.addEntity(guiEntity);
//
//		Debug.log("TOTAL AMOUNT OF VERTICES IN SCENE: " + Loader.getInstance().vertexCounter);
//		Debug.log("TOTAL AMOUNT OF POLYGONS IN SCENE: " + Loader.getInstance().vertexCounter / 3);
//	}
//	
//	private void createCamera(){
//		Entity camera = new Entity("MainCamera", 200, 20, 100);
//		Camera cameraComponent = new Camera(2.0f);
//		camera.addComponent(cameraComponent);
//		m_Renderer.addCamera(cameraComponent);
//		m_Scene.addEntity(camera);
//	}
//	
//	private Terrain generateTerrain(){
//		Texture backgroundTexture = new Texture(Loader.getInstance().loadModelTexture("grassy"));
//		Texture rTexture 		 = new Texture(Loader.getInstance().loadModelTexture("mud"));
//		Texture gTexture 		 = new Texture(Loader.getInstance().loadModelTexture("grassFlowers"));
//		Texture bTexture 		 = new Texture(Loader.getInstance().loadModelTexture("bricks"));
//		NormalMap bricksNormal 		 = new NormalMap(Loader.getInstance().loadModelTexture("bricksNormal"));
//		Texture blendMap 		 = new Texture(Loader.getInstance().loadModelTexture("blendMap"));
//		
//		Material backgroundMaterial =  new Material(backgroundTexture, 
//				RenderEngine.getInstance().getTerrainRenderer().getShader());
//		
//		Material rMaterial =  new Material(rTexture, 
//				RenderEngine.getInstance().getTerrainRenderer().getShader());
//		
//		Material gMaterial =  new Material(gTexture, 
//				RenderEngine.getInstance().getTerrainRenderer().getShader());
//		
//		Material bMaterial =  new Material(bTexture, new Vector3f(1.0f, 1.0f, 1.0f),
//				new Vector3f(0.2f, 0.2f, 0.2f), 
//				new Vector3f(0, 0, 0), 
//				0.0f,
//				false, 
//				false,
//				 RenderEngine.getInstance().getTerrainRenderer().getShader());
//		
//		TerrainTexturePack texturePack   = new TerrainTexturePack();
//		
//		texturePack.addTerrainMaterial(backgroundMaterial);
//		texturePack.addTerrainMaterial(rMaterial);
//		texturePack.addTerrainMaterial(gMaterial);
//		texturePack.addTerrainMaterial(bMaterial);
//		
//		Entity terrainEntity = new Entity("Terrain", 0, 0, 0);
//		//terrainEntity.getTransform().scale(2.0f, 2.0f, 2.0f);
//		Terrain terrain 	 = new Terrain("Terrain", 0, 0, m_Loader, texturePack, blendMap, "heightmap");
//		//Terrain terrain 	 = new Terrain("Terrain", 0, 0, m_Loader, texturePack, blendMap);
//		terrainEntity.addComponent(terrain);
//
//		
//		m_Scene.addEntity(terrainEntity);
//		
//		Entity water = new Entity("Water", 200.0f, -2.0f, 200.0f);
//		WaterTile tile  = new WaterTile("WaterTile");
//		float scale = 3.25f;
//		water.getTransform().scale(WaterTile.TILE_SIZE * scale, WaterTile.TILE_SIZE * scale, WaterTile.TILE_SIZE * scale);
//		water.addComponent(tile);
//		m_Scene.addEntity(water);
//		m_Renderer.water = tile;
//		
//		return terrain;
//	}
//	
//	private void createLightSources(Terrain terrain){
//		Transform sunTransform = new Transform(new Vector3f(5000,5000,5000),
//											   new Quaternion(new Vector3f(1,0,0), 40.0f),
//											   new Vector3f(1,1,1));
//		
//		DirectionalLight sun = new DirectionalLight("DirectionalLight", sunTransform,
//											   new Vector3f(1.0f, 0.7f, 0.7f),
//											   3.0f);
//		
//		m_Scene.addDirectionalLight(sun);	
//	
//		
//		Transform spotLight1Transform = new Transform(new Vector3f(200.0f, 20.0f, 150.0f), 
//				   									  new Quaternion(new Vector3f(1,0,0), 90.0f), 
//				   									  new Vector3f(1,1,1));
//		
//		SpotLight spotLight = new SpotLight("SpotLight1",spotLight1Transform, new Vector3f(1.0f, 1.0f, 1.0f), 
//														  new Vector3f(1.0f, 0.01f, 0.002f),
//														  5.0f,
//														  500.0f,
//														  0.95f);
////		m_Scene.addSpotLight(spotLight);
//		
//		
//		
//		float range = 300.0f;
//		/*LampPost lamp = new LampPost("Lamp1", new Vector3f(200.0f, 0.0f, 150.0f));
//		PointLight pointLight1 = new PointLight("PointLight", lamp.getPosition(),new Vector3f(1.0f, 1.0f, 0.0f), 
//			     new Vector3f(1.0f, 0.045f, 0.00075f),
//			     3.0f,
//				range);
//
//		pointLight1.getPosition().y = lamp.getPosition().y + 20;
//		m_Scene.addPointLight(pointLight1);
//		m_Scene.addEntity(lamp);
//		
//		LampPost lamp2 = new LampPost("Lamp2", new Vector3f(200.0f, 0.0f, 250.0f));
//		PointLight pointLight2 = new PointLight("PointLight", lamp2.getPosition(), new Vector3f(1.0f, 0.0f, 0.0f), 
//			     new Vector3f(1.0f, 0.045f, 0.00075f),
//			     3.0f,
//				range);
//
//		pointLight2.getPosition().y = lamp2.getPosition().y + 20;
//		
//		m_Scene.addPointLight(pointLight2);
//		m_Scene.addEntity(lamp2);
//		
//		LampPost lamp3 = new LampPost("Lamp3", new Vector3f(300.0f, 0.0f, 200.0f));
//		PointLight pointLight3 = new PointLight("PointLight", lamp3.getPosition(), new Vector3f(0.0f, 1.0f, 0.0f), 
//			     new Vector3f(1.0f, 0.045f, 0.00075f),
//			     3.0f,
//				range);
//
//		pointLight3.getPosition().y = lamp3.getPosition().y + 20;
//		
//		m_Scene.addPointLight(pointLight3);
//		m_Scene.addEntity(lamp3);
//		
//		LampPost lamp4 = new LampPost("Lamp3", new Vector3f(100.0f, 0.0f, 200.0f));
//		PointLight pointLight4 = new PointLight("PointLight", lamp4.getPosition(), new Vector3f(0.0f, 0.0f, 1.0f), 
//			     new Vector3f(1.0f, 0.045f, 0.00075f),
//			     3.0f,
//				range);
//		
//		pointLight4.getPosition().y = lamp4.getPosition().y + 20;
//		
//		m_Scene.addPointLight(pointLight4);
//		m_Scene.addEntity(lamp4);*/
//	}
//	
//	private void createEntities(Terrain terrain){
//		Transform dragonTransform = new Transform(new Vector3f(320.0f, 0.0f, 200.0f), 
//											      new Quaternion(new Vector3f(0,1,0), 90.0f), 
//											      new Vector3f(3,3,3));
//
////		Dragon dragon = new Dragon("Dragon", dragonTransform);
////		dragon.getPosition().y = terrain.getHeightOfTerrain(dragon.getPosition().x, dragon.getPosition().z) + 9;
//		
//		Transform crateTransform = new Transform(new Vector3f(160.0f, 80.0f, 200.0f),
//											     new Quaternion(new Vector3f(0,0,0), 0.0f), 
//											     new Vector3f(0.2f,0.2f,0.2f));
//		
//		Crate crate = new Crate("Crate", crateTransform);
////		crate.getPosition().y = terrain.getHeightOfTerrain(crate.getPosition().x, crate.getPosition().z) + 32;
//		
//		Transform barrelTransform = new Transform(new Vector3f(240.0f, 80.0f, 200.0f),
//								    new Quaternion(new Vector3f(0,0,0), 90.0f), 
//								    new Vector3f(3f,3f,3f));
//		
//		Barrel barrel = new Barrel("Barrel", barrelTransform);
////		barrel.getPosition().y = terrain.getHeightOfTerrain(barrel.getPosition().x, barrel.getPosition().z) + 25;
//		
////		m_Scene.addEntity(dragon);
//		m_Scene.addEntity(crate);
//		m_Scene.addEntity(barrel);
//	}
//	
//	@Override
//	public void update(float dt) {
//		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_B)){
//			gsm.pushState(new PauseState(gsm));
//		}
//		m_Scene.update(dt);
//	}
//
//	@Override
//	public void render() {
//		m_Scene.render();
//	}
//
//	@Override
//	public void dispose() {
//		Debug.log("PLAY STATE DISPOSED");
//		Loader.getInstance().dispose();
//		m_Renderer.dispose();
//	}
//}
