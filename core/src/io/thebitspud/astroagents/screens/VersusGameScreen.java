package io.thebitspud.astroagents.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.thebitspud.astroagents.AstroAgents;
import io.thebitspud.astroagents.VersusGame;

public class VersusGameScreen implements Screen {
	private AstroAgents app;
	private OrthographicCamera camera;

	public VersusGame game;
	private TextureRegion grayBox;
	public final int hudHeight = 50,
			textXOffset = AstroAgents.SCREEN_WIDTH / 2 - 100,
			textYOffset = AstroAgents.SCREEN_HEIGHT / 2 - 20;

	public VersusGameScreen(final AstroAgents app) {
		this.app = app;

		game = new VersusGame(app);
		grayBox = new TextureRegion(new Texture("asteroids.png"), 10, 10, 1, 1);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, AstroAgents.SCREEN_WIDTH, AstroAgents.SCREEN_HEIGHT);
	}

	@Override
	public void show() {
		game.init();
	}

	@Override
	public void render(float delta) {
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		app.batch.setProjectionMatrix(camera.combined);

		app.batch.begin();

		game.render();

		app.batch.draw(grayBox, 0, AstroAgents.SCREEN_HEIGHT - 50, AstroAgents.SCREEN_WIDTH, hudHeight);
		app.drawCenteredText("Player 1 Health: " + game.player1.getHealth(), -textXOffset, textYOffset);
		app.drawCenteredText("Player 2 Health: " + game.player2.getHealth(), textXOffset, textYOffset);

		app.batch.end();

		game.tick(delta);
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
