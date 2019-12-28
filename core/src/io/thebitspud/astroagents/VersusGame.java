package io.thebitspud.astroagents;

import io.thebitspud.astroagents.entities.*;

import java.util.ArrayList;
import java.util.Iterator;

public class VersusGame {
	private AstroAgents app;

	public Player player1, player2;

	private ArrayList<Projectile> asteroids;
	private ArrayList<Explosion> explosions;
	private ArrayList<Powerup> powerups;

	public VersusGame(final AstroAgents app) {
		this.app = app;

		final int yPos = (AstroAgents.SCREEN_HEIGHT / 2) - 16;
		player1 = new Player(20, yPos, 1, app);
		player2 = new Player(AstroAgents.SCREEN_WIDTH - 51, yPos, 2, app);

		asteroids = new ArrayList<Projectile>();
		explosions = new ArrayList<Explosion>();
		powerups = new ArrayList<Powerup>();
	}

	public void init() {
		player1.init();
		player2.init();

		asteroids.clear();
		powerups.clear();
		explosions.clear();

		for(int i = 0; i < 60; i++) {
			int xPos;
			if(i < 20) xPos = (AstroAgents.SCREEN_WIDTH / 2) + (int) (Math.random() * 100) - 62;
			else if(i < 40) xPos = (AstroAgents.SCREEN_WIDTH / 2) + (int) (Math.random() * 200) - 112;
			else xPos = (AstroAgents.SCREEN_WIDTH / 2) + (int) (Math.random() * 500) - 262;

			final int yPos = (int) ((AstroAgents.SCREEN_HEIGHT - 25 - app.vsGameScreen.hudHeight) * Math.random());

			asteroids.add(new Asteroid(xPos, yPos, 0, (int)(Math.random() * 100) - 50, app));
		}

		for(int i = 0; i < 5; i++) {
			final int xPos = (AstroAgents.SCREEN_WIDTH / 2) + (int) (Math.random() * 500) - 275;
			final int yPos = (AstroAgents.SCREEN_HEIGHT - 50 - app.vsGameScreen.hudHeight) / 10 * (2 * i + 1);
			asteroids.add(new LargeAsteroid(xPos, yPos, 0, 0, app));
		}
	}

	public void tick(float delta) {
		player1.tick(delta);
		player2.tick(delta);
		tickAsteroids(delta);
		tickExplosions(delta);
		tickPowerups(delta);
	}

	public void render() {
		player1.render();
		player2.render();
		for (Projectile asteroid: asteroids) asteroid.render();
		for (Explosion explosion: explosions) explosion.render();
		for (Powerup powerup: powerups) powerup.render();
	}

	private void tickAsteroids(float delta) {
		for(Projectile asteroid: asteroids) asteroid.tick(delta);

		for (Iterator<Projectile> iter = asteroids.iterator(); iter.hasNext(); ) {
			Projectile asteroid = iter.next();

			if (asteroid.overlaps(player1)) {
				player1.adjustHealth(-asteroid.getDamage());
				iter.remove();

				if(asteroid.getClass() == LargeAsteroid.class) {
					largeAsteroidKill((LargeAsteroid) asteroid);
					return;
				} else if(Math.random() > 0.67) spawnPowerUp((int) asteroid.x, (int) asteroid.y);
			}

			if (asteroid.overlaps(player2)) {
				player2.adjustHealth(-asteroid.getDamage());
				iter.remove();

				if(asteroid.getClass() == LargeAsteroid.class) {
					largeAsteroidKill((LargeAsteroid) asteroid);
					return;
				} else if(Math.random() > 0.67) spawnPowerUp((int) asteroid.x, (int) asteroid.y);
			}

			if(iterateAsteroidCollisions(asteroid, iter, player1.missiles)) return;
			if(iterateAsteroidCollisions(asteroid, iter, player2.missiles)) return;
		}
	}

	private void spawnPowerUp(int x, int y) {
		powerups.add(new Powerup(x, y, (int) (Math.random() * 4), app));
	}

