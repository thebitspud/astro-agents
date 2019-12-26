package io.thebitspud.astroagents.entities;

import io.thebitspud.astroagents.AstroAgents;

public class Asteroid extends Projectile {
	private int health;

	public Asteroid(int x, int y, int xVelocity, int yVelocity, AstroAgents app) {
		super(x, y, 25, 25, xVelocity, yVelocity, app);

		texture = app.assets.smallAsteroid;
		damage = 10;
		health = 20;
	}

	public int getHealth() {
		return health;
	}

	public void adjustHealth(int value) {
		health += value;
	}
}
