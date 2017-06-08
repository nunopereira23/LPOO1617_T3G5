package com.cvc.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class CVCUtils {
	public static final float DEGUNIT = 180.0f / (float) Math.PI;

//	public final float WHRATIO = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();

	public static final Color DARK_GRAY = new Color(0.38f, 0.38f, 0.38f, 1);
	public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f, 1);
	public static final Color LIGHT_GRAY = new Color(0.63f, 0.63f, 0.63f, 1);

	public static final Color DARK_ORANGE = new Color(0.6f, 0.3f, 0.0f, 1);
	public static final Color ORANGE = new Color(0.8f, 0.4f, 0.0f, 1);
	public static final Color LIGHT_ORANGE = new Color(1.0f, 0.5f, 0.0f, 1);

	/** Converter to pixels
	 *
	 * @param meters
	 * @return float
	 */
	public static float toPixels(float meters) {
		return meters * (Gdx.graphics.getHeight() / 20.0f);
	}

	public static void debugOut(String message) {
		Gdx.app.log("DebugOut ", message);
	}
}
