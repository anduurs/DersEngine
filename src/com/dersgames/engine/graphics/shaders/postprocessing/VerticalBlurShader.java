package com.dersgames.engine.graphics.shaders.postprocessing;

import com.dersgames.engine.graphics.shaders.Shader;

/**
 * Created by Anders on 5/29/2017.
 */
public class VerticalBlurShader extends Shader{

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
