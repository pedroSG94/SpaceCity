package com.pedro.game.android;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.pedro.game.Main;
import com.pedro.game.android.utils.DataBase;
import com.pedro.game.utils.ChangeToAndroid;
import com.pedro.game.utils.Score;

import java.util.ArrayList;

public class AndroidLauncher extends AndroidApplication implements ChangeToAndroid {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
    initialize(new Main(this), config);
  }

  @Override
  public void startActivity() {
    startActivity(new Intent(this, MenuActivity.class));
  }

  @Override
  public void insertScore(int score, String name) {

    try {
      DataBase db = new DataBase(getApplicationContext());
      db.open();
      db.insertScore(score, name);
      db.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public ArrayList<Score> getScore() {
    ArrayList<Score> listScores;
    try {
      DataBase db = new DataBase(getApplicationContext());
      db.open();
      listScores = db.getScores();
      db.close();
      return listScores;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }
}
