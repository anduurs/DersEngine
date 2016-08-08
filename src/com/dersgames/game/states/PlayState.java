package com.dersgames.game.states;

import com.dersgames.game.components.MovementComponent;
import com.dersgames.game.components.StaticMesh;
import com.dersgames.game.components.lights.Light;
import com.dersgames.game.core.Camera;
import com.dersgames.game.core.GameState;
import com.dersgames.game.core.GameStateManager;
import com.dersgames.game.core.Matrix4f;
import com.dersgames.game.core.Vector3f;
import com.dersgames.game.entities.Entity;
import com.dersgames.game.entities.Scene;
import com.dersgames.game.graphics.ModelLoader;
import com.dersgames.game.graphics.Renderer3D;
import com.dersgames.game.graphics.Window;
import com.dersgames.game.graphics.models.TexturedModel;
import com.dersgames.game.graphics.shaders.StaticShader;
import com.dersgames.game.graphics.shaders.Shader;
import com.dersgames.game.graphics.textures.ModelTexture;

public class PlayState extends GameState{
	
	Renderer3D renderer;
	StaticShader shader;
	ModelLoader loader;
	Scene scene;
	Camera camera;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
		
	}

	@Override
	public void init() {
		shader = new StaticShader();
		Matrix4f projection = new Matrix4f().setPerspectiveProjection(70.0f, (float)Window.getWidth() / (float)Window.getHeight(), 0.01f, 1000.0f);
		camera = new Camera();
		renderer = new Renderer3D(shader, projection);
		loader = new ModelLoader();
		scene = new Scene();
		
		TexturedModel texturedModel = new TexturedModel(loader.loadObjModel("dragon"), 
				new ModelTexture(loader.loadTexture("dragon")));
	
		Entity entity = new Entity("Entity1", 0, 0, 20);
		texturedModel.getModelTexture().setShineDamper(10.0f);
		texturedModel.getModelTexture().setReflectivity(1.0f);
		entity.addComponent(new StaticMesh("StaticMesh1", texturedModel));
		entity.addComponent(new MovementComponent("MovementTest"));
		
		Entity lightSource = new Entity("LightSource", 200, 200, 100);
		Light light = new Light("Sun", new Vector3f(1.0f, 1.0f, 1.0f));
		lightSource.addComponent(light);
		
		scene.addCameraToScene(camera);
		scene.addSunToScene(light);
		scene.addEntity(entity);
		scene.addEntity(lightSource);
	}
	
	@Override
	public void update(float dt) {
		scene.update(dt);
	}

	@Override
	public void render() {
		scene.render(renderer);
	}

	@Override
	public void cleanUp() {
		loader.cleanUp();
		renderer.cleanUp();
	}

}
