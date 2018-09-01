package com.dersgames.engine.graphics.shaders.postprocessing;

import com.dersgames.engine.graphics.shaders.GLShader;

/**
 * Created by Anders on 5/29/2017.
 */
public class HorizontalBlurShader extends GLShader {

    public HorizontalBlurShader() {
        super("postprocessing/horizontalBlurVertexShader", "postprocessing/blurFragmentShader");
        addUniform("targetWidth");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    public void loadTargetWidth(float targetWidth){
        super.loadFloat("targetWidth", targetWidth);
    }
}
