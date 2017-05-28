package com.dersgames.engine.graphics.renderers;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.graphics.shaders.Shader;
import com.dersgames.engine.maths.Vector3f;

/**
 * Created by Anders on 5/28/2017.
 */
public interface Renderer3D {
    public void begin(Camera camera);
    public void render();
    public void end();
    public void dispose();
    public Shader getShader();
}
