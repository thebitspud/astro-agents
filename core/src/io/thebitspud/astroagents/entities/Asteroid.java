package io.thebitspud.astroagents.entities;

import io.thebitspud.astroagents.AstroAgents;

public class Asteroid extends Projectile {
	public Asteroid(int x, int y, int xVelocity, int yVelocity, AstroAgents app) {
		super(x, y, 25, 25, xVelocity, yVelocity, app);

		texture = app.assets.smallAsteroid;
		damage = 25;
		health = 25;
	}

	@Override
	public void tick(float delta) {
		x += xVelocity * delta;
		y += yVelocity * delta;

		if(x <= 0 || x >= AstroAgents.SCREEN_WIDTH - width) xVelocity *= -1;
		if(y <= 0 || y >= AstroAgents.SCREEN_HEIGHT - app.vsGameScreen.hudHeight - height) yVelocity *= -1;
	}
}
