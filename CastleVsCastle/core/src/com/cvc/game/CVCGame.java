package com.cvc.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

public class CVCGame extends Game {
	private Texture tex_1;
	private Sound se_1;
	private Music bgse_1;
	
	@Override
	public void create () {
		tex_1 = new Texture("badlogic.jpg");
		se_1 = Gdx.audio.newSound(Gdx.files.internal("se/droplet.ogg"));
		bgse_1 = Gdx.audio.newMusic(Gdx.files.internal("bgse/rain.ogg"));

		bgse_1.setLooping(true);
		bgse_1.play();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void dispose () {
		bgse_1.stop();

		tex_1.dispose();
		se_1.dispose();
		bgse_1.dispose();
	}
}
