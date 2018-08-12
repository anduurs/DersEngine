package com.dersgames.engine.graphics.renderers;

import com.dersgames.engine.graphics.shaders.Shader;

/**
 * Created by Anders on 5/28/2017.
 */
public interface Renderer3D {
    public void begin();
    public void render();
    public void end(boolean lastRenderPass);
    public void dispose();
    public Shader getShader();
}
