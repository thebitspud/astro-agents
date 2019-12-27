package io.thebitspud.astroagents.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import io.thebitspud.astroagents.AstroAgents;

public abstract class Entity extends Rectangle {
	protected AstroAgents app;
	public TextureRegion texture;
	protected int health;
	public static final int DEFAULT_HEALTH = 10;
	public boolean active;

	public Entity(int x, int y, int width, int height, AstroAgents app) {
		this.set(x, y, width, height);

		this.app = app;
		health = DEFAULT_HEALTH;
		active = true;
	}

	public abstract void tick(float delta);

	public void render() {
		app.batch.draw(texture, x, y, width, height);
	}

	public int getHealth() {
		return health;
	}

	public void adjustHealth(int value) {
		health += value;
	}

	public boolean getActive() {
		return active;
	}
}