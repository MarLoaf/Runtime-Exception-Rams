package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Tutor;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "RAM Tutor";
		config.width = 1920;
		//config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
		config.height = 1080;
		//config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		config.fullscreen = false;
		new LwjglApplication(new Tutor(), config);
	}
}
