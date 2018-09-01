package com.dersgames.engine.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.opengl.GL30.GL_CLIP_DISTANCE0;

import org.lwjgl.opengl.GL;

import com.dersgames.engine.math.Vector3f;

/**
 * Wrapper class for all opengl specific method calls.
 * @author Anders
 *
 */
public class GLRenderUtils {
	
	public static int OPENGL_VERSION = GL_VERSION;
	
	public static int FALSE = GL_FALSE;
	public static int TRUE  = GL_TRUE;
	
	public static int COLOR_BUFFER_BITMASK   = GL_COLOR_BUFFER_BIT;
	public static int DEPTH_BUFFER_BITMASK   = GL_DEPTH_BUFFER_BIT;
	public static int STENCIL_BUFFER_BITMASK = GL_STENCIL_BUFFER_BIT;
	
	public static int TRIANGLE_STRIP = GL_TRIANGLE_STRIP;
	
	public static int DEPTH_TEST = GL_DEPTH_TEST;
	
	public static int TEXTURE_2D = GL_TEXTURE_2D;
	
	public static int MULTI_SAMPLE = GL_MULTISAMPLE;
	
	public static int CULL_FACE = GL_CULL_FACE;
	public static int FRONT = GL_FRONT;
	
	public static int CLIP_DISTANCE0 = GL_CLIP_DISTANCE0;
	
	public static void createCapabilities() {
		GL.createCapabilities();
	}
	
	public static String getOpenGLVersion() {
		return glGetString(OPENGL_VERSION);
	}
	
	public static void enableClipDistance() {
		glEnable(CLIP_DISTANCE0);
	}
	
	public static void disableClipDistance() {
		glDisable(CLIP_DISTANCE0);
	}
	
	public static void enableTexture2D() {
		glEnable(TEXTURE_2D);
	}
	
	public static void enableDepthTest() {
		glEnable(DEPTH_TEST);
	}
	
	public static void disableDepthTest() {
		glDisable(DEPTH_TEST);
	}
	
	public static void enableMultiSampling() {
		glEnable(MULTI_SAMPLE);
	}
	
	public static void enableFaceCulling() {
		enableFaceCulling(FRONT);
	}
	
	public static void enableFaceCulling(int mode) {
		glEnable(CULL_FACE);
		glCullFace(mode);
	}
	
	public static void disableFaceCulling() {
		glDisable(CULL_FACE);
	}
	
	public static void clearColorAndDepthBuffer() {
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClear(COLOR_BUFFER_BITMASK | DEPTH_BUFFER_BITMASK);
	}
	
	public static void clearFrameBuffer() {
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClear(COLOR_BUFFER_BITMASK | DEPTH_BUFFER_BITMASK | STENCIL_BUFFER_BITMASK);
	}
	
	public static void clearFrameBuffer(Vector3f clearColor, float alpha) {
		glClearColor(clearColor.x, clearColor.y, clearColor.z, alpha);
		glClear(COLOR_BUFFER_BITMASK | DEPTH_BUFFER_BITMASK | STENCIL_BUFFER_BITMASK);
	}
	
	public static void clearColorBuffer() {
		glClear(COLOR_BUFFER_BITMASK);
	}
	
	public static void clearDepthBuffer() {
		glClear(DEPTH_BUFFER_BITMASK);
	}
	
	public static void clearStencilBuffer() {
		glClear(STENCIL_BUFFER_BITMASK);
	}
	
	public static void drawElements(int count) {
		drawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
	}
	
	public static void drawElements(int mode, int count) {
		drawElements(mode, count, GL_UNSIGNED_INT, 0);
	}
	
	public static void drawElements(int mode, int count, int type) {
		drawElements(mode, count, type, 0);
	}
	
	public static void drawElements(int mode, int count, int type, long indices) {
		glDrawElements(mode, count, type, indices);
		RenderingStats.drawCallCounter++;
	}
	
	public static void drawArrays(int count) {
		drawArrays(GL_TRIANGLES, 0, count);
	}
	
	public static void drawArrays(int mode, int count) {
		drawArrays(mode, 0, count);
	}

	public static void drawArrays(int mode, int first, int count) {
		glDrawArrays(mode, first, count);
		RenderingStats.drawCallCounter++;
	}
}
