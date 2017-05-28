package com.dersgames.engine.graphics.renderers;

import com.dersgames.engine.graphics.*;
import com.dersgames.engine.graphics.Window;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.shaders.GUIShader;
import com.dersgames.engine.graphics.shaders.PostProcessShader;

import java.awt.*;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by Anders on 5/28/2017.
 */
public class PostProcessRenderer {

    private ImageRenderer m_ImageRenderer;
    private PostProcessShader m_Shader;
    private final Model m_Quad;

    public PostProcessRenderer(){
        m_ImageRenderer = new ImageRenderer();
        m_Shader = new PostProcessShader();
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        m_Quad = Loader.loadModel(positions, 2);
    }

    public void render(int colorTexture){
        glBindVertexArray(m_Quad.getVaoID());
        glDisable(GL_DEPTH_TEST);
        RenderEngine.disableFaceCulling();

        m_Shader.enable();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, colorTexture);

        m_ImageRenderer.renderQuad();
        m_Shader.disable();

        glBindTexture(GL_TEXTURE_2D, 0);
        glBindVertexArray(0);
        glEnable(GL_DEPTH_TEST);
        RenderEngine.enableFaceCulling();
    }

    public void dispose(){
        m_Shader.deleteShaderProgram();
        m_ImageRenderer.dispose();
    }

    public PostProcessShader getShader(){
        return m_Shader;
    }
}
