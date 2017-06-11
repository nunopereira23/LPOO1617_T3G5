package com.cvc.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.cvc.logic.CVCFortification;
import com.cvc.logic.CVCProjectile;
import com.cvc.logic.CVCStructure;
import com.cvc.logic.CVCUtils;
import com.cvc.logic.CVCWeapon;
import com.cvc.logic.CVCWorld;


public class CVCGameScreen implements Screen, InputProcessor {
	private float deltaLast = 0;

	private OrthographicCamera camera;
	private ShapeRenderer renderer;

	/** Create the game screen
	 *
	 */
	public CVCGameScreen() {
	    renderer = new ShapeRenderer();
	    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	    camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
    }

    // Screen

    @Override
    /** Render the game screen
     *
     * @param delta The time in seconds since the last render
     */
    public void render(float delta) {
	    deltaLast += delta;
	    if (deltaLast > 0.008333) { // Cap at 120fps
		    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		    Gdx.gl.glEnable(GL20.GL_BLEND);
		    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		    camera.update();
		    renderer.setProjectionMatrix(camera.combined);

		    Body ground = CVCGame.world.getGround();
		    CVCStructure[] structures = null;
		    Body[] bodies = null;
		    float[] dying_bodies = null;

		    boolean[] fortification_edges = null;
            int fortification_high_edges = 0;

		    renderer.begin(ShapeRenderer.ShapeType.Filled);
			// Ground
		    renderer.rect(
				    CVCUtils.toPixels(ground.getPosition().x),
				    CVCUtils.toPixels(ground.getPosition().y),
				    0, 0,
				    CVCUtils.toPixels(CVCWorld.GROUND_WIDTH),
				    CVCUtils.toPixels(CVCWorld.GROUND_HEIGHT),
				    1, 1,
				    0,
				    Color.BROWN, Color.BROWN, Color.GREEN, Color.GREEN);
		    // Player Structures
		    structures = CVCGame.world.getPlayerCastle().getStructures();
            for (CVCStructure playerStructure : structures)
            {
	            bodies = playerStructure.getBodies();
	            dying_bodies = playerStructure.getDyingBodies();
                switch (playerStructure.getType())
                {
                    case Fortification:
                        fortification_edges = ((CVCFortification) playerStructure).getEdges();
                        fortification_high_edges = ((CVCFortification) playerStructure).getHighEdges();
	                    final boolean high = fortification_high_edges > 0;
                        for (int n = 0, n1 = 0; n < bodies.length; ++n) {
	                        if (dying_bodies[n] < 3) {
		                        renderer.rect(
				                        CVCUtils.toPixels(bodies[n].getPosition().x),
				                        CVCUtils.toPixels(bodies[n].getPosition().y),
				                        0, 0, // Irrelevant
				                        CVCUtils.toPixels(fortification_edges[n1] ? CVCFortification.STONE_EDGE_WIDTH : CVCFortification.STONE_WIDTH),
				                        CVCUtils.toPixels(fortification_high_edges > 0 && fortification_edges[n1] ? CVCFortification.STONE_EDGE_HEIGHT : CVCFortification.STONE_HEIGHT),
				                        1, 1, // Irrelevant
				                        CVCUtils.DEGUNIT * bodies[n].getAngle(),
				                        playerStructure.isBuilt() ? CVCUtils.DARK_GRAY : CVCUtils.DARK_GRAY_CLEAR,
				                        playerStructure.isBuilt() ? CVCUtils.GRAY : CVCUtils.GRAY_CLEAR,
				                        playerStructure.isBuilt() ? CVCUtils.LIGHT_GRAY : CVCUtils.LIGHT_GRAY_CLEAR,
				                        playerStructure.isBuilt() ? CVCUtils.GRAY : CVCUtils.GRAY_CLEAR);
		                        if (fortification_high_edges > 0 && fortification_edges[n1])
			                        --fortification_high_edges;
		                        ++n1;
		                        if (n1 == fortification_edges.length) {
			                        n1 = (!high ? 0 : 9);
		                        }
	                        }
                        }
                        break;
                    case Weapon:
	                    for (int n = 0; n < bodies.length; ++n) {
		                    if (dying_bodies[n] < 3) {
			                    Vector2[] vertex = new Vector2[]{new Vector2(), new Vector2(), new Vector2(), new Vector2()};
			                    ((PolygonShape) bodies[n].getFixtureList().get(0).getShape()).getVertex(0, vertex[0]);
			                    ((PolygonShape) bodies[n].getFixtureList().get(0).getShape()).getVertex(1, vertex[1]);
			                    ((PolygonShape) bodies[n].getFixtureList().get(0).getShape()).getVertex(2, vertex[2]);
			                    ((PolygonShape) bodies[n].getFixtureList().get(0).getShape()).getVertex(3, vertex[3]);
			                    renderer.triangle(
					                    CVCUtils.toPixels(bodies[n].getPosition().x + vertex[0].x),
					                    CVCUtils.toPixels(bodies[n].getPosition().y + vertex[0].y),
					                    CVCUtils.toPixels(bodies[n].getPosition().x + vertex[1].x),
					                    CVCUtils.toPixels(bodies[n].getPosition().y + vertex[1].y),
					                    CVCUtils.toPixels(bodies[n].getPosition().x + vertex[2].x),
					                    CVCUtils.toPixels(bodies[n].getPosition().y + vertex[2].y),
					                    CVCUtils.DARK_ORANGE, CVCUtils.ORANGE, CVCUtils.LIGHT_ORANGE);
			                    renderer.triangle(
					                    CVCUtils.toPixels(bodies[n].getPosition().x + vertex[2].x),
					                    CVCUtils.toPixels(bodies[n].getPosition().y + vertex[2].y),
					                    CVCUtils.toPixels(bodies[n].getPosition().x + vertex[3].x),
					                    CVCUtils.toPixels(bodies[n].getPosition().y + vertex[3].y),
					                    CVCUtils.toPixels(bodies[n].getPosition().x + vertex[0].x),
					                    CVCUtils.toPixels(bodies[n].getPosition().y + vertex[0].y),
					                    CVCUtils.LIGHT_ORANGE, CVCUtils.ORANGE, CVCUtils.DARK_ORANGE);
		                    }
	                    }
	                    Body ammo_body = ((CVCWeapon) playerStructure).getAmmoBody();
	                    if (ammo_body != null) {
		                    switch (((CVCWeapon) playerStructure).getSubType()) {
			                    case Catapult:
				                    renderer.setColor(0.5f, 0.5f, 0.5f, 0.5f); // not final
				                    renderer.circle(
						                    CVCUtils.toPixels(ammo_body.getPosition().x),
						                    CVCUtils.toPixels(ammo_body.getPosition().y),
						                    CVCUtils.toPixels(CVCProjectile.ROCK_RADIUS));
				                    break;
			                    case Ballista:
			                    	break;
			                    case Trebuchet:
			                    	break;
		                    }
	                    }
	                    break;
                }
            }
		    // Enemy Structures
		    structures = CVCGame.world.getEnemyCastle().getStructures();
		    for (CVCStructure enemyStructure : structures)
		    {
			    bodies = enemyStructure.getBodies();
			    dying_bodies = enemyStructure.getDyingBodies();
			    switch (enemyStructure.getType())
			    {
				    case Fortification:
					    fortification_edges = ((CVCFortification) enemyStructure).getEdges();
					    fortification_high_edges = ((CVCFortification) enemyStructure).getHighEdges();
					    final boolean high = fortification_high_edges > 0;
					    for (int n = 0, n1 = 0; n < bodies.length; ++n) {
						    if (dying_bodies[n] < 3) {
							    renderer.rect(
									    CVCUtils.toPixels(bodies[n].getPosition().x),
									    CVCUtils.toPixels(bodies[n].getPosition().y),
									    0, 0, // Irrelevant
									    CVCUtils.toPixels(fortification_edges[n1] ? CVCFortification.STONE_EDGE_WIDTH : CVCFortification.STONE_WIDTH),
									    CVCUtils.toPixels(fortification_high_edges > 0 && fortification_edges[n1] ? CVCFortification.STONE_EDGE_HEIGHT : CVCFortification.STONE_HEIGHT),
									    1, 1, // Irrelevant
									    CVCUtils.DEGUNIT * bodies[n].getAngle(),
									    enemyStructure.isBuilt() ? CVCUtils.DARK_GRAY : CVCUtils.DARK_GRAY_CLEAR,
									    enemyStructure.isBuilt() ? CVCUtils.GRAY : CVCUtils.GRAY_CLEAR,
									    enemyStructure.isBuilt() ? CVCUtils.LIGHT_GRAY : CVCUtils.LIGHT_GRAY_CLEAR,
									    enemyStructure.isBuilt() ? CVCUtils.GRAY : CVCUtils.GRAY_CLEAR);
							    if (fortification_high_edges > 0 && fortification_edges[n1])
								    --fortification_high_edges;
							    ++n1;
							    if (n1 == fortification_edges.length) {
								    n1 = (!high ? 0 : 9);
							    }
						    }
					    }
					    break;
				    case Weapon:
					    for (int n = 0; n < bodies.length; ++n) {
						    if (dying_bodies[n] < 3) {
							    Vector2[] vertex = new Vector2[]{new Vector2(), new Vector2(), new Vector2(), new Vector2()};
							    ((PolygonShape) bodies[n].getFixtureList().get(0).getShape()).getVertex(0, vertex[0]);
							    ((PolygonShape) bodies[n].getFixtureList().get(0).getShape()).getVertex(1, vertex[1]);
							    ((PolygonShape) bodies[n].getFixtureList().get(0).getShape()).getVertex(2, vertex[2]);
							    ((PolygonShape) bodies[n].getFixtureList().get(0).getShape()).getVertex(3, vertex[3]);
							    renderer.triangle(
									    CVCUtils.toPixels(bodies[n].getPosition().x + vertex[0].x),
									    CVCUtils.toPixels(bodies[n].getPosition().y + vertex[0].y),
									    CVCUtils.toPixels(bodies[n].getPosition().x + vertex[1].x),
									    CVCUtils.toPixels(bodies[n].getPosition().y + vertex[1].y),
									    CVCUtils.toPixels(bodies[n].getPosition().x + vertex[2].x),
									    CVCUtils.toPixels(bodies[n].getPosition().y + vertex[2].y),
									    CVCUtils.DARK_ORANGE, CVCUtils.ORANGE, CVCUtils.LIGHT_ORANGE);
							    renderer.triangle(
									    CVCUtils.toPixels(bodies[n].getPosition().x + vertex[2].x),
									    CVCUtils.toPixels(bodies[n].getPosition().y + vertex[2].y),
									    CVCUtils.toPixels(bodies[n].getPosition().x + vertex[3].x),
									    CVCUtils.toPixels(bodies[n].getPosition().y + vertex[3].y),
									    CVCUtils.toPixels(bodies[n].getPosition().x + vertex[0].x),
									    CVCUtils.toPixels(bodies[n].getPosition().y + vertex[0].y),
									    CVCUtils.LIGHT_ORANGE, CVCUtils.ORANGE, CVCUtils.DARK_ORANGE);
						    }
					    }
					    Body ammo_body = ((CVCWeapon) enemyStructure).getAmmoBody();
	                    if (ammo_body != null) {
		                    switch (((CVCWeapon) enemyStructure).getSubType()) {
			                    case Catapult:
				                    renderer.setColor(0.5f, 0.5f, 0.5f, 0.5f); // Not final
				                    renderer.circle(
						                    CVCUtils.toPixels(ammo_body.getPosition().x),
						                    CVCUtils.toPixels(ammo_body.getPosition().y),
						                    CVCUtils.toPixels(CVCProjectile.ROCK_RADIUS));
				                    break;
			                    case Ballista:
			                    	break;
			                    case Trebuchet:
			                    	break;
		                    }
	                    }
					    break;
			    }
		    }

		    // Debugging
		    renderer.rect(0f, CVCUtils.toPixels(20f), CVCUtils.toPixels(150f), CVCUtils.toPixels(0.25f), Color.RED, Color.BLUE, Color.BLUE, Color.RED); // Debug
		    renderer.rect(0f, 0f, CVCUtils.toPixels(0.25f), CVCUtils.toPixels(30f), Color.RED, Color.RED, Color.BLUE, Color.BLUE); // Debug
		    renderer.rect(CVCUtils.toPixels(50f), 0f, CVCUtils.toPixels(0.25f), CVCUtils.toPixels(30f), Color.RED, Color.RED, Color.BLUE, Color.BLUE); // Debug
		    renderer.rect(CVCUtils.toPixels(100f), 0f, CVCUtils.toPixels(0.25f), CVCUtils.toPixels(30f), Color.RED, Color.RED, Color.BLUE, Color.BLUE); // Debug
		    renderer.rect(CVCUtils.toPixels(125f), 0f, CVCUtils.toPixels(1f), CVCUtils.toPixels(1f), Color.RED, Color.RED, Color.BLUE, Color.BLUE); // Debug

		    renderer.end();

		    Gdx.gl.glDisable(GL20.GL_BLEND);

		    CVCGame.world.update(deltaLast);

		    if (CVCGame.menu != null) {
			    CVCGame.menu.act(deltaLast);
			    CVCGame.menu.draw();
		    }

		    CVCGame.hud.act(deltaLast);
		    CVCGame.hud.draw();

		    deltaLast = 0;
	    }
    }

