package io.thebitspud.astroagents.entities;

import io.thebitspud.astroagents.AstroAgents;

public abstract class Projectile extends Entity {
	protected int xVelocity, yVelocity, damage;

	public Projectile(int x, int y, int width, int height, int xVelocity, int yVelocity, AstroAgents app) {
		super(x, y, width, height, app);

		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
	}

	@Override
	public void tick(float delta) {
		x += xVelocity * delta;
		y += yVelocity * delta;
	}

	public int getDamage() {
		return damage;
	}
}