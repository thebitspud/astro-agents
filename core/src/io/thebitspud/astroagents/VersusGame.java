package io.thebitspud.astroagents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.utils.TimeUtils;
import io.thebitspud.astroagents.entities.*;

import java.util.ArrayList;
import java.util.Iterator;

public class VersusGame {
	private AstroAgents app;

	public Player player1, player2;

	private ArrayList<Asteroid> asteroids;

	public VersusGame(final AstroAgents app) {
		this.app = app;

		final int yPos = (AstroAgents.SCREEN_HEIGHT / 2) - 16;
		player1 = new Player(20, yPos, 1, app);
		player2 = new Player(AstroAgents.SCREEN_WIDTH - 51, yPos, 2, app);

		asteroids = new ArrayList<Asteroid>();
	}

	public void init() {
		player1.init();
		player2.init();

		asteroids.clear();

		for(int i = 0; i < 30; i++) {
			final int xPos = (AstroAgents.SCREEN_WIDTH / 2) + (int) (Math.random() * 100) - 62;
			final int yPos = (int) ((AstroAgents.SCREEN_HEIGHT - 25 - app.vsGameScreen.hudHeight) * Math.random());
			asteroids.add(new Asteroid(xPos, yPos, 0, 0, app));
		}
	}

	public void tick(float delta) {
		player1.tick(delta);
		player2.tick(delta);
		tickAsteroids(delta);
	}

	public void render() {
		player1.render();
		player2.render();
		for (Asteroid asteroid: asteroids) asteroid.render();
	}

	private void tickAsteroids(float delta) {
		for (Iterator<Asteroid> iter = asteroids.iterator(); iter.hasNext(); ) {
			Asteroid asteroid = iter.next();
			asteroid.tick(delta);

			if (asteroid.overlaps(player1)) {
				player1.adjustHealth(-asteroid.getDamage());
				iter.remove();
				return;
			}

			if (asteroid.overlaps(player2)) {
				player2.adjustHealth(-asteroid.getDamage());
				iter.remove();
				return;
			}

			if(iterateCollisions(asteroid, iter, player1.missiles)) return;
			if(iterateCollisions(asteroid, iter, player2.missiles)) return;
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