    @Override
    /** Resize the screen
     *
     */
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    /** Pause
     *
     */
    public void pause() {
    }

    @Override
    /** Resume
     *
     */
    public void resume() {
    }

    @Override
    public void dispose() {
	    renderer.dispose();
    }

    // Input processor

	@Override
    /** Handle key down
     *
     */
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
    /** Handle key up
     *
     */
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
    /** Handle key typed
     *
     */
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
    /** Handle mouse moved
     *
     */
	public boolean mouseMoved (int screenX, int screenY) {
		return false;
	}

	@Override
    /** Handle scroll
     *
     */
	public boolean scrolled(int amount) {
		return false;
	}

	private boolean dragging = false;
	private int absScreenX = 0;
	private int absScreenY = 0;
	private int lastScreenX;
	private int lastScreenY;
	private int accDeltaX;
	private int accDeltaY;

	@Override
    /** Handle touch down the screen
     *
     */
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.RIGHT) return false;
		dragging = true;
		lastScreenX = screenX;
		lastScreenY = screenY;
		accDeltaX = 0;
		accDeltaY = 0;
		if (CVCGame.menu != null) {
			CVCGame.closeMenu();
		}
		return true;
	}

	@Override
    /** Handle drag the screen
     *
     */
	public boolean touchDragged (int screenX, int screenY, int pointer) {
		if (!dragging) return false;
		int lastAbsScreenX = absScreenX;
		int lastAbsScreenY = absScreenY;
		int deltaX = lastScreenX - screenX;
		int deltaY = screenY - lastScreenY;
		absScreenX += deltaX;
		absScreenY += deltaY;
		accDeltaX += Math.abs(deltaX);
		accDeltaY += Math.abs(deltaY);
		if (absScreenX < 0 || absScreenX > CVCUtils.toPixels(CVCWorld.GROUND_WIDTH) - Gdx.graphics.getWidth())
			absScreenX = lastAbsScreenX;
		else
			camera.translate(deltaX, 0);
		if (absScreenY < 0 || absScreenY > Gdx.graphics.getHeight() / 2)
			absScreenY = lastAbsScreenY;
		else
			camera.translate(0, deltaY);
		lastScreenX = screenX;
		lastScreenY = screenY;
		return true;
	}

	@Override
    /** Handle touch up the screen
     *
     */
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.RIGHT) return false;
		dragging = false;
		if (accDeltaX < 10 && accDeltaY < 10) {
			CVCGame.CLICK.play();
			if (absScreenY + (Gdx.graphics.getHeight() - screenY) >= CVCUtils.toPixels(1) && absScreenY + (Gdx.graphics.getHeight() - screenY) < CVCUtils.toPixels(20)) {
				if (absScreenX + screenX >= 0 && absScreenX + screenX < CVCUtils.toPixels(50)) {
					CVCGame.world.getContextMenu(CVCUtils.toMeters(absScreenX + screenX), CVCUtils.toMeters(absScreenY + Gdx.graphics.getHeight() - screenY));
				}
			}
		}
		return true;
	}
}
