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
	private long lastControllerSwitch, lastGamepadCheck;

	MainMenuScreen(final AstroAgents game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, AstroAgents.SCREEN_WIDTH, AstroAgents.SCREEN_HEIGHT);
		lastGamepadCheck = TimeUtils.nanoTime();
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
		game.drawCenteredText("Astro Agents", 0, 0);

		if (game.gamepads.size() < 2) {
			if(TimeUtils.nanoTime() - lastGamepadCheck > 500000000) {
				game.checkForGamepads();
			}

			game.font.setColor(Color.RED);
			game.drawCenteredText("You must plug in 2 controllers to play", 0, -50);
		} else {
			game.drawCenteredText("Both players: Hold start to begin", 0, -50);

			game.drawCenteredText("Press A to calibrate", 0, -100);
			if (game.gamepads.get(0).getButton(1))
				game.drawCenteredText("Player 1: A", -75, -150);
			if (game.gamepads.get(1).getButton(1))
				game.drawCenteredText("Player 1: A", 75, -150);

			if (game.gamepads.get(0).getButton(8)
					&& game.gamepads.get(1).getButton(8)
					&& TimeUtils.nanoTime() - lastControllerSwitch > 2000000000)
				switchControllers();

			if (TimeUtils.nanoTime() - lastControllerSwitch < 2000000000)
				game.drawCenteredText("Controllers successfully switched!", 0, -200);
			else game.drawCenteredText("Both players: Hold select to switch controllers", 0, -200);

			if (game.gamepads.get(0).getButton(9) && game.gamepads.get(1).getButton(9)) {
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
