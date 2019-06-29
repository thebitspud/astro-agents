package io.thebitspud.astroagent.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.thebitspud.astroagent.AstroAgent;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.allowSoftwareMode = true;
		config.forceExit = true;
		config.width = 800;
		config.height = 600;
		config.title = "Astro Agents v0.1";
		new LwjglApplication(new AstroAgent(), config);
	}
}
