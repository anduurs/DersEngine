package com.dersgames.examplegame.states;

import org.lwjgl.glfw.GLFW;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.GUIComponent;
import com.dersgames.engine.core.Debug;
import com.dersgames.engine.core.GameState;
import com.dersgames.engine.core.GameStateManager;
import com.dersgames.engine.core.Scene;
import com.dersgames.engine.core.Transform;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.entities.lights.DirectionalLight;
import com.dersgames.engine.entities.lights.PointLight;
import com.dersgames.engine.entities.lights.SpotLight;
import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.textures.TerrainTexturePack;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.graphics.textures.lightingmaps.NormalMap;
import com.dersgames.engine.graphics.water.WaterTile;
import com.dersgames.engine.input.KeyInput;
import com.dersgames.engine.maths.Quaternion;
import com.dersgames.engine.maths.Vector3f;
import com.dersgames.engine.terrains.Terrain;
import com.dersgames.engine.utils.ImageManager;
import com.dersgames.examplegame.entities.Barrel;
import com.dersgames.examplegame.entities.Crate;
import com.dersgames.examplegame.entities.LampPost;

public class PlayState extends GameState{
	
	private RenderEngine m_Renderer;
	private Loader m_Loader;
	private Scene m_Scene;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
		//init();
	}

	@Override
	public void init() {
		//ENTITIES TEXTURES
		ImageManager.addImage("dragontexture", "dragontexture.png");
		ImageManager.addImage("lamp", "lamp.png");
		ImageManager.addImage("barrel", "barrel.png");
		ImageManager.addImage("crate", "crate.png");
		
		//GUI TEXTURES
		ImageManager.addImage("socuwan", "socuwan.png");
		
		//SPECULAR MAPS
		ImageManager.addImage("barrelSpecularMap", "barrelS.png");
		
		//NORMAL MAPS
		ImageManager.addImage("barrelNormalMap", "barrelNormal.png");
		ImageManager.addImage("crateNormalMap", "crateNormal.png");
		ImageManager.addImage("bricksNormal", "bricksNormal.png");
		
		//CUBE MAPS
		ImageManager.addImage("top", "cubemaps/top.png");
		ImageManager.addImage("bottom", "cubemaps/bottom.png");
		ImageManager.addImage("right", "cubemaps/right.png");
		ImageManager.addImage("left", "cubemaps/left.png");
		ImageManager.addImage("front", "cubemaps/front.png");
		ImageManager.addImage("back", "cubemaps/back.png");
		
		ImageManager.addImage("nightTop", "cubemaps/nightTop.png");
		ImageManager.addImage("nightBottom", "cubemaps/nightBottom.png");
		ImageManager.addImage("nightRight", "cubemaps/nightRight.png");
		ImageManager.addImage("nightLeft", "cubemaps/nightLeft.png");
		ImageManager.addImage("nightFront", "cubemaps/nightFront.png");
		ImageManager.addImage("nightBack", "cubemaps/nightBack.png");
		
		//TERRAIN TEXTURES
		ImageManager.addImage("grassy", "grassy2.png");
		ImageManager.addImage("grassFlowers", "grassFlowers.png");
		ImageManager.addImage("mud", "mud.png");
		ImageManager.addImage("path", "path.png");
		ImageManager.addImage("blendMap", "blendMap.png");
		ImageManager.addImage("heightmap", "heightmap.png");
		ImageManager.addImage("bricks", "bricks.png");
		
		//PLAYER TEXTURE
		ImageManager.addImage("player", "playerTexture.png");
		
		m_Loader   = new Loader();
		m_Renderer = new RenderEngine();
		m_Scene    = new Scene();
		
		createCamera();
		
		Terrain terrain = generateTerrain();
		
		createLightSources(terrain);
		createEntities(terrain);
		
		
		Entity guiEntity = new Entity("gui", 0, 0, 200f, 100f);
//		guiEntity.addComponent(new GUIComponent("Gui", new Texture(Loader.loadGUITexture("dragontexture"))));
		guiEntity.addComponent(new GUIComponent("Gui", new Vector3f(1,0,0)));
		m_Scene.addEntity(guiEntity);
	}
	
	private void createCamera(){
		Entity camera = new Entity("MainCamera", 200, 20, 100);
		Camera cameraComponent = new Camera(2.0f);
		camera.addComponent(cameraComponent);
		m_Renderer.addCamera(cameraComponent);
		m_Scene.addEntity(camera);
	}
	
	private Terrain generateTerrain(){
		Texture backgroundTexture = new Texture(Loader.loadModelTexture("grassy"));
		Texture rTexture 		 = new Texture(Loader.loadModelTexture("mud"));
		Texture gTexture 		 = new Texture(Loader.loadModelTexture("grassFlowers"));
		Texture bTexture 		 = new Texture(Loader.loadModelTexture("bricks"));
		NormalMap bricksNormal 		 = new NormalMap(Loader.loadModelTexture("bricksNormal"));
		Texture blendMap 		 = new Texture(Loader.loadModelTexture("blendMap"));
		
		Material backgroundMaterial =  new Material(backgroundTexture, 
				RenderEngine.getTerrainRenderer().getShader());
		
		Material rMaterial =  new Material(rTexture, 
				RenderEngine.getTerrainRenderer().getShader());
		
		Material gMaterial =  new Material(gTexture, 
				RenderEngine.getTerrainRenderer().getShader());
		
		Material bMaterial =  new Material(bTexture, new Vector3f(1.0f, 1.0f, 1.0f),
				new Vector3f(0.2f, 0.2f, 0.2f), 
				new Vector3f(0, 0, 0), 
				1.0f, 
				false, 
				false,
										   RenderEngine.getTerrainRenderer().getShader());
		
		TerrainTexturePack texturePack   = new TerrainTexturePack();
		
//		texturePack.addTerrainMaterial(backgroundMaterial);
//		texturePack.addTerrainMaterial(rMaterial);
//		texturePack.addTerrainMaterial(gMaterial);
		texturePack.addTerrainMaterial(bMaterial);
		
		Entity terrainEntity = new Entity("Terrain");
//		Terrain terrain 	 = new Terrain("Terrain", 0, 0, m_Loader, texturePack, blendMap, "heightmap");
		Terrain terrain 	 = new Terrain("Terrain", 0, 0, m_Loader, texturePack, blendMap);
		terrainEntity.addComponent(terrain);
		
		m_Scene.addEntity(terrainEntity);
		
		Entity water = new Entity("Water", 300, 5, 70);
		WaterTile tile  = new WaterTile("WaterTile");
		water.getTransform().scale(WaterTile.TILE_SIZE, WaterTile.TILE_SIZE, WaterTile.TILE_SIZE);
		water.addComponent(tile);
		m_Scene.addEntity(water);
		
		return terrain;
	}
	
	private void createLightSources(Terrain terrain){
		Transform sunTransform = new Transform(new Vector3f(0,0,0), 
											   new Quaternion(new Vector3f(1,0,0), 35.0f), 
											   new Vector3f(1,1,1));
		
		DirectionalLight sun = new DirectionalLight("DirectionalLight", sunTransform,
											   new Vector3f(0.8f, 0.8f, 0.8f),
											   0.3f);
		
		m_Scene.addDirectionalLight(sun);	
	
		
		Transform spotLight1Transform = new Transform(new Vector3f(200.0f, 20.0f, 150.0f), 
				   									  new Quaternion(new Vector3f(1,0,0), 90.0f), 
				   									  new Vector3f(1,1,1));
		
		SpotLight spotLight = new SpotLight("SpotLight1",spotLight1Transform, new Vector3f(1.0f, 1.0f, 1.0f), 
														  new Vector3f(1.0f, 0.01f, 0.002f),
														  5.0f,
														  500.0f,
														  0.95f);
//		m_Scene.addSpotLight(spotLight);
		
		
		
		
		LampPost lamp = new LampPost("Lamp1", new Vector3f(200.0f, 0.0f, 150.0f));
		PointLight pointLight1 = new PointLight("PointLight", lamp.getPosition(),new Vector3f(1.0f, 1.0f, 0.0f), 
			     new Vector3f(1.0f, 0.045f, 0.00075f),
			     3.0f,
			     200.0f);

		pointLight1.getPosition().y = lamp.getPosition().y + 20;
		m_Scene.addPointLight(pointLight1);
		m_Scene.addEntity(lamp);
		
		LampPost lamp2 = new LampPost("Lamp2", new Vector3f(200.0f, 0.0f, 250.0f));
		PointLight pointLight2 = new PointLight("PointLight", lamp2.getPosition(), new Vector3f(1.0f, 0.0f, 0.0f), 
			     new Vector3f(1.0f, 0.045f, 0.00075f),
			     3.0f,
			     200.0f);

		pointLight2.getPosition().y = lamp2.getPosition().y + 20;
		
		m_Scene.addPointLight(pointLight2);
		m_Scene.addEntity(lamp2);
		
		LampPost lamp3 = new LampPost("Lamp3", new Vector3f(300.0f, 0.0f, 200.0f));
		PointLight pointLight3 = new PointLight("PointLight", lamp3.getPosition(), new Vector3f(0.0f, 1.0f, 0.0f), 
			     new Vector3f(1.0f, 0.045f, 0.00075f),
			     3.0f,
			     200.0f);

		pointLight3.getPosition().y = lamp3.getPosition().y + 20;
		
		m_Scene.addPointLight(pointLight3);
		m_Scene.addEntity(lamp3);
		
		LampPost lamp4 = new LampPost("Lamp3", new Vector3f(100.0f, 0.0f, 200.0f));
		PointLight pointLight4 = new PointLight("PointLight", lamp4.getPosition(), new Vector3f(0.0f, 0.0f, 1.0f), 
			     new Vector3f(1.0f, 0.045f, 0.00075f),
			     3.0f,
			     200.0f);
		
		pointLight4.getPosition().y = lamp4.getPosition().y + 20;
		
		m_Scene.addPointLight(pointLight4);
		m_Scene.addEntity(lamp4);
	}
	
	private void createEntities(Terrain terrain){
		Transform dragonTransform = new Transform(new Vector3f(320.0f, 0.0f, 200.0f), 
											      new Quaternion(new Vector3f(0,1,0), 90.0f), 
											      new Vector3f(3,3,3));

//		Dragon dragon = new Dragon("Dragon", dragonTransform);
//		dragon.getPosition().y = terrain.getHeightOfTerrain(dragon.getPosition().x, dragon.getPosition().z) + 9;
		
		Transform crateTransform = new Transform(new Vector3f(140.0f, 40.0f, 200.0f), 
											     new Quaternion(new Vector3f(0,0,0), 0.0f), 
											     new Vector3f(0.2f,0.2f,0.2f));
		
		Crate crate = new Crate("Crate", crateTransform);
//		crate.getPosition().y = terrain.getHeightOfTerrain(crate.getPosition().x, crate.getPosition().z) + 32;
		
		Transform barrelTransform = new Transform(new Vector3f(240.0f, 40.0f, 200.0f), 
								    new Quaternion(new Vector3f(0,0,0), 90.0f), 
								    new Vector3f(3f,3f,3f));
		
		Barrel barrel = new Barrel("Barrel", barrelTransform);
//		barrel.getPosition().y = terrain.getHeightOfTerrain(barrel.getPosition().x, barrel.getPosition().z) + 25;
		
//		m_Scene.addEntity(dragon);
		m_Scene.addEntity(crate);
		m_Scene.addEntity(barrel);
	}
	
	@Override
	public void update(float dt) {
		if(KeyInput.isKeyDown(GLFW.GLFW_KEY_B)){
			gsm.pushState(new PauseState(gsm));
		}
		m_Scene.update(dt);
	}

	@Override
	public void render() {
		m_Scene.render(m_Renderer);
	}

	@Override
	public void dispose() {
		Debug.log("PLAY STATE DISPOSED");
		Loader.dispose();
		m_Renderer.dispose();
	}
}
