package com.dersgames.examplegame.scenes;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.core.Entity;
import com.dersgames.engine.core.Scene;
import com.dersgames.engine.core.Transform;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.lights.DirectionalLight;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.models.ModelManager;
import com.dersgames.engine.graphics.shaders.ShaderManager;
import com.dersgames.engine.graphics.textures.TerrainTexturePack;
import com.dersgames.engine.graphics.textures.Texture;
import com.dersgames.engine.graphics.textures.TextureManager;
import com.dersgames.engine.graphics.water.WaterTile;
import com.dersgames.engine.graphics.water.WaterUpdate;
import com.dersgames.engine.math.Quaternion;
import com.dersgames.engine.math.Vector3f;
import com.dersgames.engine.terrain.Terrain;
import com.dersgames.engine.util.Randomizer;
import com.dersgames.examplegame.components.BarrelRotator;
import com.dersgames.examplegame.components.CrateRotator;

public class TestScene extends Scene {

	public TestScene(String name) {
		super(name);
	}
	
	@Override
	public void initEntities() {
		initCamera();
		initTerrain();
//		initWater();
		initCrate(50);
		initBarrel(50);
	}

	@Override
	public void initLightSetup() {
		Transform sunTransform = new Transform(new Vector3f(5000,5000,5000),
				   new Quaternion(new Vector3f(1,0,0), 40.0f),
				   new Vector3f(1,1,1));

		DirectionalLight sun = new DirectionalLight("DirectionalLight", sunTransform,
				   new Vector3f(1.0f, 0.7f, 0.7f),
				   3.0f);
		
		addDirectionalLight(sun);
	}
	
	private void initCamera() {
		Entity camera = new Entity("MainCamera", 200, 50, 100);
		Camera cameraComponent = new Camera(2.0f);
		camera.addComponent(cameraComponent);
		RenderEngine.getInstance().addCamera(cameraComponent);
		addEntity(camera);
	}
	
	private void initTerrain(){
		Texture backgroundTexture = new Texture(TextureManager.getInstance().loadModelTexture("grassy"));
		Texture rTexture 		  = new Texture(TextureManager.getInstance().loadModelTexture("mud"));
		Texture gTexture 		  = new Texture(TextureManager.getInstance().loadModelTexture("grassFlowers"));
		Texture bTexture 		  = new Texture(TextureManager.getInstance().loadModelTexture("bricks"));
		Texture blendMap 		  = new Texture(TextureManager.getInstance().loadModelTexture("blendMap"));
		
		Material backgroundMaterial =  new Material(backgroundTexture, 
				RenderEngine.getInstance().getTerrainRenderer().getShader());
		
		Material rMaterial =  new Material(rTexture, 
				RenderEngine.getInstance().getTerrainRenderer().getShader());
		
		Material gMaterial =  new Material(gTexture, 
				RenderEngine.getInstance().getTerrainRenderer().getShader());
		
		Material bMaterial =  new Material(bTexture, new Vector3f(1.0f, 1.0f, 1.0f),
				new Vector3f(0.2f, 0.2f, 0.2f), 
				new Vector3f(0, 0, 0), 
				0.0f,
				false, 
				false,
				 RenderEngine.getInstance().getTerrainRenderer().getShader());
		
		TerrainTexturePack texturePack   = new TerrainTexturePack();
		
//		texturePack.addTerrainMaterial(backgroundMaterial);
//		texturePack.addTerrainMaterial(rMaterial);
//		texturePack.addTerrainMaterial(gMaterial);
		texturePack.addTerrainMaterial(bMaterial);
		
		Entity terrainEntity = new Entity("Terrain", 0, 0, 0);
		//terrainEntity.getTransform().scale(2.0f, 2.0f, 2.0f);
//		Terrain terrain 	 = new Terrain("Terrain", 0, 0, texturePack, blendMap, "heightmap");
		Terrain terrain 	 = new Terrain("Terrain", 0, 0, texturePack, blendMap);
		terrainEntity.addRenderableComponent(terrain);
		
		addEntity(terrainEntity);
	}
	
	private void initWater() {
		Entity water = new Entity("Water", 200.0f, -2.0f, 200.0f);
		WaterTile tile  = new WaterTile("WaterTile");
		float scale = 3.25f;
		water.getTransform().scale(WaterTile.TILE_SIZE * scale, WaterTile.TILE_SIZE * scale, WaterTile.TILE_SIZE * scale);
		water.addRenderableComponent(tile);
		water.addComponent(new WaterUpdate("WaterUpdate"));
		addEntity(water);
		RenderEngine.getInstance().water = tile;
	}
	
	private void initCrate(int num) {
		Model crateModel = ModelManager.getInstance().loadModelFromObjFile("crate", true);
		
		Texture crateTexture   = new Texture(TextureManager.getInstance().loadModelTexture("crate"));
		Texture crateNormalMap = new Texture(TextureManager.getInstance().loadModelTexture("crateNormalMap"));
		
		Material crateMaterial = new Material(crateTexture, crateNormalMap,
				   new Vector3f(0.6f, 0.6f, 0.6f),
				   new Vector3f(0.8f, 0.8f, 0.8f),
				   new Vector3f(0.02f, 0.02f, 0.02f),
				   8.0f, false, false,
				   ShaderManager.getInstance().getShader(ShaderManager.DEFAULT_NORMALMAP_SHADER));

		for (int i = 0; i < num; i++) {
			float x = Randomizer.getFloat(0, 380);
			float z = Randomizer.getFloat(0, 380);
			
			Transform crateTransform = new Transform(new Vector3f(x, 7, z),
				     new Quaternion(new Vector3f(0,0,0), 0.0f), 
				     new Vector3f(0.07f,0.07f,0.07f));
			
			Entity crate = new Entity("crate", crateTransform);
			crate.addRenderableComponent(new StaticMesh("CrateStaticMesh", crateModel, crateMaterial));
			crate.addComponent(new BarrelRotator("CrateMovement", 15.0f));
			
			addEntity(crate);
		}
	}
	
	private void initBarrel(int num) {
		Model barrelModel = ModelManager.getInstance().loadModelFromObjFile("barrel", true);
		
		Texture barrelTexture 	   = new Texture(TextureManager.getInstance().loadModelTexture("barrel"));
		Texture barrelSpecularMap  = new Texture(TextureManager.getInstance().loadModelTexture("barrelSpecularMap"));
		Texture barrelNormalMap    = new Texture(TextureManager.getInstance().loadModelTexture("barrelNormalMap"));
		
		Material barrelMaterial = new Material(barrelTexture, barrelSpecularMap, barrelNormalMap,
					   new Vector3f(0.6f, 0.6f, 0.6f),
					   new Vector3f(0.8f, 0.8f, 0.8f),
					   new Vector3f(0.02f, 0.02f, 0.02f),
					   16.0f, false, false,
					   ShaderManager.getInstance().getShader(ShaderManager.DEFAULT_NORMALMAP_SHADER));
		
		for (int i = 0; i < num; i++) {
			float x = Randomizer.getFloat(0, 380);
			float z = Randomizer.getFloat(0, 380);
			
			Transform barrelTransform = new Transform(new Vector3f(x, 6, z),
				     new Quaternion(new Vector3f(0,0,0), 0.0f), 
				     new Vector3f(1f,1f,1f));
			
			Entity barrel = new Entity("barrel", barrelTransform);

			barrel.addRenderableComponent(new StaticMesh("BarrelStaticMesh", barrelModel, barrelMaterial));
			barrel.addComponent(new BarrelRotator("BarrelMovement", 10.0f));
	       
			addEntity(barrel);
		}
	}
}
