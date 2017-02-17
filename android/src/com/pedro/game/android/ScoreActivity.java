package com.pedro.game.android;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.pedro.game.android.utils.DataBase;
import com.pedro.game.utils.Score;

import java.util.ArrayList;

public class ScoreActivity extends Activity implements Button.OnClickListener {

  private Button bBack;
  private ListView lvScore;
  private MediaPlayer mp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_score);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    mp = MediaPlayer.create(getApplicationContext(), R.raw.maintheme);
    mp.setLooping(true);
    mp.setVolume(100, 100);
    mp.start();

    bBack = (Button) findViewById(R.id.bBack);
    bBack.setOnClickListener(this);
    lvScore = (ListView) findViewById(R.id.listScore);

    try {
      DataBase db = new DataBase(getApplicationContext());
      db.open();
      ArrayList<Score> listScore = db.getScores();
      db.close();
      String[] arrayScores;
      if (listScore.size() <= 10) {
        arrayScores = new String[listScore.size()];
        for (int i = 0; i < listScore.size(); i++) {
          arrayScores[i] =
              (i + 1) + ". " + listScore.get(i).getName() + ": " + listScore.get(i).getScore();
        }
      } else {
        arrayScores = new String[10];
        for (int i = 0; i < 10; i++) {
          arrayScores[i] = listScore.get(i).toString();
        }
      }
      ArrayAdapter<String> adapter =
          new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayScores);
      lvScore.setAdapter(adapter);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onBackPressed() {
    onPause();
    startActivity(new Intent(this, MenuActivity.class));
    finish();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bBack:
        onPause();
        startActivity(new Intent(this, MenuActivity.class));
        finish();
        break;
    }
  }

  public void onPause() {
    if (mp.isPlaying()) {
      mp.stop();
    }
    super.onPause();
  }
}
