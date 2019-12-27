package io.thebitspud.astroagents.entities;

import io.thebitspud.astroagents.AstroAgents;

public class Powerup extends Entity {
	private long timeCreated;
	private int despawnTime, id;
	public static final int DEFAULT_DESPAWN_TIME = 30;

	public Powerup(int x, int y, int id, AstroAgents app) {
		super(x, y, 25, 25, app);

		this.id = id;

		timeCreated = System.currentTimeMillis();
		despawnTime = DEFAULT_DESPAWN_TIME;

		switch (id) {
			case 0:
				texture = app.assets.repair;
				despawnTime = 120;
				break;
			case 1:
				texture = app.assets.boost;
				break;
			case 2:
				texture = app.assets.reload;
				break;
			case 3:
				texture = app.assets.velocity;
				break;
		}
	}

	@Override
	public void tick(float delta) {
		if(System.currentTimeMillis() > timeCreated + 1000 * despawnTime) {
			active = false;
		}
	}

	public void applyEffects(Player target) {
		switch (id) {
			case 0:
				target.adjustHealth(25);
				break;
			case 1:
				target.adjustSpeed(15);
				break;
			case 2:
				target.adjustReloadSpeed(0.9);
				break;
			case 3:
				target.adjustMissileVelocity(100);
				break;
		}
	}
}