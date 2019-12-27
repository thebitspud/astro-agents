package io.thebitspud.astroagents.entities;

import io.thebitspud.astroagents.AstroAgents;

public class Powerup extends Entity {
	private long timeCreated;

	public Powerup(int x, int y, AstroAgents app) {
		super(x, y, 25, 25, app);

		timeCreated = System.currentTimeMillis();
	}

	@Override
	public void tick(float delta) {
		if(System.currentTimeMillis() > timeCreated + 30000) {

		}
	}
}
