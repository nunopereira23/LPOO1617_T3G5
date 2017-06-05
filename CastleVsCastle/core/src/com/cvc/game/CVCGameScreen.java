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
import com.cvc.logic.CVCStructure;
import com.cvc.logic.CVCTower;
import com.cvc.logic.CVCUtils;
import com.cvc.logic.CVCWall;
import com.cvc.logic.CVCWeapon;
import com.cvc.logic.CVCWorld;

public class CVCGameScreen implements Screen, InputProcessor {
    private CVCWorld world;
	private CVCStructure[] playerStructures;

	private float deltaLast = 0;

	private OrthographicCamera camera;
	private ShapeRenderer renderer;

	public CVCGameScreen() {
        world = new CVCWorld();
		playerStructures = world.getPlayerStructures();

	    renderer = new ShapeRenderer();
	    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	    camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
    }

    // Screen

    @Override
    public void render(float delta) {
	    deltaLast += delta;
	    if (deltaLast > 0.008333) { // Cap at 120fps
		    Gdx.gl.glClearColor(0.7f, 1.0f, 1.0f, 1);
		    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		    camera.update();
		    renderer.setProjectionMatrix(camera.combined);

		    world.update(deltaLast);

		    Body ground = world.getGround();
		    int n = 0;

		    Body[] fortification_bodies = null;
		    boolean[] fortification_edges = null;
            int fortification_high_edges = 0;

		    Body[] weapon_bodies = null;
//		    Body roof = null; // For the building type, a triangle that can take several colors, depending on subclass; can also be null

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
		    // Structures
            for (CVCStructure pStruct : playerStructures)
            {
                switch (pStruct.getType())
                {
                    case Fortification:
                        fortification_bodies = ((CVCFortification) pStruct).getBodies();
                        fortification_edges = ((CVCFortification) pStruct).getEdges();
                        fortification_high_edges = ((CVCFortification) pStruct).getHighEdges();
	                    n = 0;
                        for (Body body : fortification_bodies) {
                            renderer.rect(
                                    CVCUtils.toPixels(body.getPosition().x),
                                    CVCUtils.toPixels(body.getPosition().y),
                                    0, 0, // Irrelevant
                                    CVCUtils.toPixels(fortification_edges[n] ? CVCFortification.STONE_EDGE_WIDTH : CVCFortification.STONE_WIDTH),
                                    CVCUtils.toPixels(fortification_high_edges > 0 && fortification_edges[n] ? CVCFortification.STONE_EDGE_HEIGHT : CVCFortification.STONE_HEIGHT),
                                    1, 1, // Irrelevant
                                    CVCUtils.DEGUNIT * body.getAngle(),
                                    CVCUtils.DARK_GRAY, CVCUtils.GRAY, CVCUtils.LIGHT_GRAY, CVCUtils.GRAY);
                            if (fortification_high_edges > 0 && fortification_edges[n]) --fortification_high_edges;
                            ++n; if (n == fortification_edges.length) { n = (((CVCFortification) pStruct).getHighEdges() == 0 ? 0 : 9); }
                        }
                        break;
                    case Weapon:
	                    weapon_bodies = ((CVCWeapon) pStruct).getBodies();
	                    for (Body body : weapon_bodies) {
		                    Vector2[] vertex = new Vector2[]{new Vector2(),new Vector2(),new Vector2(),new Vector2()};
		                    ((PolygonShape) body.getFixtureList().get(0).getShape()).getVertex(0, vertex[0]);
			                ((PolygonShape) body.getFixtureList().get(0).getShape()).getVertex(1, vertex[1]);
			                ((PolygonShape) body.getFixtureList().get(0).getShape()).getVertex(2, vertex[2]);
		                    ((PolygonShape) body.getFixtureList().get(0).getShape()).getVertex(3, vertex[3]);
		                    renderer.triangle(
				                    CVCUtils.toPixels(body.getPosition().x + vertex[0].x),
				                    CVCUtils.toPixels(body.getPosition().y + vertex[0].y),
				                    CVCUtils.toPixels(body.getPosition().x + vertex[1].x),
				                    CVCUtils.toPixels(body.getPosition().y + vertex[1].y),
				                    CVCUtils.toPixels(body.getPosition().x + vertex[2].x),
				                    CVCUtils.toPixels(body.getPosition().y + vertex[2].y),
				                    CVCUtils.DARK_GRAY, CVCUtils.GRAY, CVCUtils.LIGHT_GRAY);
		                    renderer.triangle(
				                    CVCUtils.toPixels(body.getPosition().x + vertex[2].x),
				                    CVCUtils.toPixels(body.getPosition().y + vertex[2].y),
				                    CVCUtils.toPixels(body.getPosition().x + vertex[3].x),
				                    CVCUtils.toPixels(body.getPosition().y + vertex[3].y),
				                    CVCUtils.toPixels(body.getPosition().x + vertex[0].x),
				                    CVCUtils.toPixels(body.getPosition().y + vertex[0].y),
				                    CVCUtils.LIGHT_GRAY, CVCUtils.GRAY, CVCUtils.DARK_GRAY);
	                    }
	                    break;
                }
            }
		    // Debugging
		    renderer.rect(0f, CVCUtils.toPixels(20f), CVCUtils.toPixels(150f), CVCUtils.toPixels(0.25f), Color.RED, Color.BLUE, Color.BLUE, Color.RED); // Debug
		    renderer.rect(0f, 0f, CVCUtils.toPixels(0.25f), CVCUtils.toPixels(30f), Color.RED, Color.RED, Color.BLUE, Color.BLUE); // Debug
		    renderer.rect(CVCUtils.toPixels(50f), 0f, CVCUtils.toPixels(0.25f), CVCUtils.toPixels(30f), Color.RED, Color.RED, Color.BLUE, Color.BLUE); // Debug
		    renderer.rect(CVCUtils.toPixels(100f), 0f, CVCUtils.toPixels(0.25f), CVCUtils.toPixels(30f), Color.RED, Color.RED, Color.BLUE, Color.BLUE); // Debug
		    renderer.end();

		    deltaLast = 0;
	    }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() { // Super important
	    Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() { // Super important
	    Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        world.dispose();
    }

    // Input processor

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean mouseMoved (int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	private boolean dragging = false;
	private int absScreenX = 0;
	private int absScreenY = 0;
	private int lastScreenX;
	private int lastScreenY;

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		if (button != Input.Buttons.LEFT) return false;
		dragging = true;
		lastScreenX = screenX;
		lastScreenY = screenY;
		return true;
	}

	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer) {
		if (!dragging) return false;
		int lastAbsScreenX = absScreenX;
		int lastAbsScreenY = absScreenY;
		int deltaX = lastScreenX - screenX;
		int deltaY = screenY - lastScreenY;
		absScreenX += deltaX;
		absScreenY += deltaY;
		if (absScreenX < 0 || absScreenX > CVCUtils.toPixels(150) - Gdx.graphics.getWidth())
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
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		if (button != Input.Buttons.LEFT) return false;
		dragging = false;
		return true;
	}
}
