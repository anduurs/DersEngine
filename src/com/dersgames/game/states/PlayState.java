package com.dersgames.game.states;

import com.dersgames.game.components.Camera2D;
import com.dersgames.game.components.InputComponent;
import com.dersgames.game.components.Sprite;
import com.dersgames.game.components.StaticLayer;
import com.dersgames.game.components.Transform;
import com.dersgames.game.core.GameObject;
import com.dersgames.game.core.GameState;
import com.dersgames.game.core.GameStateManager;
import com.dersgames.game.core.SceneGraph;
import com.dersgames.game.graphics.BatchRenderer;
import com.dersgames.game.graphics.ColorRGBA;
import com.dersgames.game.graphics.Texture;
import com.dersgames.game.graphics.TextureRegion;
import com.dersgames.game.graphics.Window;
import com.dersgames.game.graphics.shaders.BasicShader;
import com.dersgames.game.graphics.shaders.Shader;

public class PlayState extends GameState{
	
	private SceneGraph m_SceneGraph;
	private BatchRenderer m_Batch;
	private Texture m_TextureAtlas;
	private Shader m_Shader;
	
	private GameObject m_MainCamera;
	private GameObject m_Player;
	
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
		
		createTileLayer(20, 20);
		createPlayer();
		createCamera();
	}
	
	private void createTileLayer(int width, int height){
		GameObject tileLayer = new GameObject("TileLayer");
		StaticLayer layer    = new StaticLayer("TileLayer", width, height, 32, m_Shader);
		
		layer.addTexture(ColorRGBA.GREEN, new TextureRegion(m_TextureAtlas, 0, 16*7, 32, 32));
		//layer.addTexture(ColorRGBA.BROWN, new TextureRegion(m_TextureAtlas, 16*2, 16*7, 32, 32));
		
		layer.generateLayer();
		
		tileLayer.addComponent(layer);
		
		m_SceneGraph.addChild(tileLayer);
	}
	
	private void createPlayer(){
		m_Player    = new GameObject("Player", 0, 0);
		TextureRegion region = new TextureRegion(m_TextureAtlas, 16*4, 16, 32, 32);
		m_Player.addComponent(new InputComponent());
		m_Player.addComponent(new Sprite("PlayerSprite", m_Player.getTransform().getPosition(), region));
		m_SceneGraph.addChild(m_Player);
	}
	
	private void createCamera(){
		m_MainCamera = new GameObject("Camera2D");
	
		Camera2D cameraComponent = new Camera2D("Camera2D", m_Player, Window.getWidth(), Window.getHeight());
		m_MainCamera.addComponent(cameraComponent);
//		m_MainCamera.addComponent(new InputComponent());
		cameraComponent.init();
		m_SceneGraph.addChild(m_MainCamera);
	}
	
	@Override
	public void update(float dt) {
		if(timer > 7500)timer = 0;
		else timer++;
		
		m_SceneGraph.update(dt);
	}

	@Override
	public void render() {
		m_Shader.enable();
		
		Camera2D cam = (Camera2D)m_MainCamera.findComponentByTag("Camera2D");
//		m_Shader.setUniform("view_matrix", cam.getViewMatrix());
		m_Shader.setUniform("projection_matrix", cam.getProjection());
		
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
