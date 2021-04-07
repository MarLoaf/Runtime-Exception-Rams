package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Tutor;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "RAM Tutor";
		config.width = 1920;
		//config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width; TODO requires larger background image
		config.height = 1080;
		//config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height; TODO requires larger background image
		config.fullscreen = true;
		new LwjglApplication(new Tutor(), config);
	}
}
