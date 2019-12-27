package io.thebitspud.astroagents.entities;

import com.badlogic.gdx.math.Rectangle;
import io.thebitspud.astroagents.AstroAgents;

public class LargeAsteroid extends Projectile {
	public LargeAsteroid(int x, int y, int xVelocity, int yVelocity, AstroAgents app) {
		super(x, y, 50, 50, xVelocity, yVelocity, app);

		texture = app.assets.largeAsteroid;
		damage = 100;
		health = 75;
	}

	@Override
	public void tick(float delta) {
		x += xVelocity * delta;
		y += yVelocity * delta;

		if(x <= 0 || x >= AstroAgents.SCREEN_WIDTH - width) xVelocity *= -1;
		if(y <= 0 || y >= AstroAgents.SCREEN_HEIGHT - app.vsGameScreen.hudHeight - height) yVelocity *= -1;
	}

	public boolean circleContains(int xPos, int yPos) {
		int xDiff = (int) (xPos - (x + width / 2));
		int yDiff = (int) (yPos - (y + height / 2));
		int squaredDistance = (int) (Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
		int rSquared = (int) Math.pow(width / 2, 2);

		return Math.abs(squaredDistance) < rSquared;
	}

	public boolean overlaps(Rectangle r) {
		if(circleContains((int) r.x, (int) r.y)) return true;
		if(circleContains((int) (r.x + r.width), (int) r.y)) return true;
		if(circleContains((int) r.x, (int) (r.y + r.height))) return true;
		return circleContains((int) (r.x + r.width), (int) (r.y + r.height));
	}
}