<<<<<<< HEAD
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
=======
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
>>>>>>> e3b0c0e11509b37a004d7aefb8b639f645e3ebfd
