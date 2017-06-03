package com.dersgames.engine.graphics.renderers.postprocessing;

import com.dersgames.engine.graphics.renderers.ImageRenderer;
import com.dersgames.engine.graphics.shaders.postprocessing.CombineFilterShader;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

/**
 * Created by Anders on 5/29/2017.
 */
public class CombineFilter {

    private ImageRenderer m_ImageRenderer;
    private CombineFilterShader m_Shader;

    public CombineFilter(){
        m_ImageRenderer = new ImageRenderer();
        m_Shader = new CombineFilterShader();

        m_Shader.enable();

        m_Shader.connectTextureUnits();
        m_Shader.loadContrast(0.3f);
        m_Shader.loadGlowFactor(2.0f);
        m_Shader.loadExposure(1.0f);

        m_Shader.disable();
    }

    public void render(int colorTexture, int highlightTexture) {
        m_Shader.enable();

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, colorTexture);
        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, highlightTexture);

        m_ImageRenderer.renderQuad();

        m_Shader.disable();
    }

    public void dispose() {
        m_ImageRenderer.dispose();
        m_Shader.deleteShaderProgram();
    }
}
