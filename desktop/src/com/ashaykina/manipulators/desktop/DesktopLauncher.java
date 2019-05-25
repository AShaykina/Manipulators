package com.ashaykina.manipulators.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ashaykina.manipulators.Manipulators;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 750;
		config.height = 750;
		config.vSyncEnabled = false;
		new LwjglApplication(new Manipulators((short) 750), config);
	}
}
