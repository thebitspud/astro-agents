package io.thebitspud.astroagents.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.thebitspud.astroagents.AstroAgents;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.allowSoftwareMode = true;
		config.forceExit = true;
		config.width = 800;
		config.height = 600;
		config.title = "Astro Agents v0.3";

		new LwjglApplication(new AstroAgents(), config);
	}
}
