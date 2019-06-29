package io.thebitspud.astroagents;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AstroAgents extends Game {
	private MainMenuScreen menuScreen;
	public VersusGameScreen vsScreen;

	public SpriteBatch batch;
	public BitmapFont font;

	@Override
	public void create() {
		menuScreen = new MainMenuScreen(this);
		vsScreen = new VersusGameScreen(this);

		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(menuScreen);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}