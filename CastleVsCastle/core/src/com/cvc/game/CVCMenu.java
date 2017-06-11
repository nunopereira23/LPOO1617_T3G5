package com.cvc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cvc.logic.CVCCastle;
import com.cvc.logic.CVCDefender;
import com.cvc.logic.CVCFortification;
import com.cvc.logic.CVCTower;
import com.cvc.logic.CVCWeapon;

public class CVCMenu extends Stage { // God bless this mess
	public enum MenuType {bTower, bWall, bAll, Tower, Wall, Catapult, Trebuchet, Ballista}

	private Stage stage;
	private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

	private final CVCCastle player_castle = CVCGame.world.getPlayerCastle();

	public CVCMenu(ScreenViewport viewport) { // HUD
		super(viewport);
		stage = this;

		final TextField field_1 = new TextField("Defenders", skin);
		TextField.TextFieldStyle fieldStyle = field_1.getStyle();
		fieldStyle.disabledFontColor = Color.RED;
		field_1.setStyle(fieldStyle);
		field_1.setPosition(10, Gdx.graphics.getHeight() - 40);
		field_1.setWidth(300);
		field_1.setHeight(30);
		field_1.setAlignment(1);
		field_1.setDisabled(true);

		final TextButton button_1 = new TextButton("", skin); // Not actually a button
		final TextButton button_2 = new TextButton("Search", skin);
		final TextButton button_3 = new TextButton("Teach", skin);
		final TextButton button_4 = new TextButton("Swap work", skin);
		final TextButton button_5 = new TextButton("\\/", skin);
		final boolean[] open = new boolean[]{true};

		final SelectBox box = new SelectBox(skin);
		box.setPosition(10, Gdx.graphics.getHeight() - 70);
		box.setWidth(300);
		box.setHeight(30);

		CVCDefender[] items = CVCGame.world.getPlayerCastle().getDefenders();
		String[] itemsStrings = new String[CVCDefender.total_force_];
		for (int n = 0; n < CVCDefender.total_force_; ++n)
			itemsStrings[n] = items[n].getInfo();
		Array<String> boxItems = new Array<String>(itemsStrings);
		box.setItems(boxItems);
		box.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				CVCDefender[] items = CVCGame.world.getPlayerCastle().getDefenders();
				String[] itemsStrings = new String[CVCDefender.total_force_];
				for (int n = 0; n < CVCDefender.total_force_; ++n)
					itemsStrings[n] = items[n].getInfo();
				Array<String> boxItems = new Array<String>(itemsStrings);
				box.setItems(boxItems);
				box.showList();
				button_1.setVisible(false);
				button_2.setVisible(false);
				button_3.setVisible(false);
				button_4.setVisible(false);
				button_5.setText("\\/");
				open[0] = true;
			}
		});

		TextButton.TextButtonStyle buttonStyle = button_1.getStyle();
		buttonStyle.up = fieldStyle.background;
		buttonStyle.disabled = fieldStyle.background;
		button_1.setStyle(buttonStyle);
		button_1.setPosition(10, Gdx.graphics.getHeight() - 100);
		button_1.setWidth(300);
		button_1.setHeight(30);
		button_1.setDisabled(true);
		button_1.setVisible(false);

		button_2.setStyle(buttonStyle);
		button_2.setPosition(10, Gdx.graphics.getHeight() - 130);
		button_2.setWidth(150);
		button_2.setHeight(30);
		button_2.setVisible(false);
		button_2.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				CVCDefender[] items = CVCGame.world.getPlayerCastle().getDefenders();
				items[box.getSelectedIndex()].search();
				String[] itemsStrings = new String[CVCDefender.total_force_];
				for (int n = 0; n < CVCDefender.total_force_; ++n)
					itemsStrings[n] = items[n].getInfo();
				Array<String> boxItems = new Array<String>(itemsStrings);
				box.setItems(boxItems);
				box.hideList();
				button_5.setText("\\/");

				button_1.setVisible(false);
				button_2.setVisible(false);
				button_3.setVisible(false);
				button_4.setVisible(false);
				open[0] = true;
			}
		});

		button_3.setStyle(buttonStyle);
		button_3.setPosition(160, Gdx.graphics.getHeight() - 130);
		button_3.setWidth(150);
		button_3.setHeight(30);
		button_3.setVisible(false);
		button_3.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				CVCDefender[] items = CVCGame.world.getPlayerCastle().getDefenders();
				items[box.getSelectedIndex()].teach();
				String[] itemsStrings = new String[CVCDefender.total_force_];
				for (int n = 0; n < CVCDefender.total_force_; ++n)
					itemsStrings[n] = items[n].getInfo();
				Array<String> boxItems = new Array<String>(itemsStrings);
				box.setItems(boxItems);
				box.hideList();
				button_5.setText("\\/");

				button_1.setVisible(false);
				button_2.setVisible(false);
				button_3.setVisible(false);
				button_4.setVisible(false);
				open[0] = true;
			}
		});

		button_4.setStyle(buttonStyle);
		button_4.setPosition(10, Gdx.graphics.getHeight() - 130);
		button_4.setWidth(300);
		button_4.setHeight(30);
		button_4.setVisible(false);
		button_4.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				CVCDefender[] items = CVCGame.world.getPlayerCastle().getDefenders();
				items[box.getSelectedIndex()].swapWork();
				String[] itemsStrings = new String[CVCDefender.total_force_];
				for (int n = 0; n < CVCDefender.total_force_; ++n)
					itemsStrings[n] = items[n].getInfo();
				Array<String> boxItems = new Array<String>(itemsStrings);
				box.setItems(boxItems);
				box.hideList();
				button_5.setText("\\/");

				button_1.setVisible(false);
				button_2.setVisible(false);
				button_3.setVisible(false);
				button_4.setVisible(false);
				open[0] = true;
			}
		});

		button_5.setStyle(buttonStyle);
		button_5.setPosition(310, Gdx.graphics.getHeight() - 70);
		button_5.setWidth(30);
		button_5.setHeight(30);
		button_5.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (open[0]) {
					CVCDefender[] items = CVCGame.world.getPlayerCastle().getDefenders();
					button_1.setText(items[box.getSelectedIndex()].getExtendedInfo());
					button_5.setText("/\\");

					button_1.setVisible(true);
					if (!items[box.getSelectedIndex()].isWorking()) {
						button_2.setVisible(true);
						button_3.setVisible(true);
					} else button_4.setVisible(true);
					open[0] = false;
				}
				else {
					button_5.setText("\\/");

					button_1.setVisible(false);
					button_2.setVisible(false);
					button_3.setVisible(false);
					button_4.setVisible(false);
					open[0] = true;
				}
			}
		});

		this.addActor(field_1);
		this.addActor(button_1);
		this.addActor(button_2);
		this.addActor(button_3);
		this.addActor(button_4);
		this.addActor(button_5);
		this.addActor(box);
	}

	public CVCMenu(ScreenViewport viewport, float x, float y, final MenuType menuType, final Object object) { // Context Menu
		super(viewport);
		stage = this;

		TextField field = new TextField("", skin);
		switch (menuType)
		{
			case bTower:
			case bWall:
			case bAll:
				field = new TextField("BUILD OPTIONS", skin);
				break;
			case Tower:
			case Wall:
				field = new TextField("FORTIFICATION OPTIONS", skin);
				break;
			case Catapult:
			case Trebuchet:
			case Ballista:
				field = new TextField("WEAPON OPTIONS", skin);
				break;
		}
		TextField.TextFieldStyle fieldStyle = field.getStyle();
		fieldStyle.disabledFontColor = Color.RED;
		field.setStyle(fieldStyle);
		field.setPosition(x - 100, y - 15);
		field.setWidth(200);
		field.setHeight(30);
		field.setAlignment(1);
		field.setDisabled(true);

		final Slider slider = new Slider(6, 14, 1, false, skin);
		final int[] posX = new int[1]; // "Magical" trick to bypass Java's rules
		final int[] posXTower = new int[1];
		final int[] posXWall = new int[1];
		final int[] widthWall = new int[1];
		final int[] heightWall = new int[1];

		TextButton button_1 = new TextButton("", skin);
		TextButton button_2 = new TextButton("", skin);
		final TextButton button_3 = new TextButton("Confirm", skin);
		final TextButton button_4 = new TextButton("Cancel", skin);
		final TextButton button_5 = new TextButton("X", skin);
		button_3.setVisible(false);
		button_4.setVisible(false);
		switch (menuType)
		{
			case bTower:
			case bWall:
			case bAll:
				button_1 = new TextButton("Tower", skin);
				button_2 = new TextButton("Wall", skin);
				switch (menuType)
				{
					case bTower:
						button_2.setDisabled(true);
						posXTower[0] = ((int[]) object)[0];
						break;
					case bWall:
						button_1.setDisabled(true);
						posXWall[0] = ((int[]) object)[0];
						widthWall[0] = ((int[]) object)[1];
						heightWall[0] = ((int[]) object)[2];
						break;
					case bAll:
						posXTower[0] = ((int[]) object)[0];
						posXWall[0] = ((int[]) object)[1];
						widthWall[0] = ((int[]) object)[2];
						heightWall[0] = ((int[]) object)[3];
						break;
				}
				button_1.addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						CVCGame.world.setUpdate();
						while (CVCGame.world.isUpdating());
						player_castle.planFortification(CVCFortification.FortificationType.Tower, (posX[0] = posXTower[0]), 6 /*Irrelevant*/, 6);
						CVCGame.world.setUpdate();

						slider.setValue(6);
						slider.setVisible(true);
						button_3.setVisible(true);
						button_4.setVisible(true);

						Gdx.input.setInputProcessor(stage);
					}
				});
				button_2.addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						CVCGame.world.setUpdate();
						while (CVCGame.world.isUpdating());
						player_castle.planFortification(CVCFortification.FortificationType.Wall, (posX[0] = posXWall[0]), widthWall[0], 4);
						CVCGame.world.setUpdate();

						slider.setRange(4, heightWall[0]);
						slider.setValue(4);
						slider.setVisible(true);
						button_3.setVisible(true);
						button_4.setVisible(true);

						Gdx.input.setInputProcessor(stage);
					}
				});
				break;
			case Tower:
			case Wall:
				button_1 = new TextButton("Repair", skin);
				if (((CVCFortification) object).getHealth() == 100)
					button_1.setDisabled(true);
				else
					button_1.addListener(new ChangeListener() {
						@Override
						public void changed(ChangeEvent event, Actor actor) {
							Timer.post(new Timer.Task() {
								@Override
								public void run() {
								}
							});
						}
					});
				switch (menuType)
				{
					case Tower:
						button_2 = new TextButton("Build", skin);
						if (((CVCTower) object).hasWeapon())
							button_2.setDisabled(true);
						else
							button_2.addListener(new ChangeListener() {
								@Override
								public void changed(ChangeEvent event, Actor actor) {
									Timer.post(new Timer.Task() {
										@Override
										public void run() {
											CVCGame.world.setUpdate();
											while (CVCGame.world.isUpdating());
											CVCGame.world.getPlayerCastle().buildWeapon(((CVCTower) object).getX() + 2, ((CVCTower) object).getHeight() + 3, false);
											((CVCTower) object).getWeapon();
											CVCGame.world.setUpdate();
											CVCGame.closeMenu();
										}
									});
								}
							});
						break;
					case Wall:
						button_2 = new TextButton("Defend", skin);
//						if (((CVCWall) object).checkSomething()) // to do
							button_2.setDisabled(true);
//						else
							button_2.addListener(new ChangeListener() {
								@Override
								public void changed(ChangeEvent event, Actor actor) {
									Timer.post(new Timer.Task() {
										@Override
										public void run() {
										}
									});
								}
							});
						break;
				}
				break;
			case Catapult:
			case Trebuchet:
			case Ballista:
				button_1 = new TextButton("Load", skin);
				button_2 = new TextButton("Fire", skin);
				if (((CVCWeapon) object).getAmmoBody() != null)
					button_1.setDisabled(true);
				button_2.setDisabled(true);
				button_1.addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						Timer.post(new Timer.Task() {
							@Override
							public void run() {
							}
						});
					}
				});
				button_2.addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						Timer.post(new Timer.Task() {
							@Override
							public void run() {
							}
						});
					}
				});
				break;
		}

		slider.setPosition(x - 100, y - 105);
		slider.setWidth(200);
		slider.setHeight(30);
		slider.setVisible(false);
		slider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				CVCGame.world.setUpdate();
				while (CVCGame.world.isUpdating());
				player_castle.changePlannedFortification(posX[0], widthWall[0], (int) slider.getValue());
				CVCGame.world.setUpdate();
			}
		});

		TextButton.TextButtonStyle buttonStyle = button_1.getStyle();
		buttonStyle.disabled = fieldStyle.background;
		button_1.setStyle(buttonStyle);
		button_1.setPosition(x - 100, y - 45); // y0 on top
		button_1.setWidth(200);
		button_1.setHeight(30);
		button_2.setStyle(buttonStyle);
		button_2.setPosition(x - 100, y - 75);
		button_2.setWidth(200);
		button_2.setHeight(30);
		button_3.setStyle(buttonStyle);
		button_3.setPosition(x - 100, y - 135);
		button_3.setWidth(100);
		button_3.setHeight(30);
		button_3.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				CVCGame.world.setUpdate();
				while (CVCGame.world.isUpdating());
				player_castle.buildPlannedFortification(posX[0], widthWall[0], (int) slider.getValue());
				CVCGame.world.setUpdate();

				CVCGame.closeMenu();
			}
		});
		button_4.setStyle(buttonStyle);
		button_4.setPosition(x, y - 135);
		button_4.setWidth(100);
		button_4.setHeight(30);
		button_4.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				CVCGame.world.setUpdate();
				while (CVCGame.world.isUpdating());
				player_castle.cancelPlannedFortification();
				CVCGame.world.setUpdate();

				slider.setVisible(false);
				button_3.setVisible(false);
				button_4.setVisible(false);

				Gdx.input.setInputProcessor(CVCGame.input_multi);
			}
		});
		button_5.setStyle(buttonStyle);
		button_5.setPosition(x + 100, y - 15);
		button_5.setWidth(30);
		button_5.setHeight(30);
		button_5.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				CVCGame.closeMenu();
			}
		});

		this.addActor(field);
		this.addActor(button_1);
		this.addActor(button_2);
		this.addActor(button_3);
		this.addActor(button_4);
		this.addActor(button_5);
		this.addActor(slider);
	}

	public void delete() {
		CVCGame.world.setUpdate();
		while (CVCGame.world.isUpdating());
		player_castle.cancelPlannedFortification();
		CVCGame.world.setUpdate();
	}
}
