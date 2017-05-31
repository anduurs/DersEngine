package com.dersgames.engine.graphics.shaders.postprocessing;

import com.dersgames.engine.graphics.shaders.Shader;

/**
 * Created by Anders on 5/29/2017.
 */
public class GaussianBlurShader extends Shader {

    public GaussianBlurShader() {
        super("postprocessing/gaussianBlurVertexShader",
                "postprocessing/gaussianBlurFragmentShader");
        addUniform("targetDimension");
        addUniform("horizontalBlur");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    public void loadIsHorizontalBlur(boolean horizontalBlur){
        super.loadInteger("horizontalBlur", horizontalBlur ? 1 : 0);
    }
    public void loadTargetDimension(float targetDimension){
        super.loadFloat("targetDimension", targetDimension);
    }
}