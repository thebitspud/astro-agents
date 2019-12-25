package io.thebitspud.astroagents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class VersusOverScreen implements Screen {
	private AstroAgents game;
	private OrthographicCamera camera;
	String gameOverText;

	VersusOverScreen(final AstroAgents game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, AstroAgents.SCREEN_WIDTH, AstroAgents.SCREEN_HEIGHT);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();

		game.font.setColor(Color.WHITE);

		game.drawCenteredText("Game Over", 0, -50);
		game.drawCenteredText(gameOverText, 0, -100);
		game.drawCenteredText("Both players: Hold start to play again", 0, -150);

		if (game.gamepads.get(0).getButton(9) && game.gamepads.get(1).getButton(9)) {
			game.setScreen(game.vsGameScreen);
			dispose();
		}

		game.batch.end();
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
