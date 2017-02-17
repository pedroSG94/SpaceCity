package com.pedro.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.pedro.game.screens.GameScreen;
import com.pedro.game.screens.LoadingScreen;
import com.pedro.game.utils.BaseScreen;

public class Main extends Game {

  private AssetManager am;
  public BaseScreen gameScreen;
  public com.pedro.game.utils.ChangeToAndroid changeToAndroid;

  public Main(com.pedro.game.utils.ChangeToAndroid changeToAndroid) {
    super();
    this.changeToAndroid = changeToAndroid;
  }

  @Override
  public void create() {

		/*Need move to other class*/
    am = new AssetManager();
    am.load("texture/asteroidNormal.png", Texture.class);
    am.load("texture/asteroidFast.png", Texture.class);
    am.load("texture/asteroidSlow.png", Texture.class);

    am.load("texture/hp.png", Texture.class);
    am.load("texture/city.png", Texture.class);
    am.load("texture/bullet.png", Texture.class);
    am.load("texture/explosionCity.png", Texture.class);
    am.load("texture/explosionBullet.png", Texture.class);
    am.load("texture/turrets.png", Texture.class);
    am.load("texture/laser.png", Texture.class);

    am.load("effects/explosionSoundCity.mp3", Sound.class);
    am.load("effects/laserShoot.mp3", Sound.class);
    am.load("effects/meteoriteDeathSound.mp3", Sound.class);
    am.load("effects/warpout_2.mp3", Sound.class);

    am.load("musics/gamePlayMusic.mp3", Music.class);
    am.load("musics/gameOverMusic.mp3", Music.class);

    setScreen(new LoadingScreen(this));
  }

  public void finishLoading() {
    gameScreen = new GameScreen(this);
    setScreen(gameScreen);
  }

  public AssetManager getAssetManager() {
    return am;
  }
}
