package com.dersgames.engine.graphics.renderers;

import com.dersgames.engine.components.Renderable;
import com.dersgames.engine.graphics.shaders.GLShader;

public interface IRenderer<T extends Renderable> {
	public void submit(T renderable);
    public void begin();
    public void render();
    public void end(boolean lastRenderPass);
    public void dispose();
    public GLShader getShader();
}
