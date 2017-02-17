package com.pedro.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pedro.game.utils.ChangeToAndroid;
import com.pedro.game.Main;
import com.pedro.game.utils.Score;

import java.util.ArrayList;

public class DesktopLauncher{
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 720;
		config.height = 1280;
		new LwjglApplication(new Main(new ChangeToAndroid() {
			@Override
			public void startActivity() {

			}

			@Override
			public void insertScore(int score, String name) {

			}

			@Override
			public ArrayList<Score> getScore() {
				return null;
			}
		}));
	}
}
