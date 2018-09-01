package com.dersgames.engine.graphics.shaders.postprocessing;

import com.dersgames.engine.graphics.shaders.GLShader;

/**
 * Created by Anders on 5/29/2017.
 */
public class BrightFilterShader extends GLShader {

    public BrightFilterShader() {
        super("postprocessing/postProcessVertexShader", "postprocessing/brightFilterFragmentShader");

    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}
