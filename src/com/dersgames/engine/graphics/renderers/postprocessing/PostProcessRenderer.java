package com.dersgames.engine.graphics.renderers.postprocessing;

import com.dersgames.engine.graphics.*;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.renderers.ImageRenderer;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Anders on 5/28/2017.
 */
public class PostProcessRenderer {

    private ImageRenderer m_ImageRenderer;

    private GaussianBlur m_HorizontalBlur;
    private GaussianBlur m_VerticalBlur;

    private BrightFilter m_BrightFilter;
    private CombineFilter m_CombineFilter;

    private final Model m_Quad;

    public PostProcessRenderer(){
        m_ImageRenderer = new ImageRenderer();
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        m_Quad = Loader.loadModel(positions, 2);

        m_HorizontalBlur = new GaussianBlur(Window.getWidth() / 2,
                Window.getHeight() / 2, GaussianBlur.BlurType.HORIZONTAL);
        m_VerticalBlur = new GaussianBlur(Window.getWidth() / 2,
                Window.getHeight() / 2, GaussianBlur.BlurType.VERTICAL);

        m_BrightFilter = new BrightFilter(Window.getWidth() / 2, Window.getHeight() / 2);
        m_CombineFilter = new CombineFilter();
    }

    private void begin(){
        glBindVertexArray(m_Quad.getVaoID());
        glDisable(GL_DEPTH_TEST);
        RenderEngine.disableFaceCulling();
    }

    public void renderPostProcessingEffects(int colorTexture, int brightColor){
        begin();

        //m_BrightFilter.render(colorTexture);
        //m_HorizontalBlur.render(m_BrightFilter.getOutputColorTexture());
        m_HorizontalBlur.render(brightColor);
        m_VerticalBlur.render(m_HorizontalBlur.getOutputColorTexture());
        m_CombineFilter.render(colorTexture, m_VerticalBlur.getOutputColorTexture());

        end();
    }

    private void end(){
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindVertexArray(0);
        glEnable(GL_DEPTH_TEST);
        RenderEngine.enableFaceCulling();
    }

    public void dispose(){
        m_ImageRenderer.dispose();
        m_HorizontalBlur.dispose();
        m_VerticalBlur.dispose();
        m_BrightFilter.dispose();
        m_CombineFilter.dispose();
    }
}
