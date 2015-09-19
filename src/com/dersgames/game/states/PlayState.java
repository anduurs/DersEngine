package com.dersgames.game.states;

import java.util.ArrayList;
import java.util.List;

import com.dersgames.game.components.BasicMovement;
import com.dersgames.game.components.Camera2D;
import com.dersgames.game.components.Component;
import com.dersgames.game.components.InputComponent;
import com.dersgames.game.components.Sprite;
import com.dersgames.game.components.StaticLayer;
import com.dersgames.game.core.GameObject;
import com.dersgames.game.core.GameState;
import com.dersgames.game.core.GameStateManager;
import com.dersgames.game.core.Matrix4f;
import com.dersgames.game.core.SceneGraph;
import com.dersgames.game.graphics.BatchRenderer;
import com.dersgames.game.graphics.ColorRGBA;
import com.dersgames.game.graphics.Texture;
import com.dersgames.game.graphics.TextureRegion;
import com.dersgames.game.graphics.Window;
import com.dersgames.game.graphics.shaders.BasicShader;
import com.dersgames.game.graphics.shaders.Shader;
import com.dersgames.game.utils.Randomizer;

public class PlayState extends GameState{
	
	private SceneGraph m_SceneGraph;
	private BatchRenderer m_Batch;
	private Texture m_TextureAtlas;
	private Shader m_Shader;
	
	private List<GameObject> gameObjects;
	
	private GameObject m_mainCamera;
	
	private int timer = 0;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	@Override
	public void init() {
		m_Shader 		= new BasicShader();
		m_TextureAtlas  = new Texture("atlas");
		m_Batch 		= new BatchRenderer();
		m_SceneGraph 	= new SceneGraph();
		
		createTileLayer(500, 500);
		createPlayer();
		//createGameObjects(100);
	}
	
	private void createTileLayer(int width, int height){
		GameObject tileLayer = new GameObject("TileLayer");
		StaticLayer layer    = new StaticLayer("TileLayerMesh", width, height, 16, m_Shader);
		
		layer.addTexture(ColorRGBA.RED, new TextureRegion(m_TextureAtlas, 0, 0, 16, 16));
		layer.generateLayer();
		
		tileLayer.addComponent(layer);
		
		m_SceneGraph.addChild(tileLayer);
	}
	
	private void createPlayer(){
		GameObject player    = new GameObject("Player");
		TextureRegion region = new TextureRegion(m_TextureAtlas, 16*4, 16, 32, 32);
		
		player.addComponent(new InputComponent());
		player.addComponent(new Sprite("PlayerSprite", player.getTransform().getPosition(), region));
		
		m_mainCamera = new GameObject("Camera2D");
		Camera2D cameraComponent = new Camera2D("Camera2D", player, Window.getWidth(), Window.getHeight());
		m_mainCamera.addComponent(cameraComponent);
		cameraComponent.init();
		
		m_SceneGraph.addChild(player);
		m_SceneGraph.addChild(m_mainCamera);
	}
	
	private void createGameObjects(int num){
		gameObjects 		 = new ArrayList<GameObject>();
		TextureRegion region = new TextureRegion(m_TextureAtlas, 16*4, 16, 32, 32);
		
		for(int i = 0; i < num; i++){
			float y = Randomizer.getFloat(0, Window.getHeight()/2 - 16);
			GameObject go = new GameObject("Player", 0, y);
			float speed = Randomizer.getFloat(1f, 3.0f);
			go.addComponent(new BasicMovement(2.5f));
			go.addComponent(new Sprite("PlayerSprite", go.getTransform().getPosition(), region));
			gameObjects.add(go);
		}
	}
	
	private void generateGameObjects(){
		if(timer % 30 == 0){
			if(gameObjects.size() != 0){
				int index = Randomizer.getInt(0, gameObjects.size());
				if(gameObjects.get(index) != null){
					m_SceneGraph.addChild(gameObjects.get(index));
					gameObjects.remove(index);
				}
			}
		}
	}

	@Override
	public void update(float dt) {
		if(timer > 7500)timer = 0;
		else timer++;
		
		//generateGameObjects();
		
		m_SceneGraph.update(dt);
	}

	@Override
	public void render() {
		m_Shader.enable();
		Camera2D cam = (Camera2D)m_mainCamera.findComponentByTag("Camera2D");
		m_Shader.setUniform("view_matrix", cam.getViewMatrix());
		m_TextureAtlas.bind();
		
		m_Batch.begin();
		m_SceneGraph.render(m_Batch);
		m_Batch.end();
		m_Shader.setUniformi("is_Static", 0);
		m_Batch.flush();
		
		m_TextureAtlas.unbind();
		m_Shader.disable();
	}

}
