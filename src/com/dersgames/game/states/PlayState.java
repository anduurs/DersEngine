package com.dersgames.game.states;

import java.util.ArrayList;
import java.util.List;

import com.dersgames.game.components.BasicMovement;
import com.dersgames.game.components.Sprite;
import com.dersgames.game.components.Transform;
import com.dersgames.game.core.GameObject;
import com.dersgames.game.core.GameState;
import com.dersgames.game.core.GameStateManager;
import com.dersgames.game.core.SceneGraph;
import com.dersgames.game.graphics.BatchRenderer;
import com.dersgames.game.graphics.Shader;
import com.dersgames.game.graphics.Texture;
import com.dersgames.game.graphics.TextureRegion;
import com.dersgames.game.graphics.Window;
import com.dersgames.game.utils.Randomizer;

public class PlayState extends GameState{
	
	private SceneGraph m_SceneGraph;
	private BatchRenderer m_Batch;
	private Texture m_TextureAtlas;
	private Shader m_Shader;
	
	private List<GameObject> gameObjects;
	
	int timer = 0;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	@Override
	public void init() {
		m_Shader = new Shader("basicVert", "basicFrag");
		
		m_Shader.addUniform("projection_matrix");
		m_Shader.addUniform("sampler");
		
		m_Shader.enable();
		m_Shader.setUniformi("sampler", 0);
		m_Shader.disable();
		
		m_TextureAtlas = new Texture("atlas");
		m_Batch = new BatchRenderer();
		m_SceneGraph = new SceneGraph();
		
		TextureRegion region = new TextureRegion(m_TextureAtlas, 0, 16*5, 32, 32);
		
		gameObjects = new ArrayList<GameObject>();
	
		for(int i = 0; i < 100; i++){
			float y = Randomizer.getFloat(-16, Window.getHeight()/2 - 16);
			GameObject go = new GameObject("Player", 0, y);
			float speed = Randomizer.getFloat(1f, 3.0f);
			go.addComponent(new BasicMovement(2));
			go.addComponent(new Sprite("PlayerSprite", go.getTransform().getPosition(), region));
			gameObjects.add(go);
		}
		
	}

	@Override
	public void update(float dt) {
		if(timer > 7500)
			timer = 0;
		else timer++;
		
		if(timer % 30 == 0){
			if(gameObjects.size() != 0){
				int index = Randomizer.getInt(0, gameObjects.size());
				if(gameObjects.get(index) != null){
					m_SceneGraph.addChild(gameObjects.get(index));
					gameObjects.remove(index);
				}
			}
		}
		
		m_SceneGraph.update(dt);
	}

	@Override
	public void render() {
		m_Shader.enable();
		m_Shader.setUniform("projection_matrix", Transform.getOrthoProjection());
		m_TextureAtlas.bind();
		
		m_Batch.begin();
		m_SceneGraph.render(m_Batch);
		m_Batch.end();
		m_Batch.flush();
		
		m_TextureAtlas.unbind();
		m_Shader.disable();
	}

}
