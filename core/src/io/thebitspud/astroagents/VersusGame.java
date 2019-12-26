package io.thebitspud.astroagents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import io.thebitspud.astroagents.entities.Asteroid;
import io.thebitspud.astroagents.entities.Projectile;
import io.thebitspud.astroagents.entities.Rocket;
import io.thebitspud.astroagents.entities.Seeker;

import java.util.ArrayList;
import java.util.Iterator;

public class VersusGame {
	private AstroAgents app;

	public Rectangle player1, player2;

	private ArrayList<Projectile> p1Missiles, p2Missiles;
	private ArrayList<Asteroid> asteroids;
	private long p1LastShot, p2LastShot, p1LastSeeker, p2LastSeeker;
	public int p1Health, p2Health;

	public VersusGame(final AstroAgents app) {
		this.app = app;

		final int yPos = (AstroAgents.SCREEN_HEIGHT / 2) - 16;
		player1 = new Rectangle(20, yPos, 31, 31);
		player2 = new Rectangle(AstroAgents.SCREEN_WIDTH - 51, yPos, 31, 31);

		p1Missiles = new ArrayList<Projectile>();
		p2Missiles = new ArrayList<Projectile>();
		asteroids = new ArrayList<Asteroid>();
	}

	public void init() {
		final int playerYPos = (AstroAgents.SCREEN_HEIGHT / 2) - 16;
		player1.setPosition(20, playerYPos);
		player2.setPosition(AstroAgents.SCREEN_WIDTH - 51, playerYPos);

		p1Missiles.clear();
		p2Missiles.clear();

		asteroids.clear();

		for(int i = 0; i < 30; i++) {
			final int xPos = (AstroAgents.SCREEN_WIDTH / 2) + (int) (Math.random() * 100) - 62;
			final int yPos = (int) ((AstroAgents.SCREEN_HEIGHT - 25 - app.vsGameScreen.hudHeight) * Math.random());
			asteroids.add(new Asteroid(xPos, yPos, 0, 0, app));
		}

		p1Health = 100;
		p2Health = 100;
	}

	public void tick(float delta) {
		checkIfDead();
		getInput(delta);
		tickMissiles(delta);
		tickAsteroids(delta);
	}

	public void render() {
		for (Projectile missile : p1Missiles) missile.render();
		for (Projectile missile : p2Missiles) missile.render();
		for (Asteroid asteroid: asteroids) asteroid.render();

		app.font.setColor(Color.WHITE);
		app.batch.draw(app.assets.ship1, player1.x, player1.y);
		app.batch.draw(app.assets.ship2, player2.x, player2.y);
	}

	private void checkIfDead() {
		if(p1Health <= 0 || p2Health <= 0) {
			if(p1Health <= 0) app.vsOverScreen.gameOverText = "Player 2 Wins";
			else app.vsOverScreen.gameOverText = "Player 1 Wins";

			app.setScreen(app.vsOverScreen);
		}
	}

