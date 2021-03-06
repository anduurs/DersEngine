package com.dersgames.engine.core;

public class Debug {
	
	public enum LogLevel {
		INFO, 
		WARNING,
		ERROR,
		DEBUG
	}
	
	public static void log(String message){
		System.out.println(message);
	}
	
	public static void log(int message){
		System.out.println(message);
	}
	
	public static void log(float message){
		System.out.println(message);
	}

}
