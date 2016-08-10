package com.dersgames.testgame.states;

import java.util.Random;

import com.dersgames.game.components.StaticMesh;
import com.dersgames.game.components.lights.Light;
import com.dersgames.game.core.Camera;
import com.dersgames.game.core.GameState;
import com.dersgames.game.core.GameStateManager;
import com.dersgames.game.core.Scene;
import com.dersgames.game.core.Vector3f;
import com.dersgames.game.entities.Entity;
import com.dersgames.game.graphics.ModelLoader;
import com.dersgames.game.graphics.Renderer3D;
import com.dersgames.game.graphics.models.TexturedModel;
import com.dersgames.game.graphics.textures.ModelTexture;
import com.dersgames.game.graphics.textures.TerrainTexture;
import com.dersgames.game.graphics.textures.TerrainTexturePack;
import com.dersgames.game.terrains.Terrain;
import com.dersgames.game.utils.ImageManager;
import com.dersgames.testgame.entities.Player;

public class PlayState extends GameState{
	
	private Renderer3D m_Renderer;
	private ModelLoader m_Loader;
	private Scene m_Scene;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		ImageManager.addImage("stall", "stallTexture.png");
		ImageManager.addImage("test", "test.png");
		ImageManager.addImage("dragon", "dragontexture.png");
		ImageManager.addImage("grass", "grass.png");
		
		ImageManager.addImage("grassTexture", "grassTexture.png");
		ImageManager.addImage("fern", "fern.png");
		ImageManager.addImage("tree", "tree.png");
		
		ImageManager.addImage("grassy", "grassy2.png");
		ImageManager.addImage("grassFlowers", "grassFlowers.png");
		ImageManager.addImage("mud", "mud.png");
		ImageManager.addImage("path", "path.png");
		
		ImageManager.addImage("blendMap", "blendMap.png");
		
		ImageManager.addImage("player", "playerTexture.png");
		
		m_Renderer = new Renderer3D();
		m_Loader = new ModelLoader();
		m_Scene = new Scene();
		
		addLightSource();
		generateTerrain();
		generateEntities(100);
		
		Player player = new Player(m_Loader, 200, 0, 150);
		m_Scene.addEntity(player);
		m_Scene.addCamera(new Camera(player, new Vector3f(200,20,100)));
	}
	
	private void addLightSource(){
		Entity lightSource = new Entity("LightSource", 200, 200, 100);
		Light light = new Light("Sun", new Vector3f(1.0f, 1.0f, 1.0f));
		lightSource.addComponent(light);
		m_Scene.addSun(light);
		m_Scene.addEntity(lightSource);
	}
	
	private void generateTerrain(){
		TerrainTexture backgroundTexture = new TerrainTexture(m_Loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(m_Loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(m_Loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(m_Loader.loadTexture("path"));
		TerrainTexture blendMap = new TerrainTexture(m_Loader.loadTexture("blendMap"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		Entity terrainEntity = new Entity("Terrain1");
		Terrain terrain = new Terrain("TerrainComponent1", 0, 0, m_Loader, texturePack, blendMap);
		terrainEntity.addComponent(terrain);
		
		Entity terrainEntity2 = new Entity("Terrain2");
		Terrain terrain2 = new Terrain("TerrainComponent2", 1, 0, m_Loader, texturePack, blendMap);
		terrainEntity2.addComponent(terrain2);
		
		m_Scene.addEntity(terrainEntity);
		m_Scene.addEntity(terrainEntity2);
	}
	
	private void generateEntities(int numOfEntities){
		TexturedModel treeModel = new TexturedModel(m_Loader.loadObjModel("tree"), 
				new ModelTexture(m_Loader.loadTexture("tree")));
		
		TexturedModel grassModel = new TexturedModel(m_Loader.loadObjModel("grassModel"), 
				new ModelTexture(m_Loader.loadTexture("grassTexture")));
		grassModel.getModelTexture().setTransparency(true);
		grassModel.getModelTexture().setUseFakeLighting(true);
		
		TexturedModel fernModel = new TexturedModel(m_Loader.loadObjModel("fern"), 
				new ModelTexture(m_Loader.loadTexture("fern")));
		fernModel.getModelTexture().setTransparency(true);
		
		Random random = new Random();
		
		for(int i = 0; i < numOfEntities; i++){
//			Entity tree = new Entity("Tree" + i, random.nextFloat() * 800, 0, random.nextFloat() * 800, 10);
//			treeModel.getModelTexture().setShineDamper(10.0f);
//			treeModel.getModelTexture().setReflectivity(1.0f);
//			tree.addComponent(new StaticMesh("treemesh" + i, treeModel));
			
			Entity grass = new Entity("Grass" + i, random.nextFloat() * 800, 0, random.nextFloat() * 800);
			grassModel.getModelTexture().setShineDamper(1.0f);
			grassModel.getModelTexture().setReflectivity(0.0f);
			grass.addComponent(new StaticMesh("grassmesh" + i, grassModel));
			
			Entity fern = new Entity("Fern" + i, random.nextFloat() * 800, 0, random.nextFloat() * 800);
			fernModel.getModelTexture().setShineDamper(1.0f);
			fernModel.getModelTexture().setReflectivity(0.0f);
			fern.addComponent(new StaticMesh("fernmesh" + i, fernModel));
			
//			m_Scene.addEntity(tree);
			m_Scene.addEntity(grass);
			m_Scene.addEntity(fern);
		}
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
	public void cleanUp() {
		m_Loader.cleanUp();
		m_Renderer.cleanUp();
	}
}
