package com.dersgames.engine.graphics;

import com.dersgames.engine.core.Debug;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL30.*;


/**
 * Created by Anders on 5/28/2017.
 */
public class FrameBuffer {

    public enum DepthBufferType{
        NONE, DEPTH_TEXTURE, DEPTH_RENDER_BUFFER;
    }

    private DepthBufferType m_DepthBufferType;

    private final int m_Width;
    private final int m_Height;

    private int m_FrameBuffer;

    private int m_ColorTexture;
    private int m_DepthTexture;

    private int m_DepthBuffer;
    private int m_ColorBuffer;

    public FrameBuffer(int width, int height, DepthBufferType depthBufferType){
        this(width, height, depthBufferType, false);
    }

    public FrameBuffer(int width, int height, DepthBufferType depthBufferType, boolean hdr){
        m_Width = width;
        m_Height = height;
        m_DepthBufferType = depthBufferType;

        createFrameBuffer();
        createTextureAttachment(hdr);

        switch(depthBufferType){
            case NONE:
                break;
            case DEPTH_TEXTURE:
                createDepthTextureAttachment();
                break;
            case DEPTH_RENDER_BUFFER:
                createDepthBufferAttachment();
                break;
        }

        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE){
            Debug.log("ERROR: Framebuffer is not complete!");
        }else{
            Debug.log("Framebuffer created successfully!");
        }

        unbind();
    }

    public void bind(){
        glBindFramebuffer(GL_FRAMEBUFFER, m_FrameBuffer);
        glViewport(0, 0, m_Width, m_Height);
    }

    public void unbind(){
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glViewport(0, 0, m_Width, m_Height);
    }

    public void dispose(){
        glDeleteFramebuffers(m_FrameBuffer);
        glDeleteTextures(m_ColorTexture);
        glDeleteTextures(m_DepthTexture);
        glDeleteRenderbuffers(m_DepthBuffer);
        glDeleteRenderbuffers(m_ColorBuffer);
    }

    public int getColorTexture(){
        return m_ColorTexture;
    }
    public int getDepthTexture(){
        return m_DepthTexture;
    }

    private void createFrameBuffer() {
        m_FrameBuffer = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, m_FrameBuffer);
        glDrawBuffer(GL_COLOR_ATTACHMENT0);
    }

    private void createTextureAttachment(boolean hdr) {
        m_ColorTexture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, m_ColorTexture);

        if(hdr){
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA16F, m_Width, m_Height, 0, GL_RGBA, GL_UNSIGNED_BYTE,
                    (ByteBuffer) null);
        }else{
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, m_Width, m_Height, 0, GL_RGBA, GL_UNSIGNED_BYTE,
                    (ByteBuffer) null);
        }

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, m_ColorTexture,
                0);
    }

    private void createDepthTextureAttachment() {
        m_DepthTexture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, m_DepthTexture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT24, m_Width, m_Height, 0, GL_DEPTH_COMPONENT,
                GL_FLOAT, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, m_DepthTexture, 0);
    }

    private void createDepthBufferAttachment() {
        m_DepthBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, m_DepthBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT24, m_Width, m_Height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER,
                m_DepthBuffer);
    }
}
