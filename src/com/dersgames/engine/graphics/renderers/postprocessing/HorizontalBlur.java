package com.dersgames.engine.graphics.renderers.postprocessing;

import com.dersgames.engine.graphics.shaders.postprocessing.HorizontalBlurShader;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

/**
 * Created by Anders on 5/29/2017.
 */
public class HorizontalBlur extends PostProcessingEffect{

    private HorizontalBlurShader m_Shader;

    public HorizontalBlur(int frameBufferWidth, int frameBufferHeight){
        super(frameBufferWidth, frameBufferHeight);

        m_Shader = new HorizontalBlurShader();
        m_Shader.enable();
        m_Shader.loadTargetWidth(frameBufferWidth);
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