	private void getInput(float delta) {
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

		if (app.gamepads.size() > 0) {
			Controller gamepad = app.gamepads.get(0);

			if (gamepad.getButton(1) && (TimeUtils.nanoTime() - p1LastShot) / 100000000 > 4) {
				p1Missiles.add(new Rocket((int) player1.x + 9, (int) player1.y  + 4, 500, 0, app));
				p1Missiles.add(new Rocket((int) player1.x + 9, (int) player1.y  + 23, 500, 0, app));
				p1LastShot = TimeUtils.nanoTime();
			}

			if (gamepad.getButton(0) && (TimeUtils.nanoTime() - p1LastSeeker) / 100000000 > 20) {
				p1Missiles.add(new Seeker((int) player1.x + 4, (int) player1.y  + 13, 1, 0, app));
				p1LastSeeker = TimeUtils.nanoTime();
			}

			if (gamepad.getAxis(0) == -1) player1.y += 150 * delta;
			else if (gamepad.getAxis(0) == 1) player1.y -= 150 * delta;
			if (gamepad.getAxis(1) == -1) player1.x -= 150 * delta;
			else if (gamepad.getAxis(1) == 1) player1.x += 150 * delta;

			if (player1.x < 0) player1.x = 0;
			else if (player1.x > AstroAgents.SCREEN_WIDTH) player1.x = AstroAgents.SCREEN_WIDTH;
			if (player1.y < 0) player1.y = 0;
			else if (player1.y > AstroAgents.SCREEN_HEIGHT - 31 - app.vsGameScreen.hudHeight)
				player1.y = AstroAgents.SCREEN_HEIGHT - 31 - app.vsGameScreen.hudHeight;
		}

		if (app.gamepads.size() > 1) {
			Controller gamepad = app.gamepads.get(1);

			if (gamepad.getButton(1) && (TimeUtils.nanoTime() - p2LastShot) / 100000000 > 4) {
				p2Missiles.add(new Rocket((int) player2.x + 9, (int) player2.y + 4, -500, 0, app));
				p2Missiles.add(new Rocket((int) player2.x + 9, (int) player2.y + 23, -500, 0, app));
				p2LastShot = TimeUtils.nanoTime();
			}

			if (gamepad.getButton(0) && (TimeUtils.nanoTime() - p2LastSeeker) / 100000000 > 20) {
				p2Missiles.add(new Seeker((int) player2.x + 4, (int) player2.y  + 13, -1, 0, app));
				p2LastSeeker = TimeUtils.nanoTime();
			}

			if (gamepad.getAxis(0) == -1) player2.y += 150 * delta;
			else if (gamepad.getAxis(0) == 1) player2.y -= 150 * delta;
			if (gamepad.getAxis(1) == -1) player2.x -= 150 * delta;
			else if (gamepad.getAxis(1) == 1) player2.x += 150 * delta;

			if (player2.x < 0) player2.x = 0;
			else if (player2.x > AstroAgents.SCREEN_WIDTH - 31) player2.x = AstroAgents.SCREEN_WIDTH - 31;
			if (player2.y < 0) player2.y = 0;
			else if (player2.y > AstroAgents.SCREEN_HEIGHT - 31 - app.vsGameScreen.hudHeight)
				player2.y = AstroAgents.SCREEN_HEIGHT - 31 - app.vsGameScreen.hudHeight;
		}
	}

	private void tickMissiles(float delta) {
		for (Iterator<Projectile> iter = p1Missiles.iterator(); iter.hasNext(); ) {
			Projectile missile = iter.next();
			missile.tick(delta);

			if (missile.x > AstroAgents.SCREEN_WIDTH) iter.remove();
			if (missile.overlaps(player2)) {
				p2Health -= missile.getDamage();
				iter.remove();
			}
		}

		for (Iterator<Projectile> iter = p2Missiles.iterator(); iter.hasNext(); ) {
			Projectile missile = iter.next();
			missile.tick(delta);

			if (missile.x < -16) iter.remove();
			if (missile.overlaps(player1)) {
				p1Health -= missile.getDamage();
				iter.remove();
			}
		}
	}

	private void tickAsteroids(float delta) {
		for (Iterator<Asteroid> iter = asteroids.iterator(); iter.hasNext(); ) {
			Asteroid asteroid = iter.next();
			asteroid.tick(delta);

			if (asteroid.overlaps(player1)) {
				p1Health -= asteroid.getDamage();
				iter.remove();
				return;
			}

			if (asteroid.overlaps(player2)) {
				p2Health -= asteroid.getDamage();
				iter.remove();
				return;
			}

			if(iterateCollisions(asteroid, iter, p1Missiles)) return;
			if(iterateCollisions(asteroid, iter, p2Missiles)) return;
		}
	}

	private boolean iterateCollisions(Asteroid asteroid, Iterator<Asteroid> iterObj, ArrayList<Projectile> list) {
		for (Iterator<Projectile> iter = list.iterator(); iter.hasNext(); ) {
			Projectile listObj = iter.next();
			if(listObj.overlaps(asteroid)) {
				asteroid.adjustHealth(-listObj.getDamage());
				if(asteroid.getHealth() <= 0) iterObj.remove();

				iter.remove();
				return true;
			}
		}

		return false;
	}

	public void dispose() {
		app.assets.dispose();
	}
}
