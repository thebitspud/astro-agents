package io.thebitspud.astroagents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class VersusOverScreen implements Screen {
	private AstroAgents app;
	private OrthographicCamera camera;
	String gameOverText;

	VersusOverScreen(final AstroAgents app) {
		this.app = app;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, AstroAgents.SCREEN_WIDTH, AstroAgents.SCREEN_HEIGHT);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		app.batch.setProjectionMatrix(camera.combined);

		app.batch.begin();

		app.font.setColor(Color.WHITE);

		app.drawCenteredText("Game Over", 0, 60);
		app.drawCenteredText(gameOverText, 0, 10);
		app.drawCenteredText("Both players: Hold start to play again", 0, -40);

		if (app.gamepads.get(0).getButton(9) && app.gamepads.get(1).getButton(9)) {
			app.setScreen(app.vsGameScreen);
			dispose();
		}

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
	public void dispose() {

	}
}
