package com.dersgames.game.utils;

import java.util.Random;

public class Randomizer {
	
	private static Random rnd = new Random();

	public static int getBinary() {
		return rnd.nextInt(2);
	}

	public static float getFloat(float min, float max) {
		return rnd.nextFloat() * (max - min) + min;
	}

	public static int getInt(int min, int max) {
		return rnd.nextInt((max) - min) + min;
	}

	public static int getInt2(int min, int max) {
		return rnd.nextInt((max + 1) - min) + min;
	}

}
