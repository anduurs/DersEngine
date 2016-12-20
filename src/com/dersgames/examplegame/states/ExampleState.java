package com.dersgames.examplegame.states;

import java.util.Random;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.lights.DirectionalLight;
import com.dersgames.engine.components.lights.SpotLight;
import com.dersgames.engine.core.GameState;
import com.dersgames.engine.core.GameStateManager;
import com.dersgames.engine.core.Quaternion;
import com.dersgames.engine.core.Scene;
import com.dersgames.engine.core.Transform;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.textures.TerrainTexturePack;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.terrains.Terrain;
import com.dersgames.engine.utils.ImageManager;
import com.dersgames.examplegame.entities.Barrel;
import com.dersgames.examplegame.entities.Dragon;
import com.dersgames.examplegame.entities.PointLightLamp;

public class ExampleState extends GameState{
	
	private RenderEngine m_Renderer;
	private Loader m_Loader;
	private Scene m_Scene;
	
	public ExampleState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		//ENTITIES TEXTURES
		ImageManager.addImage("grassTexture", "grassTexture.png");
		ImageManager.addImage("fern", "fern.png");
		ImageManager.addImage("tree", "tree.png");
		ImageManager.addImage("box", "box.png");
		ImageManager.addImage("dragontexture", "dragontexture.png");
		ImageManager.addImage("lamp", "lamp.png");
		ImageManager.addImage("barrel", "barrel.png");
		
		//SPECULAR MAPS
		ImageManager.addImage("barrelSpecularMap", "barrelS.png");
		
		//NORMAL MAPS
		ImageManager.addImage("barrelNormalMap", "barrelNormal.png");
		
		//TERRAIN TEXTURES
		ImageManager.addImage("grassy", "grassy2.png");
		ImageManager.addImage("grassFlowers", "grassFlowers.png");
		ImageManager.addImage("mud", "mud.png");
		ImageManager.addImage("path", "path.png");
		ImageManager.addImage("blendMap", "blendMap.png");
		ImageManager.addImage("heightmap", "heightmap.png");
		
		//PLAYER TEXTURE
		ImageManager.addImage("player", "playerTexture.png");
		
		m_Renderer = new RenderEngine();
		m_Loader   = new Loader();
		m_Scene    = new Scene();
		
		createCamera();
		
		Terrain terrain = generateTerrain();
		
		createLightSources(terrain);
		createEntities(terrain);
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
		Texture bTexture 		 = new Texture(Loader.loadModelTexture("path"));
		Texture blendMap 		 = new Texture(Loader.loadModelTexture("blendMap"));
		
		TerrainTexturePack texturePack   = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		Entity terrainEntity = new Entity("Terrain");
		Terrain terrain 	 = new Terrain("Terrain", 0, 0, m_Loader, texturePack, blendMap, "heightmap");
//		Terrain terrain 	 = new Terrain("Terrain", 0, 0, m_Loader, texturePack, blendMap);
		terrainEntity.addComponent(terrain);
		
		m_Scene.addEntity(terrainEntity);
		
		return terrain;
	}
	
	private void createLightSources(Terrain terrain){
		Transform sunTransform = new Transform(new Vector3f(0,0,0), 
											   new Quaternion(new Vector3f(1,0,0), 40.0f), 
											   new Vector3f(1,1,1));
		
		Entity directionalLight = new Entity("DirectionalLight", sunTransform);
		DirectionalLight sun = new DirectionalLight("DirectionalLight", 
											   new Vector3f(0.8f, 0.8f, 0.8f),
											   0.2f);
		directionalLight.addComponent(sun);
		m_Scene.addDirectionalLight(sun);	
	
		
		Transform spotLight1Transform = new Transform(new Vector3f(200.0f, 20.0f, 150.0f), 
				   									  new Quaternion(new Vector3f(1,0,0), 90.0f), 
				   									  new Vector3f(1,1,1));
		
		Entity flashLight = new Entity("SpotLight1", spotLight1Transform);
		SpotLight spotLight = new SpotLight("SpotLight1", new Vector3f(1.0f, 1.0f, 1.0f), 
														  new Vector3f(1.0f, 0.01f, 0.002f),
														  5.0f,
														  500.0f,
														  0.95f);
		flashLight.addComponent(spotLight);
//		m_Scene.addSpotLight(spotLight);
		
		Random random = new Random();
		
		
		
		for(int i = 0; i < 32; i ++){
			PointLightLamp lamp = new PointLightLamp("Lamp1", random.nextFloat() * 800, 0, random.nextFloat() * 800);
			lamp.getPosition().y = terrain.getHeightOfTerrain(lamp.getPosition().x, lamp.getPosition().z);
			lamp.getPointLight().getEntity().getPosition().y = lamp.getPosition().y + 5;
			m_Scene.addPointLight(lamp.getPointLight());
			m_Scene.addEntity(lamp);
		}
	}
	
	private void createEntities(Terrain terrain){
//		Transform dragonTransform = new Transform(new Vector3f(250.0f, 0.0f, 200.0f), 
//			      new Quaternion(new Vector3f(0,1,0), 90.0f), 
//			      new Vector3f(3,3,3));
//
//		Dragon dragon = new Dragon("Dragon", dragonTransform);
//		dragon.getPosition().y = terrain.getHeightOfTerrain(dragon.getPosition().x, dragon.getPosition().z) + 9;
//		m_Scene.addEntity(dragon);


		Transform barrelTransform = new Transform(new Vector3f(200.0f, 0.0f, 200.0f), 
					      new Quaternion(new Vector3f(0,0,0), 90.0f), 
					      new Vector3f(3,3,3));
		
		Barrel barrel = new Barrel("Barrel", barrelTransform, m_Loader);
		barrel.getPosition().y = terrain.getHeightOfTerrain(barrel.getPosition().x, barrel.getPosition().z) + 20;
		m_Scene.addEntity(barrel);
	}
	
	@Override
	public void update(float dt) {
		m_Scene.update(dt);
	}

	@Override
	public void render() {
		m_Scene.render(m_Renderer);
	}

	@Override
	public void dispose() {
		Loader.dispose();
		m_Renderer.dispose();
	}
}
