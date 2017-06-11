package com.cvc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cvc.logic.CVCUtils;

public class CVCMenu extends Stage {
	private Stage stage;

	private Skin skin;

	public CVCMenu(ScreenViewport viewport, float x, float y) {
		super(viewport);
		stage = this;

		skin = new Skin(Gdx.files.internal("uiskin.json"));


		final TextField field = new TextField(" WEAPON OPTIONS", skin);
		TextField.TextFieldStyle style = field.getStyle();
		style.disabledFontColor = Color.RED;
		field.setStyle(style);
		field.setPosition(x, y);
		field.setWidth(150);
		field.setHeight(30);
		field.setDisabled(true);

		final TextButton button = new TextButton("Load Weapon", skin, "default");
		button.setPosition(x, y - 30); // y0 on top
		button.setWidth(150);
		button.setHeight(30);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			/*	Timer.schedule(new Timer.Task() {
					@Override
					public void run() {

					}
				}, 3);*/
			}
		});

		this.addActor(field);
		this.addActor(button);
	}
}
