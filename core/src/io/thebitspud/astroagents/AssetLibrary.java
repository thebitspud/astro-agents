package io.thebitspud.astroagents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLibrary {
	public Texture ships, missiles, asteroids, powerups, explosions;
	public TextureRegion ship1, ship2, rocket1, rocket2, seeker1, seeker2, smallAsteroid, largeAsteroid,
			repair, boost, reload, velocity;
	public TextureRegion[] smallExplosion, largeExplosion;

	public AssetLibrary() {
		ships = new Texture("ships.png");
		ship1 = new TextureRegion(ships, 0, 0, 31, 31);
		ship2 = new TextureRegion(ships, 31, 0, 31, 31);

		missiles = new Texture("missiles.png");
		rocket1 = new TextureRegion(missiles, 0, 0, 13, 4);
		rocket2 = new TextureRegion(missiles, 13, 0, 13, 4);
		seeker1 = new TextureRegion(missiles, 0, 4, 26, 5);
		seeker2 = new TextureRegion(missiles, 0, 9, 26, 5);

		asteroids = new Texture("asteroids.png");
		smallAsteroid = new TextureRegion(asteroids, 0, 0, 25, 25);
		largeAsteroid = new TextureRegion(asteroids, 0, 25, 50, 50);

		powerups = new Texture("powerups.png");
		repair = new TextureRegion(powerups, 0, 0, 25, 25);
		boost = new TextureRegion(powerups, 25, 0, 25, 25);
		reload = new TextureRegion(powerups, 50, 0, 25, 25);
		velocity = new TextureRegion(powerups, 75, 0, 25, 25);

		explosions = new Texture("explosions.png");
		smallExplosion = new TextureRegion[8];
		for(int i = 0; i < 8; i++) {
			smallExplosion[i] = new TextureRegion(explosions, i * 16, 0, 16, 16);
		}

		largeExplosion = new TextureRegion[12];
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 3; y++) {
				largeExplosion[y * 4 + x] = new TextureRegion(explosions, x * 32, y * 32 + 16, 32, 32);
			}
		}
	}

	public void dispose() {
		ships.dispose();
		missiles.dispose();
		asteroids.dispose();
	}
}
