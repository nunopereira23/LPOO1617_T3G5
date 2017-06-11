package com.cvc.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class CVCUtils {
	public static final float DEGUNIT = 180.0f / (float) Math.PI;

	public static final Color DARK_GRAY = new Color(0.38f, 0.38f, 0.38f, 1);
	public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f, 1);
	public static final Color LIGHT_GRAY = new Color(0.63f, 0.63f, 0.63f, 1);

	public static final Color DARK_GRAY_CLEAR = new Color(0.38f, 0.38f, 0.38f, 0.5f);
	public static final Color GRAY_CLEAR = new Color(0.5f, 0.5f, 0.5f, 0.5f);
	public static final Color LIGHT_GRAY_CLEAR = new Color(0.63f, 0.63f, 0.63f, 0.5f);

	public static final Color DARK_ORANGE = new Color(0.6f, 0.3f, 0.0f, 1);
	public static final Color ORANGE = new Color(0.8f, 0.4f, 0.0f, 1);
	public static final Color LIGHT_ORANGE = new Color(1.0f, 0.5f, 0.0f, 1);

	/** Converter to pixels
	 *
	 * @param meters
	 * @return float
	 */
	public static int toPixels(float meters) {
		return (int) (meters * (Gdx.graphics.getHeight() / 20.0f));
	}

	public static int toMeters(int pixels) {
		return (int) (pixels * (20.0f / Gdx.graphics.getHeight()));
	}

	public static void debugOut(String message) {
		Gdx.app.log("DebugOut ", message);
	}
}
