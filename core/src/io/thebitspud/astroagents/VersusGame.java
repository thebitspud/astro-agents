package io.thebitspud.astroagents;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class VersusGame {
	private AstroAgents app;

	private Texture ships, missiles;
	private TextureRegion ship1, ship2, missile1, missile2, seeker1, seeker2;

	private Rectangle player1, player2;

	private ArrayList<Rectangle> p1Missiles, p2Missiles, p1Seekers, p2Seekers;
	private long p1LastShot, p2LastShot, p1LastSeeker, p2LastSeeker;
	private int p1Health, p2Health;

	VersusGame(final AstroAgents app) {
		this.app = app;

		ships = new Texture("player.png");
		ship1 = new TextureRegion(ships, 0, 0, 31, 31);
		ship2 = new TextureRegion(ships, 31, 0, 31, 31);

		missiles = new Texture("missile.png");
		missile1 = new TextureRegion(missiles, 0, 0, 13, 4);
		missile2 = new TextureRegion(missiles, 13, 0, 13, 4);
		seeker1 = new TextureRegion(missiles, 0, 4, 26, 5);
		seeker2 = new TextureRegion(missiles, 0, 9, 26, 5);

		player1 = new Rectangle(20, 284, 31, 31);
		player2 = new Rectangle(749, 284, 31, 31);

		p1Missiles = new ArrayList<Rectangle>();
		p2Missiles = new ArrayList<Rectangle>();
		p1Seekers = new ArrayList<Rectangle>();
		p2Seekers = new ArrayList<Rectangle>();
	}

	void init() {
		player1.setPosition(20, 284);
		player2.setPosition(749, 284);

		p1Missiles.clear();
		p2Missiles.clear();
		p1Seekers.clear();
		p2Seekers.clear();

		p1Health = 100;
		p2Health = 100;
	}

	void tick(float delta) {
		getInput(delta);
		tickMissiles(delta);
		tickSeekers(delta);
	}

	void render() {
		for (Rectangle missile : p1Missiles) app.batch.draw(missile1, missile.x, missile.y);
		for (Rectangle missile : p2Missiles) app.batch.draw(missile2, missile.x, missile.y);
		for (Rectangle seeker : p1Seekers) app.batch.draw(seeker1, seeker.x, seeker.y);
		for (Rectangle seeker : p2Seekers) app.batch.draw(seeker2, seeker.x, seeker.y);

		app.font.setColor(Color.WHITE);
		app.batch.draw(ship1, player1.x, player1.y);
		app.batch.draw(ship2, player2.x, player2.y);

		app.drawCenteredText("Player 1 Health: " + p1Health + "%", -300, 250);
		app.drawCenteredText("Player 2 Health: " + p2Health + "%", 300, 250);

		if(p1Health <= 0 || p2Health <= 0) {
			if(p1Health <= 0) app.vsOverScreen.gameOverText = "Player 2 Wins";
			else app.vsOverScreen.gameOverText = "Player 1 Wins";

			app.setScreen(app.vsOverScreen);
		}
	}

	private void getInput(float delta) {
		if (app.gamepads.size() > 0) {
			Controller gamepad = app.gamepads.get(0);

			if (gamepad.getButton(1) && (TimeUtils.nanoTime() - p1LastShot) / 100000000 > 4) {
				p1Missiles.add(new Rectangle(player1.x + 9, player1.y  + 4, 13, 4));
				p1Missiles.add(new Rectangle(player1.x + 9, player1.y  + 23, 13, 4));
				p1LastShot = TimeUtils.nanoTime();
			}

			if (gamepad.getButton(0) && (TimeUtils.nanoTime() - p1LastSeeker) / 100000000 > 20) {
				p1Seekers.add(new Rectangle(player1.x + 4, player1.y  + 13, 26, 5));
				p1LastSeeker = TimeUtils.nanoTime();
			}

			if (gamepad.getAxis(0) == -1) player1.y += 150 * delta;
			else if (gamepad.getAxis(0) == 1) player1.y -= 150 * delta;
			if (gamepad.getAxis(1) == -1) player1.x -= 150 * delta;
			else if (gamepad.getAxis(1) == 1) player1.x += 150 * delta;

			if (player1.x < 0) player1.x = 0;
			else if (player1.x > 768) player1.x = 768;
			if (player1.y < 0) player1.y = 0;
			else if (player1.y > 568) player1.y = 568;
		}

		if (app.gamepads.size() > 1) {
			Controller gamepad = app.gamepads.get(1);

			if (gamepad.getButton(1) && (TimeUtils.nanoTime() - p2LastShot) / 100000000 > 4) {
				p2Missiles.add(new Rectangle(player2.x + 9, player2.y + 4, 13, 4));
				p2Missiles.add(new Rectangle(player2.x + 9, player2.y + 23, 13, 4));
				p2LastShot = TimeUtils.nanoTime();
			}

			if (gamepad.getButton(0) && (TimeUtils.nanoTime() - p2LastSeeker) / 100000000 > 20) {
				p2Seekers.add(new Rectangle(player2.x + 4, player2.y  + 13, 26, 5));
				p2LastSeeker = TimeUtils.nanoTime();
			}

			if (gamepad.getAxis(0) == -1) player2.y += 150 * delta;
			else if (gamepad.getAxis(0) == 1) player2.y -= 150 * delta;
			if (gamepad.getAxis(1) == -1) player2.x -= 150 * delta;
			else if (gamepad.getAxis(1) == 1) player2.x += 150 * delta;

			if (player2.x < 0) player2.x = 0;
			else if (player2.x > 768) player2.x = 768;
			if (player2.y < 0) player2.y = 0;
			else if (player2.y > 568) player2.y = 568;
		}
	}

	private void tickMissiles(float delta) {
		for (Iterator<Rectangle> iter = p1Missiles.iterator(); iter.hasNext(); ) {
			Rectangle missile = iter.next();
			missile.x += 500 * delta;
			if (missile.x > 800) iter.remove();
			if (missile.overlaps(player2)) {
				p2Health -= 5;
				iter.remove();
			}
		}

		for (Iterator<Rectangle> iter = p2Missiles.iterator(); iter.hasNext(); ) {
			Rectangle missile = iter.next();
			missile.x -= 500 * delta;
			if (missile.x < -16) iter.remove();

			if (missile.overlaps(player1)) {
				p1Health -= 5;
				iter.remove();
			}
		}
	}

	private void tickSeekers(float delta) {
		float seekerBonus = 110 * delta;

		for (Iterator<Rectangle> iter = p1Seekers.iterator(); iter.hasNext(); ) {
			Rectangle seeker = iter.next();
			seeker.x += 400 * delta;
			if (seeker.x > 800) iter.remove();

			if (Math.abs(seeker.y - (player2.y + 15)) < seekerBonus) seeker.x += seekerBonus;
			else if(seeker.y < (player2.y + 15)) seeker.y += seekerBonus;
			else seeker.y -= seekerBonus;

			if (seeker.overlaps(player2)) {
				p2Health -= 20;
				iter.remove();
			}
		}

		for (Iterator<Rectangle> iter = p2Seekers.iterator(); iter.hasNext(); ) {
			Rectangle seeker = iter.next();
			seeker.x -= 400 * delta;
			if (seeker.x < -16) iter.remove();

			if (Math.abs(seeker.y - (player1.y + 15)) < seekerBonus) seeker.x -= seekerBonus;
			else if(seeker.y < (player1.y + 15)) seeker.y += seekerBonus;
			else seeker.y -= seekerBonus;

			if (seeker.overlaps(player1)) {
				p1Health -= 20;
				iter.remove();
			}
		}
	}

	void dispose() {
		ships.dispose();
		missiles.dispose();
	}
}
