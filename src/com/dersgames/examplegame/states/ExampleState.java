package com.dersgames.examplegame.states;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.components.lights.DirectionalLight;
import com.dersgames.engine.components.lights.PointLight;
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
		
		//TERRAIN TEXTURES
		ImageManager.addImage("grassy", "grassy2.png");
		ImageManager.addImage("grassFlowers", "grassFlowers.png");
		ImageManager.addImage("mud", "mud.png");
		ImageManager.addImage("path", "path.png");
		ImageManager.addImage("blendMap", "blendMap.png");
		ImageManager.addImage("heightmap", "heightmap.png");
		
		//PLAYER TEXTURE
		ImageManager.addImage("player", "playerTexture.png");
		
		//GUI SHÌZZLE
		ImageManager.addImage("gui", "test.png");
		
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
											   new Quaternion(0.0f, -1.0f, 0.3f, 0.0f), 
											   new Vector3f(1,1,1));
		
		Entity directionalLight = new Entity("DirectionalLight", sunTransform);
		
		float ambient = 0.06f;
		float diffuse = 0.5f;
		float specular = 1.0f;
		
		DirectionalLight sun = new DirectionalLight("DirectionalLight", 
											   new Vector3f(ambient, ambient, ambient),
											   new Vector3f(diffuse, diffuse, diffuse),
											   new Vector3f(specular, specular, specular),
											   0.5f);
		
		directionalLight.addComponent(sun);
		m_Scene.addDirectionalLight(sun);
		
		Entity pointLight1 = new Entity("PointLight1", 170.0f, 5.0f, 395.0f);
		PointLight redLight = new PointLight("PointLight1",  new Vector3f(0.6f, 0.1f, 0.1f),
				   										   new Vector3f(0.8f, 0.8f, 0.8f),
				   										   new Vector3f(1.0f, 1.0f, 1.0f), 
				   										   new Vector3f(0, 0.014f, 0.0007f), 
				   										   1.8f);
		redLight.speed = 1.2f;
		pointLight1.addComponent(redLight);
		m_Scene.addPointLight(redLight);
		
		Entity pointLight2 = new Entity("PointLight2", 370, 10.0f, 395.0f);
		PointLight greenlight = new PointLight("PointLight2", new Vector3f(0.1f, 0.6f, 0.1f),
														   new Vector3f(0.8f, 0.8f, 0.8f),
														   new Vector3f(1.0f, 1.0f, 1.0f), 
														   new Vector3f(0, 0.014f, 0.0007f),
														   1.8f);
		greenlight.speed = 0.6f;
		pointLight2.addComponent(greenlight);
		m_Scene.addPointLight(greenlight);
		
		Entity pointLight3 = new Entity("PointLight3", 570, 5.0f, 395.0f);
		PointLight blueLight = new PointLight("PointLight3", new Vector3f(0.1f, 0.1f, 0.6f),
														   new Vector3f(0.8f, 0.8f, 0.8f),
														   new Vector3f(1.0f, 1.0f, 1.0f), 
														   new Vector3f(0, 0.014f, 0.0007f),
														   1.8f);
		blueLight.speed = 0.9f;
		pointLight3.addComponent(blueLight);
		m_Scene.addPointLight(blueLight);
	}
	
	private void generateTerrain(){
		TerrainTexture backgroundTexture = new TerrainTexture(m_Loader.loadModelTexture("grassy"));
		TerrainTexture rTexture 		 = new TerrainTexture(m_Loader.loadModelTexture("mud"));
		TerrainTexture gTexture 		 = new TerrainTexture(m_Loader.loadModelTexture("grassFlowers"));
		TerrainTexture bTexture 		 = new TerrainTexture(m_Loader.loadModelTexture("path"));
		TerrainTexture blendMap 		 = new TerrainTexture(m_Loader.loadModelTexture("blendMap"));
		
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
		TextureAtlas dragonTexture = new TextureAtlas(m_Loader.loadModelTexture("dragontexture"), 1);
		TexturedMesh dragonMesh    = new TexturedMesh(m_Loader.loadObjFile("dragon"), 
						                              new Material(dragonTexture, 
																   new Vector3f(0.5f, 0.5f, 0.5f),
																   new Vector3f(1.0f, 0.5f, 0.31f),
																   new Vector3f(0.5f, 0.5f, 0.5f),
																   32.0f,
																   m_Renderer.getEntityRenderer().getShader())); 
				
		Entity dragon = new Entity("Dragon", 370, 15.0f, 395.0f, 10);
//		box.getPosition().y = terrain.getHeightOfTerrain(box.getPosition().x, box.getPosition().z) + 9;
		dragon.addComponent(new StaticMesh("DragonStaticMesh", dragonMesh));
		dragon.addComponent(new MovementComponent("DragonMovement", 10.0f));
		m_Scene.addEntity(dragon);
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
		m_Loader.dispose();
		m_Renderer.dispose();
	}
}
