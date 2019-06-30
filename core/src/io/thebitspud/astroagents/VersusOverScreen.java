package io.thebitspud.astroagents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class VersusOverScreen implements Screen {
	private AstroAgents game;
	private OrthographicCamera camera;
	public String gameOverText;

	public VersusOverScreen(final AstroAgents game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
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
		game.font.draw(game.batch, "Game Over", 400, 350, 0, 1, false);
		game.font.draw(game.batch, gameOverText, 400, 300, 0, 1, false);
		game.font.draw(game.batch, "Both players: Hold start to play again",
				400, 250,0, 1, false);

		game.batch.end();

		if (game.gamepads.get(0).getButton(9)
				&& game.gamepads.get(1).getButton(9)) {
			game.setScreen(game.vsGameScreen);
			dispose();
		}
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
