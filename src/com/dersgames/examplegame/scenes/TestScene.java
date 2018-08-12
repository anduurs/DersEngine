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
import com.dersgames.engine.graphics.textures.lightingmaps.NormalMap;
import com.dersgames.engine.graphics.textures.lightingmaps.SpecularMap;
import com.dersgames.engine.graphics.water.WaterTile;
import com.dersgames.engine.graphics.water.WaterUpdate;
import com.dersgames.engine.maths.Quaternion;
import com.dersgames.engine.maths.Vector3f;
import com.dersgames.engine.terrains.Terrain;
import com.dersgames.examplegame.components.BarrelRotator;
import com.dersgames.examplegame.components.RotatorComponent;

public class TestScene extends Scene {

	public TestScene(String name) {
		super(name);
	}
	
	@Override
	public void initEntities() {
		initCamera();
		initTerrain();
		initWater();
		initCrate();
		initBarrel();
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
		Entity camera = new Entity("MainCamera", 200, 0, 100);
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
		
		texturePack.addTerrainMaterial(backgroundMaterial);
		texturePack.addTerrainMaterial(rMaterial);
		texturePack.addTerrainMaterial(gMaterial);
		texturePack.addTerrainMaterial(bMaterial);
		
		Entity terrainEntity = new Entity("Terrain", 0, 0, 0);
		//terrainEntity.getTransform().scale(2.0f, 2.0f, 2.0f);
		Terrain terrain 	 = new Terrain("Terrain", 0, 0, texturePack, blendMap, "heightmap");
		//Terrain terrain 	 = new Terrain("Terrain", 0, 0, m_Loader, texturePack, blendMap);
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
	
	private void initCrate() {
		Transform crateTransform = new Transform(new Vector3f(160.0f, 80.0f, 200.0f),
			     new Quaternion(new Vector3f(0,0,0), 0.0f), 
			     new Vector3f(0.2f,0.2f,0.2f));
		
		Entity crate = new Entity("crate", crateTransform);
		
		Texture crateTexture = new Texture(TextureManager.getInstance().loadModelTexture("crate"), 1);
		NormalMap crateNormalMap = new NormalMap(TextureManager.getInstance().loadModelTexture("crateNormalMap"));
		
		Material crateMaterial = new Material(crateTexture, crateNormalMap,
				   new Vector3f(0.6f, 0.6f, 0.6f),
				   new Vector3f(0.8f, 0.8f, 0.8f),
				   new Vector3f(0.02f, 0.02f, 0.02f),
				   8.0f, false, false,
				   ShaderManager.getInstance().getShader(ShaderManager.DEFAULT_ENTITY_SHADER));
		
		Model crateModel = ModelManager.getInstance().loadModelFromObjFile("crate", true);
		
		crate.addRenderableComponent(new StaticMesh("CrateStaticMesh", crateModel, crateMaterial));
		crate.addComponent(new RotatorComponent("CrateMovement", 15.0f));
		
		addEntity(crate);
	}
	
	private void initBarrel() {
		Transform barrelTransform = new Transform(new Vector3f(300.0f, 80.0f, 200.0f),
			     new Quaternion(new Vector3f(0,0,0), 0.0f), 
			     new Vector3f(5f,5f,5f));
		
		Entity barrel = new Entity("barrel", barrelTransform);
		
		Texture barrelTexture = new Texture(TextureManager.getInstance().loadModelTexture("barrel"));
		SpecularMap barrelSpecularMap  = new SpecularMap(TextureManager.getInstance().loadModelTexture("barrelSpecularMap"));
		NormalMap barrelNormalMap = new NormalMap(TextureManager.getInstance().loadModelTexture("barrelNormalMap"));
		
		Model barrelModel = ModelManager.getInstance().loadModelFromObjFile("barrel", true);
		
       Material barrelMaterial = new Material(barrelTexture, barrelSpecularMap, barrelNormalMap,
					   new Vector3f(0.6f, 0.6f, 0.6f),
					   new Vector3f(0.8f, 0.8f, 0.8f),
					   new Vector3f(0.02f, 0.02f, 0.02f),
					   16.0f, false, false,
					   ShaderManager.getInstance().getShader(ShaderManager.DEFAULT_NORMALMAP_SHADER));
       
       barrel.addRenderableComponent(new StaticMesh("BarrelStaticMesh", barrelModel, barrelMaterial));
       barrel.addComponent(new BarrelRotator("BarrelMovement", 10.0f));
       
       addEntity(barrel);
	}
}
