package com.dersgames.engine.graphics.renderers.postprocessing;

import com.dersgames.engine.graphics.shaders.postprocessing.BrightFilterShader;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

/**
 * Created by Anders on 5/29/2017.
 */
public class BrightFilter extends PostProcessingEffect{

    private BrightFilterShader m_Shader;

    public BrightFilter(int targetWidth, int targetHeight){
        super(targetWidth, targetHeight);
        m_Shader = new BrightFilterShader();
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
