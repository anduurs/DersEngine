package com.dersgames.engine.graphics.renderers;

/**
 * Created by Anders on 5/28/2017.
 */
import com.dersgames.engine.graphics.FrameBuffer;
import com.dersgames.engine.graphics.GLRenderUtils;

public class ImageRenderer{

    private FrameBuffer m_FrameBuffer;

    public ImageRenderer(int width, int height) {
        m_FrameBuffer = new FrameBuffer(width, height, FrameBuffer.DepthBufferType.NONE);
    }

    public ImageRenderer() {}

    public void renderQuad() {
        if (m_FrameBuffer != null) {
            m_FrameBuffer.bind();
        }

        GLRenderUtils.clearColorBuffer();
        GLRenderUtils.drawArrays(GLRenderUtils.TRIANGLE_STRIP, 4);

        if (m_FrameBuffer != null) {
            m_FrameBuffer.unbind();
        }
    }

    public int getOutputColorTexture() {
        return m_FrameBuffer.getColorTexture();
    }

    public void dispose() {
        if (m_FrameBuffer != null) {
            m_FrameBuffer.dispose();
        }
    }

}
