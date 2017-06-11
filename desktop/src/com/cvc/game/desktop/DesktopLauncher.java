package com.cvc.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cvc.game.CVCGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "CastleWar";
		config.width = 640;
		config.height = 360;
		new LwjglApplication(new CVCGame(), config);
	}
}
