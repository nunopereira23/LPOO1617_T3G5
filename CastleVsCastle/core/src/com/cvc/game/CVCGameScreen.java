package com.cvc.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.cvc.logic.CVCFortification;
import com.cvc.logic.CVCStructure;
import com.cvc.logic.CVCUtils;
import com.cvc.logic.CVCWall;
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
		    Body[] bricks = null;
		    boolean[] brick_edges = null;
//		    Body roof = null; // For the building type, a triangle that can take several colors, depending on subclass; can also be null
		    for (CVCStructure pStruct : playerStructures)
		    {
			    switch (pStruct.getType())
			    {
				    case Fortification:
					    bricks = ((CVCFortification) pStruct).getBodies();
					    brick_edges = ((CVCFortification) pStruct).getEdges();
				    	break;
				    case Weapon:
				    	break;
			    }
		    }

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
		    // Walls (will change)
		    int n = 0;
		    for (Body body : bricks)
		    {
			    renderer.rect(
			    		CVCUtils.toPixels(body.getPosition().x),
					    CVCUtils.toPixels(body.getPosition().y),
					    0, 0, // Irrelevant
					    CVCUtils.toPixels(brick_edges[n] ? CVCWall.BRICK_EDGE_WIDTH : CVCWall.BRICK_WIDTH),
					    CVCUtils.toPixels(CVCWall.BRICK_HEIGHT),
					    1, 1, // Irrelevant
					    CVCUtils.DEGUNIT * body.getAngle(),
					    CVCUtils.DARK_GRAY, CVCUtils.GRAY, CVCUtils.LIGHT_GRAY, CVCUtils.GRAY);
			    ++n; if (n == brick_edges.length) { n = 0; }
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
		if (button != Input.Buttons.LEFT || pointer == 1) return false;
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
		if (button != Input.Buttons.LEFT || pointer == 1) return false;
		dragging = false;
		return true;
	}
}
