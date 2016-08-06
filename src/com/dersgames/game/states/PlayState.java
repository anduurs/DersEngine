package com.dersgames.game.states;

import com.dersgames.game.components.MovementComponent;
import com.dersgames.game.components.StaticMesh;
import com.dersgames.game.core.Camera;
import com.dersgames.game.core.GameState;
import com.dersgames.game.core.GameStateManager;
import com.dersgames.game.core.Matrix4f;
import com.dersgames.game.entities.Entity;
import com.dersgames.game.entities.EntityManager;
import com.dersgames.game.graphics.ModelLoader;
import com.dersgames.game.graphics.Renderer3D;
import com.dersgames.game.graphics.Window;
import com.dersgames.game.graphics.models.Model;
import com.dersgames.game.graphics.models.TexturedModel;
import com.dersgames.game.graphics.shaders.BasicShader;
import com.dersgames.game.graphics.shaders.Shader;
import com.dersgames.game.graphics.textures.ModelTexture;

public class PlayState extends GameState{
	
	Renderer3D renderer;
	Model model;
	Shader shader;
	ModelLoader loader;
	TexturedModel texturedModel;
	EntityManager entityManager;
	Camera camera;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
		
	}

	@Override
	public void init() {
		shader = new BasicShader();
		Matrix4f projection = new Matrix4f().setPerspectiveProjection(70.0f, (float)Window.getWidth() / (float)Window.getHeight(), 0.01f, 1000.0f);
		camera = new Camera();
		renderer = new Renderer3D(shader, projection, camera);
		loader = new ModelLoader();
		
		entityManager = new EntityManager();
		
		float[] vertices = {			
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,0.5f,-0.5f,		
				
				-0.5f,0.5f,0.5f,	
				-0.5f,-0.5f,0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				0.5f,0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				-0.5f,-0.5f,0.5f,	
				-0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,0.5f,
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,0.5f,0.5f,
				
				-0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f
		};
		
		float[] textureCoords = {
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0	
		};
		
		int[] indices = {
				0,1,3,	
				3,1,2,	
				4,5,7,
				7,5,6,
				8,9,11,
				11,9,10,
				12,13,15,
				15,13,14,	
				16,17,19,
				19,17,18,
				20,21,23,
				23,21,22
		};
		
		model = loader.loadToVAO(vertices, textureCoords, indices);
		texturedModel = new TexturedModel(model, 
				new ModelTexture(loader.loadTexture("test")));
		
		Entity entity = new Entity();
		entity.addComponent(new StaticMesh("StaticMesh1", texturedModel));
		entity.addComponent(new MovementComponent("MovementTest"));
		entityManager.addEntity(entity);
	}
	
	@Override
	public void update(float dt) {
		camera.update(dt);
		entityManager.update(dt);
	}

	@Override
	public void render() {
		shader.enable();
		entityManager.render(renderer);
		shader.disable();
	}

	@Override
	public void cleanUp() {
		loader.cleanUp();
		shader.deleteShader();
	}

}