	public void initExplosion(int x, int y, boolean large) {
		Explosion explosion;

		if(large) explosion = new Explosion(x - 16, y - 16, true, app);
		else explosion = new Explosion(x - 8, y - 8, false, app);

		explosions.add(explosion);

		if (explosion.overlaps(player1)) player1.adjustHealth(-explosion.getDamage());
		if (explosion.overlaps(player2)) player2.adjustHealth(-explosion.getDamage());

		for (Iterator<Projectile> iter = asteroids.iterator(); iter.hasNext(); ) {
			Projectile asteroid = iter.next();

			if (asteroid.overlaps(explosion)) {
				asteroid.adjustHealth(-explosion.getDamage());

				if (asteroid.getClass() == LargeAsteroid.class) {
					if (asteroid.getHealth() <= 0) {
						iter.remove();
						largeAsteroidKill((LargeAsteroid) asteroid);
						return;
					}
				} else if (asteroid.getHealth() <= 0) iter.remove();
			}
		}
	}

	private void tickExplosions(float delta) {
		for (Iterator<Explosion> iter = explosions.iterator(); iter.hasNext(); ) {
			Explosion explosion = iter.next();
			explosion.tick(delta);

			if (!explosion.getActive()) iter.remove();
		}
	}

	private void tickPowerups(float delta) {
		for (Iterator<Powerup> iter = powerups.iterator(); iter.hasNext(); ) {
			Powerup powerup = iter.next();
			powerup.tick(delta);

			if(!powerup.active) {
				iter.remove();
				return;
			}

			if (powerup.overlaps(player1)) {
				powerup.applyEffects(player1);
				iter.remove();
				return;
			}

			if (powerup.overlaps(player2)) {
				powerup.applyEffects(player2);
				iter.remove();
				return;
			}
		}
	}

	private boolean iterateAsteroidCollisions(Projectile asteroid, Iterator<Projectile> iterObj,
	                                     ArrayList<Projectile> list) {
		for (Iterator<Projectile> iter = list.iterator(); iter.hasNext(); ) {
			Projectile listObj = iter.next();

			if(asteroid.getClass() == LargeAsteroid.class) {
				if(((LargeAsteroid) asteroid).circleContains((int) (listObj.x + listObj.width / 2),
						(int) (listObj.y + listObj.height / 2))) {
					asteroid.adjustHealth(-listObj.getDamage());

					if(asteroid.getHealth() <= 0) {
						iterObj.remove();

						largeAsteroidKill((LargeAsteroid) asteroid);
					}

					boolean isSeeker = false;
					if(listObj.getClass() == Seeker.class) isSeeker = true;

					iter.remove();
					initExplosion((int) (listObj.x + listObj.width / 2), (int) (listObj.y + listObj.height / 2), isSeeker);
					return true;
				}
			} else if(listObj.overlaps(asteroid)) {
				asteroid.adjustHealth(-listObj.getDamage());

				if(asteroid.getHealth() <= 0) {
					iterObj.remove();

					if(Math.random() > 0.67) spawnPowerUp((int) asteroid.x, (int) asteroid.y);
				}

				boolean isSeeker = false;
				if(listObj.getClass() == Seeker.class) isSeeker = true;

				iter.remove();

				initExplosion((int) (listObj.x + listObj.width / 2), (int) (listObj.y + listObj.height / 2), isSeeker);
				return true;
			}
		}

		return false;
	}

	private void largeAsteroidKill(LargeAsteroid asteroid) {
		spawnPowerUp((int) asteroid.x + 12, (int) asteroid.y + 12);

		asteroids.add(new Asteroid((int) asteroid.x + 12, (int) asteroid.y + 12,
				(int) (-Math.random() * 50) - 50,(int) (-Math.random() * 50) - 50, app));
		asteroids.add(new Asteroid((int) asteroid.x + 12, (int) asteroid.y + 12,
				(int) (Math.random() * 50 + 50), (int) (-Math.random() * 50 - 50), app));
		asteroids.add(new Asteroid((int) asteroid.x + 12, (int) asteroid.y + 12,
				(int) (-Math.random() * 50 - 50), (int) (Math.random() * 50 + 50), app));
		asteroids.add(new Asteroid((int) asteroid.x + 12, (int) asteroid.y + 12,
				(int) (Math.random() * 50 + 50), (int) (Math.random() * 50 + 50), app));
		asteroids.add(new Asteroid((int) asteroid.x + 12, (int) asteroid.y + 12,
				(int) (Math.random() * 200 - 100), (int) (Math.random() * 200 - 100), app));
	}

	public void dispose() {
		app.assets.dispose();
	}
}
