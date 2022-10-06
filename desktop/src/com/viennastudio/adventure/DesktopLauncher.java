package com.viennastudio.adventure;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.viennastudio.adventure.util.Constants;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		config.setForegroundFPS(60);
		config.setTitle("AdvenTUre");
		AdvenTUreGame game = new AdvenTUreGame();
		game.setSplashWorker(new DesktopSplashWorker());
		new Lwjgl3Application(game, config);
	}
}
