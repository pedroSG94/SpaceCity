package com.pedro.game.utils;

import java.util.ArrayList;

/**
 * Created by pedro on 14/02/16.
 */
public interface ChangeToAndroid {
  void startActivity();

  void insertScore(int score, String name);

  ArrayList<Score> getScore();
}
