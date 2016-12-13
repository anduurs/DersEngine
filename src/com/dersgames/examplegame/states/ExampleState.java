package com.dersgames.examplegame.states;

import java.util.Random;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.components.lights.DirectionalLight;
import com.dersgames.engine.components.lights.PointLight;
import com.dersgames.engine.components.lights.SpotLight;
import com.dersgames.engine.core.GameState;
import com.dersgames.engine.core.GameStateManager;
import com.dersgames.engine.core.Quaternion;
import com.dersgames.engine.core.Scene;
import com.dersgames.engine.core.Transform;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.graphics.Loader;
import com.dersgames.engine.graphics.Material;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.models.TexturedMesh;
import com.dersgames.engine.graphics.textures.TerrainTexture;
import com.dersgames.engine.graphics.textures.TerrainTexturePack;
import com.dersgames.engine.graphics.textures.TextureAtlas;
import com.dersgames.engine.terrains.Terrain;
import com.dersgames.engine.utils.ImageManager;
import com.dersgames.examplegame.components.player.MovementComponent;
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
		ImageManager.addImage("barrelSpecularMap", "barrelS.png");
		
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
		
		addLightSources();
		generateTerrain();
		
//		Player player = new Player(m_Loader, 200, 20, 150);
//		m_Scene.addEntity(player);
	
		Entity camera = new Entity("MainCamera", 200, 20, 100);
		Camera cameraComponent = new Camera(2.0f);
		camera.addComponent(cameraComponent);
		m_Renderer.addCamera(cameraComponent);
		m_Scene.addEntity(camera);
	}
	
	private void addLightSources(){
		Transform sunTransform = new Transform(new Vector3f(0,0,0), 
											   new Quaternion(new Vector3f(1,1,0), 40.0f), 
											   new Vector3f(1,1,1));
		
		Entity directionalLight = new Entity("DirectionalLight", sunTransform);
		
		
		
		DirectionalLight sun = new DirectionalLight("DirectionalLight", 
											   new Vector3f(1.0f, 0.6f, 0.6f),
											   0.3f);
		
		directionalLight.addComponent(sun);
		
		
		float range = 500.0f;
		
		Entity pointLight1 = new Entity("PointLight1", 170.0f, 5.0f, 200.0f);
		PointLight redLight = new PointLight("PointLight1",  new Vector3f(0.6f, 0.1f, 0.1f),
				   										     new Vector3f(0, 0.014f, 0.0007f), 
				   										     1.8f,
				   										     range);
		redLight.speed = 1.2f;
		pointLight1.addComponent(redLight);
		
		
		
		Entity pointLight2 = new Entity("PointLight2", 370, 5.0f, 200.0f);
		PointLight greenlight = new PointLight("PointLight1",  new Vector3f(0.1f, 0.6f, 0.1f),
														       new Vector3f(0, 0.014f, 0.0007f), 
														       1.8f,
														       range);
		greenlight.speed = 0.6f;
		pointLight2.addComponent(greenlight);
	
		
		Entity pointLight3 = new Entity("PointLight3", 470, 5.0f, 200.0f);
		PointLight blueLight = new PointLight("PointLight3", new Vector3f(0.1f, 0.1f, 0.6f), 
														     new Vector3f(0, 0.014f, 0.0007f),
														     1.8f,
														     range);
		blueLight.speed = 0.9f;
		pointLight3.addComponent(blueLight);
		
	
		Entity pointLight4 = new Entity("PointLight4", 270, 5.0f, 200.0f);
		PointLight orangelight = new PointLight("PointLight4", new Vector3f(1.0f, 153.0f/255.0f, 51.0f/255.0f),
														       new Vector3f(0, 0.014f, 0.02f),
														       1.8f,
														       range);
		orangelight.speed = 0.9f;
		pointLight4.addComponent(orangelight);
	
		
		Transform spotLight1Transform = new Transform(new Vector3f(200.0f, 20.0f, 150.0f), 
				   									  new Quaternion(new Vector3f(1,0,0), 90.0f), 
				   									  new Vector3f(1,1,1));
		
		Entity spotLight1 = new Entity("SpotLight1", spotLight1Transform);
		
		SpotLight spotlight = new SpotLight("SpotLight1", new Vector3f(1.0f, 1.0f, 1.0f), 
														  new Vector3f(1.0f, 0.01f, 0.002f),
														  10.0f,
														  range,
														  0.9f);
		
		spotLight1.addComponent(spotlight);
		
		m_Scene.addDirectionalLight(sun);
		
//		m_Scene.addPointLight(redLight);
//		m_Scene.addPointLight(greenlight);
//		m_Scene.addPointLight(blueLight);
//		m_Scene.addPointLight(orangelight);
		
//		m_Scene.addSpotLight(spotlight);
	}
	
	private void generateTerrain(){
		TerrainTexture backgroundTexture = new TerrainTexture(Loader.loadModelTexture("grassy"));
		TerrainTexture rTexture 		 = new TerrainTexture(Loader.loadModelTexture("mud"));
		TerrainTexture gTexture 		 = new TerrainTexture(Loader.loadModelTexture("grassFlowers"));
		TerrainTexture bTexture 		 = new TerrainTexture(Loader.loadModelTexture("path"));
		TerrainTexture blendMap 		 = new TerrainTexture(Loader.loadModelTexture("blendMap"));
		
		TerrainTexturePack texturePack   = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		Entity terrainEntity = new Entity("Terrain");
		Terrain terrain 	 = new Terrain("Terrain", 0, 0, m_Loader, texturePack, blendMap, "heightmap");
//		Terrain terrain 	 = new Terrain("Terrain", 0, 0, m_Loader, texturePack, blendMap);
		terrainEntity.addComponent(terrain);
		
		m_Scene.addEntity(terrainEntity);
//	
//		TextureAtlas fernTexture  = new TextureAtlas(m_Loader.loadModelTexture("fern"), 2);
//		Material fernMaterial     = new Material(fernTexture, 1.0f, 0.0f, true, false);
//		TexturedMesh fernMesh     = new TexturedMesh(m_Loader.loadObjFile("fern"), fernMaterial); 
//		
//		TextureAtlas grassTexture = new TextureAtlas(m_Loader.loadModelTexture("grassTexture"), 1);
//		Material grassMaterial    = new Material(grassTexture, 1.0f, 0.0f, true, true);
//		TexturedMesh grassMesh    = new TexturedMesh(m_Loader.loadObjFile("grassModel"), grassMaterial); 
//		
//		Random random = new Random();
//		
//		for(int i = 0; i < 200; i++){
//			Entity grass = new Entity("Grass" + i, random.nextFloat() * 800, 0, random.nextFloat() * 800);
//			grass.getPosition().y = terrain.getHeightOfTerrain(grass.getPosition().x, grass.getPosition().z);
//			grass.addComponent(new StaticMesh("grassmesh" + i, grassMesh));
//			m_Scene.addEntity(grass);
//			
//			Entity fern = new Entity("Fern" + i, random.nextFloat() * 800, 0, random.nextFloat() * 800);
//			fern.getPosition().y = terrain.getHeightOfTerrain(fern.getPosition().x, fern.getPosition().z);
//			fern.addComponent(new StaticMesh("fernmesh" + i, fernMesh, random.nextInt(4)));
//			m_Scene.addEntity(fern);
//		}
//		
		Random random = new Random();
		
		for(int i = 0; i < 100; i ++){
			PointLightLamp lamp = new PointLightLamp("Lamp1", random.nextFloat() * 800, 0, random.nextFloat() * 800);
			lamp.getPosition().y = terrain.getHeightOfTerrain(lamp.getPosition().x, lamp.getPosition().z);
			lamp.getPointLight().getEntity().getPosition().y = lamp.getPosition().y + 5;
			m_Scene.addPointLight(lamp.getPointLight());
			m_Scene.addEntity(lamp);
		}
		
		
		TextureAtlas dragonTexture = new TextureAtlas(Loader.loadModelTexture("dragontexture"), 1);
		TexturedMesh dragonMesh    = new TexturedMesh(Loader.loadObjFile("dragon"), 
						                              new Material(dragonTexture, 
																   new Vector3f(0.6f, 0.6f, 0.6f),
																   new Vector3f(0.5f, 0.5f, 0.5f),
																   new Vector3f(0.0f, 0.0f, 0.0f),
																   32.0f,
																   RenderEngine.getEntityRenderer().getShader())); 
				
		Transform dragonTransform = new Transform(new Vector3f(200.0f, 0.0f, 250.0f), 
											      new Quaternion(new Vector3f(0,1,0), 90.0f), 
											      new Vector3f(3,3,3));
		
		Entity dragon = new Entity("Dragon", dragonTransform);
		dragon.getPosition().y = terrain.getHeightOfTerrain(dragon.getPosition().x, dragon.getPosition().z) + 9;
		dragon.addComponent(new StaticMesh("DragonStaticMesh", dragonMesh));
		dragon.addComponent(new MovementComponent("DragonMovement", 10.0f));
		m_Scene.addEntity(dragon);
		
		
		
		TextureAtlas barrelTexture = new TextureAtlas(Loader.loadModelTexture("barrel"), 1);
		TextureAtlas barrelSpecularMap = new TextureAtlas(Loader.loadModelTexture("barrelSpecularMap"), 1);
		
		TexturedMesh barrelMesh    = new TexturedMesh(Loader.loadObjFile("barrel"), 
						                              new Material(barrelTexture,barrelSpecularMap,
																   new Vector3f(0.6f, 0.6f, 0.6f),
																   new Vector3f(0.8f, 0.8f, 0.8f),
																   new Vector3f(0.0f, 0.0f, 0.0f),
																   64.0f,false,false,
																   RenderEngine.getEntityRenderer().getShader())); 
				
		Transform barrelTransform = new Transform(new Vector3f(200.0f, 0.0f, 200.0f), 
											      new Quaternion(new Vector3f(0,1,0), 90.0f), 
											      new Vector3f(3,3,3));
		
		Entity barrel = new Entity("Barrel", barrelTransform);
		barrel.getPosition().y = terrain.getHeightOfTerrain(barrel.getPosition().x, barrel.getPosition().z) + 13;
		barrel.addComponent(new StaticMesh("BarrelStaticMesh", barrelMesh));
		barrel.addComponent(new MovementComponent("BarrelMovement", 10.0f));
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
