package io.thebitspud.astroagents.entities;

import io.thebitspud.astroagents.AstroAgents;

public class Rocket extends Projectile {
	public Rocket(int x, int y, int xVelocity, int yVelocity, AstroAgents app) {
		super(x, y, 13, 4, xVelocity, yVelocity, app);

		if(xVelocity > 0) texture = app.assets.rocket1;
		else texture = app.assets.rocket2;

		damage = 5;
	}
}
