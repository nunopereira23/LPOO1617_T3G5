package com.cvc.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;

public class CVCGame extends Game{
	private Box2D phys_engine;

	public SpriteBatch batch;

	@Override
	public void create () {
		phys_engine.init();

		batch = new SpriteBatch();

		setScreen(new CVCGameScreen(this));
	}

	@Override
	public void render () {
	//	Gdx.gl.glClearColor(1, 0, 0, 1);
	//	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
