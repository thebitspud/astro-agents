package io.thebitspud.astroagents.entities;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.utils.TimeUtils;
import io.thebitspud.astroagents.AstroAgents;

import java.util.ArrayList;
import java.util.Iterator;

public class Player extends Entity {
	private int id, dir, speed, reloadSpeed, missileVelocity;
	private Player target;
	public ArrayList<Projectile> missiles;
	private Controller gamepad;
	private long lastShot, lastSeeker;
	private final int playerYPos = (AstroAgents.SCREEN_HEIGHT / 2) - 16;

	public Player(int x, int y, int id, AstroAgents app) {
		super(x, y, 31, 31, app);

		missiles = new ArrayList<Projectile>();
		this.id = id;

		if(app.gamepads.size() > id - 1) gamepad = app.gamepads.get(id - 1);

		if(id == 1) {
			texture = app.assets.ship1;
			dir = 1;
		} else if(id == 2) {
			texture = app.assets.ship2;
			dir = -1;
		}
	}

	public void init() {
		if(id == 1) {
			setPosition(20, playerYPos);
			target = app.vsGameScreen.game.player2;
		} else {
			setPosition(AstroAgents.SCREEN_WIDTH - 51, playerYPos);
			target = app.vsGameScreen.game.player1;
		}

		speed = 150;
		reloadSpeed = 500;
		missileVelocity = 500;

		missiles.clear();
		health = 100;
	}

	@Override
	public void tick(float delta) {
		if(health <= 0) {
			if(id == 1) app.vsOverScreen.gameOverText = "Player 2 Wins";
			else app.vsOverScreen.gameOverText = "Player 1 Wins";

			app.setScreen(app.vsOverScreen);
			return;
		}

		getInput(delta);
		tickMissiles(delta);
	}

	private void getInput(float delta) {
		if(gamepad == null) return;

		if (gamepad.getButton(1) && (TimeUtils.nanoTime() - lastShot) / 1000000 > reloadSpeed) {
			missiles.add(new Rocket((int) x + 9, (int) y  + 4, missileVelocity * dir, 0, app));
			missiles.add(new Rocket((int) x + 9, (int) y  + 23, missileVelocity * dir, 0, app));
			lastShot = TimeUtils.nanoTime();
		}

		if (gamepad.getButton(0) && (TimeUtils.nanoTime() - lastSeeker) / 1000000 > reloadSpeed * 4) {
			missiles.add(new Seeker((int) x + 4, (int) y  + 13, dir, 0, missileVelocity / 500, app));
			lastSeeker = TimeUtils.nanoTime();
		}

		if (gamepad.getAxis(0) == -1) y += speed * delta;
		else if (gamepad.getAxis(0) == 1) y -= speed * delta;
		if (gamepad.getAxis(1) == -1) x -= speed * delta;
		else if (gamepad.getAxis(1) == 1) x += speed * delta;

		if (x < 0)x = 0;
		else if (x > AstroAgents.SCREEN_WIDTH - width) x = AstroAgents.SCREEN_WIDTH - width;
		if (y < 0) y = 0;
		else if (y > AstroAgents.SCREEN_HEIGHT - height - app.vsGameScreen.hudHeight)
				y = AstroAgents.SCREEN_HEIGHT - height - app.vsGameScreen.hudHeight;
	}

	private void tickMissiles(float delta) {
		for (Iterator<Projectile> iter = missiles.iterator(); iter.hasNext(); ) {
			Projectile missile = iter.next();
			missile.tick(delta);

			if (missile.x > AstroAgents.SCREEN_WIDTH || missile.x < -16) iter.remove();
			if (missile.overlaps(target)) {
				target.adjustHealth(-missile.getDamage());
				iter.remove();
			}
		}
	}

	@Override
	public void render() {
		app.batch.draw(texture, x, y, width, height);

		for(Projectile missile: missiles) missile.render();
	}

	public void adjustSpeed(int value) {
		speed += value;
	}

	public void adjustReloadSpeed(double value) {
		reloadSpeed *= value;
	}

	public void adjustMissileVelocity(int value) {
		missileVelocity += value;
	}
}
