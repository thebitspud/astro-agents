package io.thebitspud.astroagents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLibrary {
	public Texture ships, missiles, asteroids;
	public TextureRegion ship1, ship2, rocket1, rocket2, seeker1, seeker2, smallAsteroid;

	public AssetLibrary() {
		ships = new Texture("player.png");
		ship1 = new TextureRegion(ships, 0, 0, 31, 31);
		ship2 = new TextureRegion(ships, 31, 0, 31, 31);

		missiles = new Texture("missile.png");
		rocket1 = new TextureRegion(missiles, 0, 0, 13, 4);
		rocket2 = new TextureRegion(missiles, 13, 0, 13, 4);
		seeker1 = new TextureRegion(missiles, 0, 4, 26, 5);
		seeker2 = new TextureRegion(missiles, 0, 9, 26, 5);

		asteroids = new Texture("asteroid.png");
		smallAsteroid = new TextureRegion(asteroids, 0, 0, 25, 25);
	}

	public void dispose() {
		ships.dispose();
		missiles.dispose();
		asteroids.dispose();
	}
}
