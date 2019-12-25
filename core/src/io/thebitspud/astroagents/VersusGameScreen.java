package io.thebitspud.astroagents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class VersusGameScreen implements Screen {
	private AstroAgents app;
	private OrthographicCamera camera;
	private VersusGame game;

	VersusGameScreen(final AstroAgents app) {
		this.app = app;
		game = new VersusGame(app);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, AstroAgents.SCREEN_WIDTH, AstroAgents.SCREEN_HEIGHT);
	}

	@Override
	public void show() {
		game.init();
	}

	@Override
	public void render(float delta) {
		game.tick(delta);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		app.batch.setProjectionMatrix(camera.combined);

		app.batch.begin();

		game.render();

		app.batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose () {
		game.dispose();
	}
}
