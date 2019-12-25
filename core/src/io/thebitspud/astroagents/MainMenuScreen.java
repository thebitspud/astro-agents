package io.thebitspud.astroagents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.TimeUtils;

public class MainMenuScreen implements Screen {
	private AstroAgents app;
	private OrthographicCamera camera;
	private long lastControllerSwitch, lastGamepadCheck;

	MainMenuScreen(final AstroAgents app) {
		this.app = app;

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
		app.batch.setProjectionMatrix(camera.combined);

		app.batch.begin();

		app.font.setColor(Color.WHITE);
		app.drawCenteredText("Astro Agents", 0, 100);

		if (app.gamepads.size() < 2) {
			if(TimeUtils.nanoTime() - lastGamepadCheck > 500000000) {
				app.checkForGamepads();
			}

			app.font.setColor(Color.RED);
			app.drawCenteredText("You must plug in 2 controllers to play", 0, 50);
		} else {
			app.drawCenteredText("Both players: Hold start to begin", 0, 50);

			app.drawCenteredText("Press A to calibrate", 0, 0);
			if (app.gamepads.get(0).getButton(1))
				app.drawCenteredText("Player 1: A", -75, -50);
			if (app.gamepads.get(1).getButton(1))
				app.drawCenteredText("Player 1: A", 75, -50);

			if (app.gamepads.get(0).getButton(8)
					&& app.gamepads.get(1).getButton(8)
					&& TimeUtils.nanoTime() - lastControllerSwitch > 2000000000)
				switchControllers();

			if (TimeUtils.nanoTime() - lastControllerSwitch < 2000000000)
				app.drawCenteredText("Controllers successfully switched!", 0, -100);
			else app.drawCenteredText("Both players: Hold select to switch controllers", 0, -100);

			if (app.gamepads.get(0).getButton(9) && app.gamepads.get(1).getButton(9)) {
				app.setScreen(app.vsGameScreen);
				dispose();
			}
		}

		app.batch.end();
	}

	private void switchControllers() {
		Controller temp = app.gamepads.get(0);
		app.gamepads.set(0, app.gamepads.get(1));
		app.gamepads.set(1, temp);

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
