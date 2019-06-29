package io.thebitspud.astroagents;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class AstroAgents extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	private Texture ships, missiles;
	private TextureRegion ship1, ship2, missile1, missile2;

	private Rectangle player1, player2;
	private Controller gamepad1, gamepad2;

	private ArrayList<Rectangle> p1Missiles, p2Missiles;
	private long p1LastShot, p2LastShot;
	private int p1Health, p2Health;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		batch = new SpriteBatch();
		font = new BitmapFont();

		ships = new Texture("player.png");
		ship1 = new TextureRegion(ships, 0, 0, 31, 31);
		ship2 = new TextureRegion(ships, 31, 0, 31, 31);

		missiles = new Texture("missile.png");
		missile1 = new TextureRegion(missiles, 0, 0, 16, 9);
		missile2 = new TextureRegion(missiles, 16, 0, 16, 9);

		player1 = new Rectangle(20, 284, 31, 31);
		player2 = new Rectangle(749, 284, 31, 31);

		p1Missiles = new ArrayList<Rectangle>();
		p2Missiles = new ArrayList<Rectangle>();

		p1Health = 10;
		p2Health = 10;

		for (Controller controller : Controllers.getControllers()) {
			if(!controller.getName().contains("Gamepad")) continue;
			if(gamepad1 == null) gamepad1 = controller;
			else gamepad2 = controller;
		}
	}

	private void update() {
		camera.update();

		getInput();
		tickMissiles();
	}

	@Override
	public void render() {
		update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		font.draw(batch, "Player 1 HP: " + p1Health + " / 10", 30, 550);
		font.draw(batch, "Player 2 HP: " + p2Health + " / 10", 650, 550);

		batch.draw(ship1, player1.x, player1.y);
		batch.draw(ship2, player2.x, player2.y);

		for (Rectangle missile : p1Missiles) batch.draw(missile1, missile.x, missile.y);
		for (Rectangle missile : p2Missiles) batch.draw(missile2, missile.x, missile.y);

		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		ships.dispose();
		font.dispose();
		missiles.dispose();
	}

	private void getInput() {
		if(TimeUtils.nanoTime() - p1LastShot > 500000000
				& gamepad1.getButton(1)) p1FireMissile();
		if(TimeUtils.nanoTime() - p2LastShot > 500000000
				& gamepad2.getButton(1)) p2FireMissile();

		if(gamepad1.getAxis(0) == -1)
			player1.y += 100 * Gdx.graphics.getDeltaTime();
		else if(gamepad1.getAxis(0) == 1)
			player1.y -= 100 * Gdx.graphics.getDeltaTime();
		if(gamepad1.getAxis(1) == -1)
			player1.x -= 100 * Gdx.graphics.getDeltaTime();
		else if(gamepad1.getAxis(1) == 1)
			player1.x += 100 * Gdx.graphics.getDeltaTime();

		if(gamepad2.getAxis(0) == -1)
			player2.y += 100 * Gdx.graphics.getDeltaTime();
		else if(gamepad2.getAxis(0) == 1)
			player2.y -= 100 * Gdx.graphics.getDeltaTime();
		if(gamepad2.getAxis(1) == -1)
			player2.x -= 100 * Gdx.graphics.getDeltaTime();
		else if(gamepad2.getAxis(1) == 1)
			player2.x += 100 * Gdx.graphics.getDeltaTime();

		if(player1.x < 0) player1.x = 0;
		else if(player1.x > 768) player1.x = 768;
		if(player1.y < 0) player1.y = 0;
		else if(player1.y > 568) player1.y = 568;

		if(player2.x < 0) player2.x = 0;
		else if(player2.x > 768) player2.x = 768;
		if(player2.y < 0) player2.y = 0;
		else if(player2.y > 568) player2.y = 568;
	}

	private void tickMissiles() {
		for(Iterator<Rectangle> iter = p1Missiles.iterator(); iter.hasNext(); ) {
			Rectangle missile = iter.next();
			missile.x += 350 * Gdx.graphics.getDeltaTime();
			if(missile.x > 800) iter.remove();
			if(missile.overlaps(player2)) {
				p2Health--;
				iter.remove();
			}
		}

		for(Iterator<Rectangle> iter = p2Missiles.iterator(); iter.hasNext(); ) {
			Rectangle missile = iter.next();
			missile.x -= 350 * Gdx.graphics.getDeltaTime();
			if(missile.x < -16) iter.remove();
			if(missile.overlaps(player1)) {
				p1Health--;
				iter.remove();
			}
		}
	}

	private void p1FireMissile() {
		Rectangle missile = new Rectangle(player1.x + 8, player1.y  + 11, 16, 9);
		p1Missiles.add(missile);
		p1LastShot = TimeUtils.nanoTime();
	}

	private void p2FireMissile() {
		Rectangle missile = new Rectangle(player2.x + 8, player2.y + 11, 16, 9);
		p2Missiles.add(missile);
		p2LastShot = TimeUtils.nanoTime();
	}
}