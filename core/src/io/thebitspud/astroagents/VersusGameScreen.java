package io.thebitspud.astroagents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class VersusGameScreen implements Screen {
	private AstroAgents game;
	private OrthographicCamera camera;

	private Texture ships, missiles;
	private TextureRegion ship1, ship2, missile1, missile2;

	private Rectangle player1, player2;

	private ArrayList<Rectangle> p1Missiles, p2Missiles;
	private long p1LastShot, p2LastShot;
	private int p1Health, p2Health;

	public VersusGameScreen(final AstroAgents game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);

		ships = new Texture("player.png");
		ship1 = new TextureRegion(ships, 0, 0, 31, 31);
		ship2 = new TextureRegion(ships, 31, 0, 31, 31);

		missiles = new Texture("missile.png");
		missile1 = new TextureRegion(missiles, 0, 0, 12, 4);
		missile2 = new TextureRegion(missiles, 12, 0, 12, 4);

		player1 = new Rectangle(20, 284, 31, 31);
		player2 = new Rectangle(749, 284, 31, 31);

		p1Missiles = new ArrayList<Rectangle>();
		p2Missiles = new ArrayList<Rectangle>();
	}

	@Override
	public void show() {
		player1.setPosition(20, 284);
		player2.setPosition(749, 284);

		p1Missiles.clear();
		p2Missiles.clear();

		p1Health = 100;
		p2Health = 100;
	}

	@Override
	public void render(float delta) {
		getInput(delta);
		tickMissiles(delta);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();

		for (Rectangle missile : p1Missiles) game.batch.draw(missile1, missile.x, missile.y);
		for (Rectangle missile : p2Missiles) game.batch.draw(missile2, missile.x, missile.y);

		game.font.setColor(Color.WHITE);
		game.batch.draw(ship1, player1.x, player1.y);
		game.batch.draw(ship2, player2.x, player2.y);

		game.font.draw(game.batch, "Player 1 Health: " + p1Health + "%",
				100, 550, 0, 1, false);
		game.font.draw(game.batch, "Player 2 Health: " + p2Health + "%",
				700, 550, 0, 1, false);

		game.batch.end();

		if(p1Health <= 0 || p2Health <= 0) {
			game.setScreen(game.vsGameScreen);
			dispose();

			if(p1Health <= 0) game.vsOverScreen.gameOverText = "Player 2 Wins";
			else game.vsOverScreen.gameOverText = "Player 1 Wins";
		}
	}

	private void getInput(float delta) {
		if (game.gamepads.size() > 0) {
			Controller gamepad = game.gamepads.get(0);

			if (TimeUtils.nanoTime() - p1LastShot > 500000000 & gamepad.getButton(1))
				p1FireMissile();

			if (gamepad.getAxis(0) == -1) player1.y += 100 * delta;
			else if (gamepad.getAxis(0) == 1) player1.y -= 100 * delta;
			if (gamepad.getAxis(1) == -1) player1.x -= 100 * delta;
			else if (gamepad.getAxis(1) == 1) player1.x += 100 * delta;

			if (player1.x < 0) player1.x = 0;
			else if (player1.x > 768) player1.x = 768;
			if (player1.y < 0) player1.y = 0;
			else if (player1.y > 568) player1.y = 568;
		}

		if (game.gamepads.size() > 1) {
			Controller gamepad = game.gamepads.get(1);

			if (TimeUtils.nanoTime() - p2LastShot > 500000000 & gamepad.getButton(1))
				p2FireMissile();

			if (gamepad.getAxis(0) == -1) player2.y += 100 * delta;
			else if (gamepad.getAxis(0) == 1) player2.y -= 100 * delta;
			if (gamepad.getAxis(1) == -1) player2.x -= 100 * delta;
			else if (gamepad.getAxis(1) == 1) player2.x += 100 * delta;

			if (player2.x < 0) player2.x = 0;
			else if (player2.x > 768) player2.x = 768;
			if (player2.y < 0) player2.y = 0;
			else if (player2.y > 568) player2.y = 568;
		}
	}

	private void tickMissiles(float delta) {
		for (Iterator<Rectangle> iter = p1Missiles.iterator(); iter.hasNext(); ) {
			Rectangle missile = iter.next();
			missile.x += 350 * delta;
			if (missile.x > 800) iter.remove();
			if (missile.overlaps(player2)) {
				p2Health -= 5;
				iter.remove();
			}
		}

		for (Iterator<Rectangle> iter = p2Missiles.iterator(); iter.hasNext(); ) {
			Rectangle missile = iter.next();
			missile.x -= 350 * delta;
			if (missile.x < -16) iter.remove();
			if (missile.overlaps(player1)) {
				p1Health -= 5;
				iter.remove();
			}
		}
	}

	private void p1FireMissile() {
		p1Missiles.add(new Rectangle(player1.x + 9, player1.y  + 4, 12, 4));
		p1Missiles.add(new Rectangle(player1.x + 9, player1.y  + 23, 12, 4));
		p1LastShot = TimeUtils.nanoTime();
	}

	private void p2FireMissile() {
		p2Missiles.add(new Rectangle(player2.x + 9, player2.y + 4, 12, 4));
		p2Missiles.add(new Rectangle(player2.x + 9, player2.y + 23, 12, 4));
		p2LastShot = TimeUtils.nanoTime();
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
		ships.dispose();
		missiles.dispose();
	}
}
