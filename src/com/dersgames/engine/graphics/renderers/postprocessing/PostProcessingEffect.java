package com.dersgames.engine.graphics.renderers.postprocessing;

import com.dersgames.engine.graphics.renderers.ImageRenderer;

/**
 * Created by Anders on 5/29/2017.
 */
public abstract class PostProcessingEffect {

    protected ImageRenderer m_ImageRenderer;

    public PostProcessingEffect(int frameBufferWidth, int frameBufferHeight){
        m_ImageRenderer = new ImageRenderer(frameBufferWidth, frameBufferHeight);
    }

    public PostProcessingEffect(){}

    public abstract void render(int texture);
    public abstract void dispose();

    public int getOutputTexture(){ return m_ImageRenderer.getOutputTexture(); }
}
