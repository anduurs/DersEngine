package com.dersgames.engine.graphics.shaders.postprocessing;

import com.dersgames.engine.graphics.shaders.GLShader;

/**
 * Created by Anders on 5/29/2017.
 */
public class VerticalBlurShader extends GLShader{

    public VerticalBlurShader() {
        super("postprocessing/verticalBlurVertexShader", "postprocessing/blurFragmentShader");
        addUniform("targetHeight");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    public void loadTargetHeight(float targetHeight){
        super.loadFloat("targetHeight", targetHeight);
    }
}
