package com.dersgames.engine.graphics.renderers;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.graphics.shaders.Shader;
import com.dersgames.engine.maths.Vector3f;
import com.dersgames.engine.maths.Vector4f;

/**
 * Created by Anders on 5/28/2017.
 */
public interface Renderer3D {
    public void begin(Camera camera, Vector4f clippingPlane);
    public void render();
    public void end(boolean lastRenderPass);
    public void dispose();
    public Shader getShader();
}
