package com.cvc.logic;

import com.badlogic.gdx.Gdx;

public class CVCUtils {
	public static float toPixels(float meters) {
		return meters * (Gdx.graphics.getHeight() / 20.0f);
	}

	public static float toMeters(float pixels) {
		return pixels * (20.0f / Gdx.graphics.getHeight());
	}
}
