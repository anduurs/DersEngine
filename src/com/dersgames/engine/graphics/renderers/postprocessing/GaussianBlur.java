package com.dersgames.engine.graphics.renderers.postprocessing;

import com.dersgames.engine.graphics.shaders.postprocessing.GaussianBlurShader;
import com.dersgames.engine.graphics.shaders.postprocessing.HorizontalBlurShader;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 * Created by Anders on 5/31/2017.
 */
public class GaussianBlur extends PostProcessingEffect{
    public enum BlurType{HORIZONTAL, VERTICAL;}
    private GaussianBlurShader m_Shader;

    public GaussianBlur(int frameBufferWidth, int frameBufferHeight, BlurType blurType){
        super(frameBufferWidth, frameBufferHeight);

        m_Shader = new GaussianBlurShader();
        m_Shader.enable();

        switch(blurType){
            case HORIZONTAL:
                m_Shader.loadIsHorizontalBlur(true);
                m_Shader.loadTargetDimension(frameBufferWidth);
                break;
            case VERTICAL:
                m_Shader.loadIsHorizontalBlur(false);
                m_Shader.loadTargetDimension(frameBufferHeight);
                break;
        }

        m_Shader.disable();
    }

    @Override
    public void render(int texture) {
        m_Shader.enable();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture);
        m_ImageRenderer.renderQuad();
        m_Shader.disable();
    }

    @Override
    public void dispose() {
        super.dispose();
        m_Shader.deleteShaderProgram();
    }
}
