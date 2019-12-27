package io.thebitspud.astroagents.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.thebitspud.astroagents.AstroAgents;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.allowSoftwareMode = true;
		config.forceExit = true;
		config.resizable = false;
		config.fullscreen = true;
		config.width = AstroAgents.SCREEN_WIDTH;
		config.height = AstroAgents.SCREEN_HEIGHT;
		config.title = "Astro Agents v0.9";

		new LwjglApplication(new AstroAgents(), config);
	}
}
