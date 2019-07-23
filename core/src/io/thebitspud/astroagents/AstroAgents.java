package io.thebitspud.astroagents;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class AstroAgents extends Game {
	VersusGameScreen vsGameScreen;
	VersusOverScreen vsOverScreen;

	SpriteBatch batch;
	BitmapFont font;

	ArrayList<Controller> gamepads;

	@Override
	public void create() {
		MainMenuScreen menuScreen = new MainMenuScreen(this);
		vsGameScreen = new VersusGameScreen(this);
		vsOverScreen = new VersusOverScreen(this);

		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(menuScreen);

		gamepads = new ArrayList<Controller>();

		for (Controller controller: Controllers.getControllers()) {
			gamepads.add(controller);
		}
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