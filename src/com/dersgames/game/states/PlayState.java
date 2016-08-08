package com.dersgames.game.states;

import java.util.Random;

import com.dersgames.game.components.MovementComponent;
import com.dersgames.game.components.StaticMesh;
import com.dersgames.game.components.lights.Light;
import com.dersgames.game.core.Camera;
import com.dersgames.game.core.GameState;
import com.dersgames.game.core.GameStateManager;
import com.dersgames.game.core.Matrix4f;
import com.dersgames.game.core.Scene;
import com.dersgames.game.core.Vector3f;
import com.dersgames.game.entities.Entity;
import com.dersgames.game.graphics.ModelLoader;
import com.dersgames.game.graphics.Renderer3D;
import com.dersgames.game.graphics.Window;
import com.dersgames.game.graphics.models.TexturedModel;
import com.dersgames.game.graphics.shaders.StaticShader;
import com.dersgames.game.graphics.textures.ModelTexture;
import com.dersgames.game.terrains.Terrain;
import com.dersgames.game.utils.ImageManager;

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
		
		
		m_Renderer = new Renderer3D();
		m_Loader = new ModelLoader();
		m_Scene = new Scene();
		
		TexturedModel texturedModel = new TexturedModel(m_Loader.loadObjModel("dragon"), 
				new ModelTexture(m_Loader.loadTexture("dragon")));
		
		ModelTexture grassTexture = new ModelTexture(m_Loader.loadTexture("grass"));
//		grassTexture.setShineDamper(1.0f);
//		grassTexture.setReflectivity(1.0f);
		
		Entity terrainEntity = new Entity("Terrain1");
		Terrain terrain = new Terrain("TerrainComponent1", 0, 0, m_Loader, grassTexture);
		terrainEntity.addComponent(terrain);
		m_Scene.addEntity(terrainEntity);
		
		Entity terrainEntity2 = new Entity("Terrain2");
		Terrain terrain2 = new Terrain("TerrainComponent2", 1, 0, m_Loader, grassTexture);
		terrainEntity.addComponent(terrain2);
		m_Scene.addEntity(terrainEntity2);
		
		Entity entity = new Entity("Entity0", 0, 0, 20);
		texturedModel.getModelTexture().setShineDamper(10.0f);
		texturedModel.getModelTexture().setReflectivity(1.0f);
		entity.addComponent(new StaticMesh("StaticMesh0", texturedModel));
		entity.addComponent(new MovementComponent("MovementTest"));
		m_Scene.addEntity(entity);
		
//		stressTest(200);
		
		Entity lightSource = new Entity("LightSource", 200, 200, 100);
		Light light = new Light("Sun", new Vector3f(1.0f, 1.0f, 1.0f));
		lightSource.addComponent(light);
		m_Scene.addSun(light);
		m_Scene.addEntity(lightSource);
		
		m_Scene.addCamera(new Camera(new Vector3f(200,20,200)));
	}
	
	private void stressTest(int numOfEntities){
		TexturedModel texturedModel = new TexturedModel(m_Loader.loadObjModel("dragon"), 
				new ModelTexture(m_Loader.loadTexture("dragon")));
		Random random = new Random();
		for(int i = 0; i < numOfEntities; i++){
			float x = random.nextFloat() * 100 - 50;
			float y = random.nextFloat() * 100 - 50;
			float z = random.nextFloat() * -300;
			Entity entity = new Entity("Entity" + i, x, y, z);
			texturedModel.getModelTexture().setShineDamper(10.0f);
			texturedModel.getModelTexture().setReflectivity(1.0f);
			entity.addComponent(new StaticMesh("StaticMesh" + i, texturedModel));
			entity.addComponent(new MovementComponent("MovementTest" + i));
			m_Scene.addEntity(entity);
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
