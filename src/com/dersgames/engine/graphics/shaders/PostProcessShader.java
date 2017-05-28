package com.dersgames.engine.graphics.shaders;

/**
 * Created by Anders on 5/28/2017.
 */
public class PostProcessShader extends Shader{

    public PostProcessShader(){
        super("postprocessing/postProcessVertexShader", "postprocessing/postProcessFragmentShader");
        addUniform("textureSampler");

        enable();
        loadInteger("textureSampler", 0);
        disable();
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}
