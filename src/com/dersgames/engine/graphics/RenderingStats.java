package com.dersgames.engine.graphics;

import com.dersgames.engine.core.Debug;
import com.dersgames.engine.core.GameApplication;

public class RenderingStats {
	 public static int drawCallCounter = 0;
	 public static int textureBindCounter = 0;
	 public static int shaderBindCounter = 0;
	 
	 public static void printStats() {
		 Debug.log("Draw calls: " + (drawCallCounter / GameApplication.fps));
//		 Debug.log("Texture binds: " + textureBindCounter);
//		 Debug.log("Shader binds: " + shaderBindCounter);
	 }
	 
	 public static void clear() {
		 drawCallCounter = 0;
		 textureBindCounter = 0;
		 shaderBindCounter = 0;
	 }
}
