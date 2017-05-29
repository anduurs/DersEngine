package com.dersgames.engine.graphics.renderers;

/**
 * Created by Anders on 5/28/2017.
 */
import com.dersgames.engine.graphics.FrameBuffer;
import static org.lwjgl.opengl.GL11.*;

public class ImageRenderer {

    private FrameBuffer m_FrameBuffer;

    public ImageRenderer(int width, int height) {
        m_FrameBuffer = new FrameBuffer(width, height, FrameBuffer.DepthBufferType.NONE, false);
    }

    public ImageRenderer() {}

    public void renderQuad() {
        if (m_FrameBuffer != null) {
            m_FrameBuffer.bind();
        }

        glClear(GL_COLOR_BUFFER_BIT);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

        if (m_FrameBuffer != null) {
            m_FrameBuffer.unbind();
        }
    }

    public int getOutputTexture() {
        return m_FrameBuffer.getColorTexture();
    }

    public void dispose() {
        if (m_FrameBuffer != null) {
            m_FrameBuffer.dispose();
        }
    }

}
