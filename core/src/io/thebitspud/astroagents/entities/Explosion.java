package io.thebitspud.astroagents.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.thebitspud.astroagents.AstroAgents;

public class Explosion extends Projectile {
	private long lastAnimFrame;
	private TextureRegion[] animTextures;
	private int frame;

	public Explosion(int x, int y, boolean large, AstroAgents app) {
		super(x, y, 16, 16, 0, 0, app);

		frame = 0;

		if(large) {
			width = 32;
			height = 32;
			animTextures = app.assets.largeExplosion;
			damage = 10;
		} else {
			animTextures = app.assets.smallExplosion;
			damage = 5;
		}

		texture = animTextures[frame];

		lastAnimFrame = System.currentTimeMillis();
	}

	@Override
	public void tick(float delta) {
		if(System.currentTimeMillis() >= lastAnimFrame + 30) {
			lastAnimFrame = System.currentTimeMillis();
			frame++;

			if(animTextures.length > frame) texture = animTextures[frame];
			else active = false;
		}
	}
}