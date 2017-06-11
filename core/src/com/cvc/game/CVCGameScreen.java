<<<<<<< HEAD
package com.cvc.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.cvc.logic.CVCUtils;
import com.cvc.logic.CVCWorld;

public class CVCGameScreen implements Screen, InputProcessor {
    private final CVCGame game;
    private CVCWorld world;

	private final float ground_width = 150f;
	private final float WHRATIO = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();

	private float deltaLast = 0;

	private OrthographicCamera camera;
	private ShapeRenderer renderer;

	public CVCGameScreen(final CVCGame game) {
        this.game = game;

        world = new CVCWorld();

	    renderer = new ShapeRenderer();

	    // Constructs a new OrthographicCamera, using the given viewport width and height
	    // Height is multiplied by aspect ratio.
	    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	    camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
	//	camera.update();
    }

    // Screen

    @Override
    public void render(float delta) {
	    deltaLast += delta;
	    if (deltaLast > 0.016667) {
		    Gdx.gl.glClearColor(0.7f, 1.0f, 1.0f, 1); // Starts to look more like sky
		    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		    world.update(deltaLast);
		    camera.update();

		    renderer.setProjectionMatrix(camera.combined);
		    renderer.begin(ShapeRenderer.ShapeType.Filled);

		    renderer.rect(0f, 0f, CVCUtils.toPixels(ground_width), CVCUtils.toPixels(1f), Color.BROWN, Color.BROWN, Color.GREEN, Color.GREEN); // Starts to look more like terrain
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
=======
package com.cvc.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.cvc.logic.CVCUtils;
import com.cvc.logic.CVCWorld;

public class CVCGameScreen implements Screen, InputProcessor {
    private final CVCGame game;
    private CVCWorld world;

	private final float ground_width = 150f;
	private final float WHRATIO = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();

	private float deltaLast = 0;

	private OrthographicCamera camera;
	private ShapeRenderer renderer;

	public CVCGameScreen(final CVCGame game) {
        this.game = game;

        world = new CVCWorld();

	    renderer = new ShapeRenderer();

	    // Constructs a new OrthographicCamera, using the given viewport width and height
	    // Height is multiplied by aspect ratio.
	    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	    camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
	//	camera.update();
    }

    // Screen

    @Override
    public void render(float delta) {
	    deltaLast += delta;
	    if (deltaLast > 0.016667) {
		    Gdx.gl.glClearColor(0.7f, 1.0f, 1.0f, 1); // Starts to look more like sky
		    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		    world.update(deltaLast);
		    camera.update();

		    renderer.setProjectionMatrix(camera.combined);
		    renderer.begin(ShapeRenderer.ShapeType.Filled);

		    renderer.rect(0f, 0f, CVCUtils.toPixels(ground_width), CVCUtils.toPixels(1f), Color.BROWN, Color.BROWN, Color.GREEN, Color.GREEN); // Starts to look more like terrain
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
>>>>>>> e3b0c0e11509b37a004d7aefb8b639f645e3ebfd
