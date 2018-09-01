package com.dersgames.engine.graphics.shaders.postprocessing;

import com.dersgames.engine.graphics.shaders.GLShader;

/**
 * Created by Anders on 5/29/2017.
 */
public class CombineFilterShader extends GLShader {

    public CombineFilterShader() {
        super("postprocessing/postProcessVertexShader", "postprocessing/combineFilterFragmentShader");

        addUniform("colorTexture");
        addUniform("highlightTexture");
        addUniform("exposure");
        addUniform("glowFactor");
        addUniform("contrast");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    public void connectTextureUnits(){
        super.loadInteger("colorTexture", 0);
        super.loadInteger("highlightTexture", 1);
    }

    public void loadExposure(float exposure){
        super.loadFloat("exposure", exposure);
    }

    public void loadContrast(float contrast){
        super.loadFloat("contrast", contrast);
    }

    public void loadGlowFactor(float glowFactor){
        super.loadFloat("glowFactor", glowFactor);
    }
}
