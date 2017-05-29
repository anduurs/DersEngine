package com.dersgames.engine.graphics.renderers.postprocessing;

import com.dersgames.engine.graphics.*;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.renderers.ImageRenderer;
import com.dersgames.engine.graphics.shaders.PostProcessShader;
import com.dersgames.engine.graphics.renderers.postprocessing.PostProcessingEffect;

import java.util.List;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

/**
 * Created by Anders on 5/28/2017.
 */
public class PostProcessRenderer {

    private ImageRenderer m_ImageRenderer;
    private PostProcessShader m_Shader;
    private HorizontalBlur m_HorizontalBlur;
    private VerticalBlur m_VerticalBlur;

    private BrightFilter m_BrightFilter;
    private CombineFilter m_CombineFilter;
    private final Model m_Quad;

    public PostProcessRenderer(){
        m_ImageRenderer = new ImageRenderer();
        m_Shader = new PostProcessShader();
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        m_Quad = Loader.loadModel(positions, 2);

        m_HorizontalBlur = new HorizontalBlur(Window.getWidth() / 5, Window.getHeight() / 5);
        m_VerticalBlur = new VerticalBlur(Window.getWidth() / 5, Window.getHeight() / 5);

        m_BrightFilter = new BrightFilter(Window.getWidth() / 2, Window.getHeight() / 2);
        m_CombineFilter = new CombineFilter();
    }

    public void renderPostProcessingEffects(int colorTexture){
        begin();

       /* m_HorizontalBlur2.render(colorTexture);
        m_VerticalBlur2.render(m_HorizontalBlur2.getOutputTexture());
        m_HorizontalBlur.render(m_VerticalBlur2.getOutputTexture());
        m_VerticalBlur.render(m_HorizontalBlur.getOutputTexture());

        finalPostProcessPass(m_VerticalBlur2.getOutputTexture());*/
        m_BrightFilter.render(colorTexture);
        m_HorizontalBlur.render(m_BrightFilter.getOutputTexture());
        m_VerticalBlur.render(m_HorizontalBlur.getOutputTexture());
        m_CombineFilter.render(colorTexture, m_VerticalBlur.getOutputTexture());
        //finalPostProcessPass(m_VerticalBlur.getOutputTexture());

        end();
    }

    private void finalPostProcessPass(int colorTexture){
        m_Shader.enable();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, colorTexture);
        m_ImageRenderer.renderQuad();
        m_Shader.disable();
    }

    private void begin(){
        glBindVertexArray(m_Quad.getVaoID());
        glDisable(GL_DEPTH_TEST);
        RenderEngine.disableFaceCulling();
    }

    private void end(){
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindVertexArray(0);
        glEnable(GL_DEPTH_TEST);
        RenderEngine.enableFaceCulling();
    }

    public void dispose(){
        m_Shader.deleteShaderProgram();
        m_ImageRenderer.dispose();
        m_HorizontalBlur.dispose();
        m_VerticalBlur.dispose();
        m_BrightFilter.dispose();
        m_CombineFilter.dispose();
    }

    public PostProcessShader getShader(){
        return m_Shader;
    }
}
