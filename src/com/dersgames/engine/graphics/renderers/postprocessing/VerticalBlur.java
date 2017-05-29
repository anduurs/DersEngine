package com.dersgames.engine.graphics.renderers.postprocessing;

import com.dersgames.engine.graphics.shaders.postprocessing.VerticalBlurShader;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 * Created by Anders on 5/29/2017.
 */
public class VerticalBlur extends PostProcessingEffect{

    private VerticalBlurShader m_Shader;

    public VerticalBlur(int frameBufferWidth, int frameBufferHeight){
        super(frameBufferWidth, frameBufferHeight);

        m_Shader = new VerticalBlurShader();
        m_Shader.enable();
        m_Shader.loadTargetHeight(frameBufferHeight);
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
        m_ImageRenderer.dispose();
        m_Shader.deleteShaderProgram();
    }
}
