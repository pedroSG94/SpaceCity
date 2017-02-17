package com.pedro.game.android;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity implements Button.OnClickListener {

  private Button bStart, bScores, bCredits, bExit;
  private MediaPlayer mp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    mp = MediaPlayer.create(getApplicationContext(), R.raw.maintheme);
    mp.setLooping(true);
    mp.setVolume(100, 100);
    mp.start();

    bStart = (Button) findViewById(R.id.bStart);
    bScores = (Button) findViewById(R.id.bScores);
    bCredits = (Button) findViewById(R.id.bCredits);
    bExit = (Button) findViewById(R.id.bExit);

    bStart.setOnClickListener(this);
    bScores.setOnClickListener(this);
    bCredits.setOnClickListener(this);
    bExit.setOnClickListener(this);
  }

  @Override
  public void onBackPressed() {
    finish();
    System.exit(0);
    System.runFinalization();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bStart:
        onPause();
        finish();
        startActivity(new Intent(this, AndroidLauncher.class));
        break;
      case R.id.bScores:
        onPause();
        startActivity(new Intent(this, ScoreActivity.class));
        finish();
        break;
      case R.id.bCredits:
        onPause();
        startActivity(new Intent(this, CreditsActivity.class));
        finish();
      case R.id.bExit:
        finish();
        System.exit(0);
        System.runFinalization();
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
