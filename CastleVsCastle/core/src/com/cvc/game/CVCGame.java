package com.cvc.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CVCGame extends Game {
	public static CVCGameScreen game_screen;

	public static InputMultiplexer input_multi;

	@Override
	/** Create the game
	 *
	 */
	public void create () {
		Box2D.init();
		World.setVelocityThreshold(Float.MAX_VALUE);

		game_screen = new CVCGameScreen();
		input_multi = new InputMultiplexer(game_screen);

		setScreen(game_screen);
		Gdx.input.setInputProcessor(input_multi);
	}

	@Override
	/** Render the game
	 *
	 */
	public void render () {
		super.render();
	}
	
	@Override
	/** Dispose the game
	 *
	 */
	public void dispose () {
	}

	public static void pushProcessor(InputProcessor processor) {
		Array<InputProcessor> processorsArray = input_multi.getProcessors();
		processorsArray.insert(0, processor);
		input_multi.setProcessors(processorsArray);
		Gdx.input.setInputProcessor(input_multi); // Just in case
	}

	public static void popProcessor() {
		Array<InputProcessor> processorsArray = input_multi.getProcessors();
		processorsArray.removeIndex(0);
		input_multi.setProcessors(processorsArray);
		Gdx.input.setInputProcessor(input_multi); // Just in case
	}
}
