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

    private boolean m_FloatingPoint;

    public FrameBuffer(int width, int height, DepthBufferType depthBufferType, boolean multiSample, boolean floatingPoint){
        m_Width = width;
        m_Height = height;
        m_DepthBufferType = depthBufferType;
        m_FloatingPoint = floatingPoint;

        createFrameBuffer();

        if(multiSample){
            attachMultiSampledColorBuffer();
        }else{
            attachColorTexture();
        }

        switch(depthBufferType){
            case NONE:
                break;
            case DEPTH_TEXTURE:
                attachDepthTexture();
                break;
            case DEPTH_RENDER_BUFFER:
                if(multiSample){
                    attachMultiSampledDepthBuffer();
                }else{
                    attachDepthBuffer();
                }
                break;
        }

        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE){
            Debug.log("ERROR: Framebuffer is not complete!");
        }else{
            Debug.log("Framebuffer created successfully!");
        }

        unbind();
    }

    public FrameBuffer(int width, int height, DepthBufferType depthBufferType){
        this(width, height, depthBufferType, false, false);
    }

    public FrameBuffer(int width, int height){
        this(width, height, DepthBufferType.DEPTH_RENDER_BUFFER, true, false);
    }

    private void createFrameBuffer() {
        m_FrameBuffer = glGenFramebuffers();

        glBindFramebuffer(GL_FRAMEBUFFER, m_FrameBuffer);
        glDrawBuffer(GL_COLOR_ATTACHMENT0);
    }

    private void attachColorTexture() {
        m_ColorTexture = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, m_ColorTexture);

        glTexImage2D(GL_TEXTURE_2D, 0, m_FloatingPoint ? GL_RGBA16F : GL_RGBA8, m_Width, m_Height, 0, GL_RGBA,
                m_FloatingPoint ? GL_FLOAT : GL_UNSIGNED_BYTE, (ByteBuffer) null);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, m_ColorTexture, 0);
    }

    private void attachColorBuffer(){
        m_ColorBuffer = glGenRenderbuffers();

        glBindRenderbuffer(GL_RENDERBUFFER, m_ColorBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_RGBA8, m_Width, m_Height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_RENDERBUFFER, m_ColorBuffer);
    }

    private void attachMultiSampledColorBuffer(){
        m_ColorBuffer = glGenRenderbuffers();

        glBindRenderbuffer(GL_RENDERBUFFER, m_ColorBuffer);
        glRenderbufferStorageMultisample(GL_RENDERBUFFER, 4, m_FloatingPoint ? GL_RGBA16F : GL_RGBA8, m_Width, m_Height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_RENDERBUFFER, m_ColorBuffer);
    }

    private void attachDepthTexture() {
        m_DepthTexture = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, m_DepthTexture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT24, m_Width, m_Height, 0, GL_DEPTH_COMPONENT,
                GL_FLOAT, (ByteBuffer) null);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, m_DepthTexture, 0);
    }

    private void attachDepthBuffer() {
        m_DepthBuffer = glGenRenderbuffers();

        glBindRenderbuffer(GL_RENDERBUFFER, m_DepthBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT24, m_Width, m_Height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, m_DepthBuffer);
    }

    private void attachMultiSampledDepthBuffer() {
        m_DepthBuffer = glGenRenderbuffers();

        glBindRenderbuffer(GL_RENDERBUFFER, m_DepthBuffer);
        glRenderbufferStorageMultisample(GL_RENDERBUFFER, 4, GL_DEPTH_COMPONENT24, m_Width, m_Height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, m_DepthBuffer);
    }

    public void bind(){
        glBindFramebuffer(GL_FRAMEBUFFER, m_FrameBuffer);
        glViewport(0, 0, m_Width, m_Height);
    }

    public void unbind(){
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glViewport(0, 0, Window.getWidth(), Window.getHeight());
    }

    public void blitToFrameBuffer(FrameBuffer outputFrameBuffer){
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, outputFrameBuffer.m_FrameBuffer);
        glBindFramebuffer(GL_READ_FRAMEBUFFER, m_FrameBuffer);
        glBlitFramebuffer(0,0, m_Width, m_Height, 0, 0,
                outputFrameBuffer.m_Width, outputFrameBuffer.m_Height ,
                GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT, GL_NEAREST);
        unbind();
    }

    public void blitToScreen(){
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
        glBindFramebuffer(GL_READ_FRAMEBUFFER, m_FrameBuffer);
        glDrawBuffer(GL_BACK);
        glBlitFramebuffer(0,0, m_Width, m_Height, 0, 0,
                Window.getHeight(), Window.getHeight(),
                GL_COLOR_BUFFER_BIT, GL_NEAREST);
        unbind();
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
    public int getColorBuffer(){ return m_ColorBuffer; }
    public int getDepthBuffer(){ return m_DepthBuffer; }
}
