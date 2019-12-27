package io.thebitspud.astroagents.entities;

import com.badlogic.gdx.math.Rectangle;
import io.thebitspud.astroagents.AstroAgents;
import io.thebitspud.astroagents.VersusGame;

public class Seeker extends Projectile {
	private int maxYVelocity, xAcceleration, yAcceleration;
	private Rectangle target;

	public Seeker(int x, int y, int xVelocity, int yVelocity, float accelConst, AstroAgents app) {
		super(x, y, 26, 5, xVelocity, yVelocity, app);

		VersusGame game = app.vsGameScreen.game;

		maxYVelocity = (int) (200 * Math.sqrt(accelConst));
		xAcceleration = (int) (10 * accelConst);
		yAcceleration = (int) (15 * accelConst);

		if(xVelocity > 0) {
			texture = app.assets.seeker1;
			target = game.player2;
		} else {
			texture = app.assets.seeker2;
			target = game.player1;
		}

		damage = 20;
	}

	@Override
	public void tick(float delta) {
		if(xVelocity > 0) xVelocity += xAcceleration;
		else xVelocity -= xAcceleration;

		if (Math.abs(y - (target.y + 15)) < maxYVelocity * delta) {
			if(yVelocity > 0) yVelocity -= yAcceleration;
			else if(yVelocity < 0) yVelocity += yAcceleration;
		} else if(y < (target.y + 15)) yVelocity += yAcceleration;
		else yVelocity -= yAcceleration;

		if(yVelocity > maxYVelocity) yVelocity = maxYVelocity;
		if(yVelocity < -maxYVelocity) yVelocity = -maxYVelocity;

		x += xVelocity * delta;
		y += yVelocity * delta;
	}
}