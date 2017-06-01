package com.cvc.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Box2D;

public class CVCGame extends Game{
	@Override
	public void create () {
		Box2D.init();
		setScreen(new CVCGameScreen());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}
}
