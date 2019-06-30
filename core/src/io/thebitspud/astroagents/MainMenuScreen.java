package io.thebitspud.astroagents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.TimeUtils;

public class MainMenuScreen implements Screen {
	private AstroAgents game;
	private OrthographicCamera camera;
	private long lastControllerSwitch;

	public MainMenuScreen(final AstroAgents game) {
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
		game.font.draw(game.batch, "Astro Agents", 400, 400, 0, 1, false);

		if (game.gamepads.size() < 2) {
			game.font.setColor(Color.RED);
			game.font.draw(game.batch, "You must plug in 2 controllers to play",
					400, 350, 0, 1, false);
		} else {
			game.font.draw(game.batch, "Both players: Hold start to begin",
					400, 350, 0, 1, false);

			game.font.draw(game.batch, "Press A to calibrate",
					400, 300, 0, 1, false);
			if (game.gamepads.get(0).getButton(1))
				game.font.draw(game.batch, "Player 1: A",
						325, 250, 0, 1, false);
			if (game.gamepads.get(1).getButton(1))
				game.font.draw(game.batch, "Player 2: A",
						475, 250, 0, 1, false);

			if (game.gamepads.get(0).getButton(8)
					&& game.gamepads.get(1).getButton(8)
					&& TimeUtils.nanoTime() - lastControllerSwitch > 2000000000)
				switchControllers();

			if (TimeUtils.nanoTime() - lastControllerSwitch < 2000000000)
				game.font.draw(game.batch, "Controllers successfully switched!",
					400, 200, 0, 1, false);
			else game.font.draw(game.batch, "Both players: Hold select to switch controllers",
					400, 200, 0, 1, false);

			if (game.gamepads.get(0).getButton(9)
					&& game.gamepads.get(1).getButton(9)) {
				game.setScreen(game.vsGameScreen);
				dispose();
			}
		}

		game.batch.end();
	}

	private void switchControllers() {
		Controller temp = game.gamepads.get(0);
		game.gamepads.set(0, game.gamepads.get(1));
		game.gamepads.set(1, temp);

		lastControllerSwitch = TimeUtils.nanoTime();
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
