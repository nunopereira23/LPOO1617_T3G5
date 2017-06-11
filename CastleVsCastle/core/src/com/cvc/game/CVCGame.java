package com.cvc.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cvc.logic.CVCWorld;

public class CVCGame extends Game {
	public static CVCWorld world;
	public static CVCMenu hud;
	public static CVCMenu menu = null;

	public static CVCGameScreen game_screen;

	public static InputMultiplexer input_multi;

	@Override
	/** Create the game
	 *
	 */
	public void create () {
		Box2D.init();
		World.setVelocityThreshold(Float.MAX_VALUE);

		world = new CVCWorld();
		hud = new CVCMenu(new ScreenViewport());

		Gdx.gl.glClearColor(0.7f, 1.0f, 1.0f, 1);

		game_screen = new CVCGameScreen();
		input_multi = new InputMultiplexer();
		input_multi.addProcessor(0, hud);
		input_multi.addProcessor(1, game_screen);

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
		world.dispose();
	}

	public static void openMenu(CVCMenu.MenuType menuType, Object object) {
		menu = new CVCMenu(new ScreenViewport(), Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), menuType, object);
		pushProcessor(menu);
	}

	public static void closeMenu() {
		menu.delete();
		menu = null;
		popProcessor();
	}

	public static void pushProcessor(InputProcessor processor) {
		Array<InputProcessor> processorsArray = input_multi.getProcessors();
		processorsArray.insert(1, processor);
		input_multi.setProcessors(processorsArray);
		Gdx.input.setInputProcessor(input_multi);
	}

	public static void popProcessor() {
		Array<InputProcessor> processorsArray = input_multi.getProcessors();
		processorsArray.removeIndex(1);
		input_multi.setProcessors(processorsArray);
		Gdx.input.setInputProcessor(input_multi);
	}
}
