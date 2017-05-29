package com.dersgames.engine.graphics.shaders.postprocessing;

import com.dersgames.engine.graphics.shaders.Shader;

/**
 * Created by Anders on 5/29/2017.
 */
public class CombineFilterShader extends Shader {

    public CombineFilterShader() {
        super("postprocessing/postProcessVertexShader", "postprocessing/combineFilterFragmentShader");
        addUniform("colorTexture");
        addUniform("highlightTexture");
    }

    public void connectTextureUnits(){
        super.loadInteger("colorTexture", 0);
        super.loadInteger("highlightTexture", 1);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}
