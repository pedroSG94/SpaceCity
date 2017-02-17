package com.pedro.game.android;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreditsActivity extends Activity implements Button.OnClickListener {

  private MediaPlayer mp;
  private Button bBack;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_credits);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    mp = MediaPlayer.create(getApplicationContext(), R.raw.maintheme);
    mp.setLooping(true);
    mp.setVolume(100, 100);
    mp.start();

    bBack = (Button) findViewById(R.id.bBack2);
    bBack.setOnClickListener(this);
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
      case R.id.bBack2:
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
