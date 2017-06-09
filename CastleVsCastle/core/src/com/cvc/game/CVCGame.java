package com.cvc.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Box2D;

public class CVCGame extends Game{
	@Override
	/** Create the game
	 *
	 */
	public void create () {
		Box2D.init();
		setScreen(new CVCGameScreen());
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
}