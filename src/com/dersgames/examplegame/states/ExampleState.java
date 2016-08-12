package com.dersgames.examplegame.states;

import java.util.Random;

import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.components.lights.Light;
import com.dersgames.engine.core.Camera;
import com.dersgames.engine.core.GameState;
import com.dersgames.engine.core.GameStateManager;
import com.dersgames.engine.core.Scene;
import com.dersgames.engine.core.Vector3f;
import com.dersgames.engine.entities.Entity;
import com.dersgames.engine.graphics.ModelLoader;
import com.dersgames.engine.graphics.models.TexturedModel;
import com.dersgames.engine.graphics.renderers.Renderer3D;
import com.dersgames.engine.graphics.textures.ModelTexture;
import com.dersgames.engine.graphics.textures.TerrainTexture;
import com.dersgames.engine.graphics.textures.TerrainTexturePack;
import com.dersgames.engine.terrains.Terrain;
import com.dersgames.engine.utils.ImageManager;
import com.dersgames.examplegame.entities.Player;

public class ExampleState extends GameState{
	
	private Renderer3D m_Renderer;
	private ModelLoader m_Loader;
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
		
		//TERRAIN TEXTURES
		ImageManager.addImage("grassy", "grassy2.png");
		ImageManager.addImage("grassFlowers", "grassFlowers.png");
		ImageManager.addImage("mud", "mud.png");
		ImageManager.addImage("path", "path.png");
		ImageManager.addImage("blendMap", "blendMap.png");
		ImageManager.addImage("heightmap", "heightmap.png");
		
		//PLAYER TEXTURE
		ImageManager.addImage("player", "playerTexture.png");
		
		m_Renderer = new Renderer3D();
		m_Loader = new ModelLoader();
		m_Scene = new Scene();
		
		addLightSource();
		generateTerrain();
		
		
		
		Player player = new Player(m_Loader, 200, 20, 150);
		m_Scene.addEntity(player);
		
		m_Scene.addCamera(new Camera(player, new Vector3f(200,20,100)));
	}
	
	private void addLightSource(){
		Entity lightSource = new Entity("LightSource", 200, 200, 100);
		Light light = new Light("Sun", new Vector3f(1.0f, 1.0f, 1.0f));
		lightSource.addComponent(light);
		m_Scene.addLightSource(light);
	}
	
	private void generateTerrain(){
		TerrainTexture backgroundTexture = new TerrainTexture(m_Loader.loadTexture("grassy"));
		TerrainTexture rTexture 		 = new TerrainTexture(m_Loader.loadTexture("mud"));
		TerrainTexture gTexture 		 = new TerrainTexture(m_Loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture 		 = new TerrainTexture(m_Loader.loadTexture("path"));
		TerrainTexture blendMap 		 = new TerrainTexture(m_Loader.loadTexture("blendMap"));
		
		TerrainTexturePack texturePack   = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		Entity terrainEntity = new Entity("Terrain");
		Terrain terrain 	 = new Terrain("Terrain", 0, 0, m_Loader, texturePack, blendMap, "heightmap");
		terrainEntity.addComponent(terrain);
		
//		Entity terrainEntity2 = new Entity("Terrain2");
//		Terrain terrain2 	  = new Terrain("TerrainComponent2", 1, 0, m_Loader, texturePack, blendMap, "heightmap");
//		terrainEntity2.addComponent(terrain2);
		
		m_Scene.addEntity(terrainEntity);
//		m_Scene.addEntity(terrainEntity2);
		
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
		
		for(int i = 0; i < 200; i++){
//			Entity tree = new Entity("Tree" + i, random.nextFloat() * 800, 0, random.nextFloat() * 800, 10);
//			tree.getPosition().y = terrain.getHeightOfTerrain(tree.getPosition().x, tree.getPosition().z);
//			treeModel.getModelTexture().setShineDamper(10.0f);
//			treeModel.getModelTexture().setReflectivity(1.0f);
//			tree.addComponent(new StaticMesh("treemesh" + i, treeModel));
//			m_Scene.addEntity(tree);
			
			Entity grass = new Entity("Grass" + i, random.nextFloat() * 800, 0, random.nextFloat() * 800);
			grass.getPosition().y = terrain.getHeightOfTerrain(grass.getPosition().x, grass.getPosition().z);
			grassModel.getModelTexture().setShineDamper(1.0f);
			grassModel.getModelTexture().setReflectivity(0.0f);
			grass.addComponent(new StaticMesh("grassmesh" + i, grassModel));
			m_Scene.addEntity(grass);
			
			Entity fern = new Entity("Fern" + i, random.nextFloat() * 800, 0, random.nextFloat() * 800);
			fern.getPosition().y = terrain.getHeightOfTerrain(fern.getPosition().x, fern.getPosition().z);
			fernModel.getModelTexture().setShineDamper(1.0f);
			fernModel.getModelTexture().setReflectivity(0.0f);
			fern.addComponent(new StaticMesh("fernmesh" + i, fernModel));
			m_Scene.addEntity(fern);
		}
		
		Entity box = new Entity("Box", 200, 7, 200, 10);
		box.getPosition().y = terrain.getHeightOfTerrain(box.getPosition().x, box.getPosition().z) + 7;
		TexturedModel boxModel = new TexturedModel(m_Loader.loadObjModel("box"), 
				new ModelTexture(m_Loader.loadTexture("box")));
		box.addComponent(new StaticMesh("BoxMesh", boxModel));
		m_Scene.addEntity(box);
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
