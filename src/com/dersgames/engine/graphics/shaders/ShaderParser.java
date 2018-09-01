package com.dersgames.engine.graphics.shaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShaderParser {
	
	public static String loadShader(String fileName){
		StringBuilder shaderSource = new StringBuilder();
		
		try {
			InputStream in = Class.class.getResourceAsStream("/shaders/" + fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = reader.readLine()) != null)
				shaderSource.append(line).append('\n');
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return shaderSource.toString();
	}

}
