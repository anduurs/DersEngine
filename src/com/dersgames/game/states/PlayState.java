package com.dersgames.game.states;

import com.dersgames.game.components.MovementComponent;
import com.dersgames.game.components.StaticMesh;
import com.dersgames.game.core.Camera;
import com.dersgames.game.core.Debug;
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
	Shader shader;
	ModelLoader loader;
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
		
		TexturedModel texturedModel = new TexturedModel(loader.loadObjModel("stall"), 
				new ModelTexture(loader.loadTexture("stall")));
		
		for(int i = 0; i < 50; i++){
			Entity entity = new Entity("Entity1", 0, -5 + i *10, 20);
			entity.addComponent(new StaticMesh("StaticMesh1", texturedModel));
			entity.addComponent(new MovementComponent("MovementTest"));
			entityManager.addEntity(entity);
		}
		
	}
	
	int timer = 0;
	
	@Override
	public void update(float dt) {
		camera.update(dt);
		entityManager.update(dt);
		
		timer ++;
		
		if(timer >= 60){
			Debug.log("Number of entities: " + entityManager.getEntityCount());
			timer = 0;
		}
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
