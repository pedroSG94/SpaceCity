package com.pedro.game.utils;

import com.badlogic.gdx.Screen;
import com.pedro.game.Main;

/**
 * Created by pedro on 13/02/16.
 */
public abstract class BaseScreen implements Screen {

  protected Main game;

  public BaseScreen(Main game) {
    this.game = game;
  }
}
