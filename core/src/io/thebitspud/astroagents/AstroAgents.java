package io.thebitspud.astroagents;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.thebitspud.astroagents.screens.MainMenuScreen;
import io.thebitspud.astroagents.screens.VersusGameScreen;
import io.thebitspud.astroagents.screens.VersusOverScreen;

import java.awt.Toolkit;
import java.util.ArrayList;

public class AstroAgents extends Game {
	public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width,
			SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

	public VersusGameScreen vsGameScreen;
	public VersusOverScreen vsOverScreen;

	public SpriteBatch batch;
	public BitmapFont font;
	public AssetLibrary assets;

	public ArrayList<Controller> gamepads;

	@Override
	public void create() {
		MainMenuScreen menuScreen = new MainMenuScreen(this);
		vsGameScreen = new VersusGameScreen(this);
		vsOverScreen = new VersusOverScreen(this);

		batch = new SpriteBatch();
		font = new BitmapFont();
		assets = new AssetLibrary();

		this.setScreen(menuScreen);

		gamepads = new ArrayList<Controller>();

		checkForGamepads();
	}

	public void checkForGamepads() {
		gamepads.clear();
		for (Controller controller: Controllers.getControllers()) {
			gamepads.add(controller);
		}
	}

	public void drawCenteredText(String text, int x, int y) {
		font.draw(batch, text, SCREEN_WIDTH / 2 + x, SCREEN_HEIGHT / 2 + y, 0, 1, false);
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