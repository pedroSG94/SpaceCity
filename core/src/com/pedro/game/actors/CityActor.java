package com.pedro.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pedro.game.Main;
import com.pedro.game.screens.GameScreen;
import com.pedro.game.utils.MakeAnimation;

/**
 * Created by pedro on 10/02/16.
 */
public class CityActor extends Actor {

  private int life;

  private Sound bulletShoot, laserShoot;
  private Sprite sCanon, sCity;
  private SpriteBatch sBatch;
  private Rectangle rCity;
  private Stage stage;
  private float spawnBullet;
  public static float rotation;
  private LaserActor la;

  private Main game;
  private int contador;
  private Animation animationLaser;
  private float state;
  private Sprite sLaser;

  private boolean laserAlive = false;

  public CityActor(Stage stage, Main game) {
    this.stage = stage;
    this.game = game;

    state = 0;
    contador = 0;
    life = 5;
    spawnBullet = 0f;

    rCity = new Rectangle();
    rCity.setPosition(0, 0);
    rCity.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 30);

    sBatch = new SpriteBatch();
    sCanon = new Sprite(game.getAssetManager().get("texture/turrets.png", Texture.class));
    sCity = new Sprite(game.getAssetManager().get("texture/city.png", Texture.class));

    sCity.setBounds(rCity.x, rCity.y + 280, rCity.width, rCity.height);
    sCity.setScale(3);
    sCanon.setBounds((Gdx.graphics.getWidth() / 2) - (Gdx.graphics.getWidth() / 36),
        Gdx.graphics.getWidth() / 9, Gdx.graphics.getWidth() / 18, Gdx.graphics.getWidth() / 9);
    sCanon.setOrigin(sCanon.getWidth() / 2, sCanon.getHeight() / 2);

    bulletShoot = game.getAssetManager().get("effects/laserShoot.mp3");
    laserShoot = game.getAssetManager().get("effects/warpout_2.mp3");

    animationLaser =
        new MakeAnimation(game.getAssetManager().get("texture/laser.png", Texture.class), 6,
            1).getAnimation();
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    rotation = sCanon.getRotation();
    if (contador != 0) {
      state += Gdx.graphics.getDeltaTime();
      sLaser = new Sprite(animationLaser.getKeyFrame(state, true));
      sLaser.setBounds(LaserActor.pLaser.getX(), LaserActor.pLaser.getY(),
          (Gdx.graphics.getWidth() / 18) * 2, Gdx.graphics.getHeight());
      sLaser.setOrigin(LaserActor.pLaser.getOriginX(), LaserActor.pLaser.getOriginY());
      sLaser.rotate(LaserActor.pLaser.getRotation());
    }
    sBatch.begin();
    sCity.draw(sBatch);
    if (contador != 0 && laserAlive) sLaser.draw(sBatch);
    sCanon.draw(sBatch);
    sBatch.end();
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    rotateCannon();
    if (life <= 0) GameScreen.changeScreen = true;
    if (spawnBullet <= 0) {
      shoot();
    } else {
      spawnBullet -= delta;
    }
  }

  public void shoot() {
    if (Gdx.input.isTouched(0) && !Gdx.input.isTouched(1)) {
      bulletShoot.play(0.4f);
      BulletActor bullet1 =
          new BulletActor(sCanon.getX() - 5, sCanon.getY() + sCanon.getHeight() / 2,
              sCanon.getRotation(), game);
      stage.addActor(bullet1);

      BulletActor bullet = new BulletActor(sCanon.getX() + sCanon.getWidth() - 5,
          sCanon.getY() + sCanon.getHeight() / 2, sCanon.getRotation(), game);
      stage.addActor(bullet);
      spawnBullet = 0.2f;

      if (contador != 0) {
        la.remove();
        laserAlive = false;
        laserShoot.stop();
      }

      contador = 0;
      if (GameScreen.power.getWidth() < Gdx.graphics.getWidth() / 20 * 5) {
        GameScreen.power.setSize(GameScreen.power.getWidth() + Gdx.graphics.getWidth() / 600,
            GameScreen.power.getHeight());
      }
    } else if (Gdx.input.isTouched(1)) {
      if (contador == 0 && GameScreen.power.getWidth() > Gdx.graphics.getWidth() / 20) {
        laserShoot.play(2.5f);
        la = new LaserActor(CityActor.rotation);
        laserAlive = true;
        GameScreen.stage.addActor(la);
        GameScreen.power.setSize(GameScreen.power.getWidth() - Gdx.graphics.getWidth() / 20,
            GameScreen.power.getHeight());
      }
      if (GameScreen.power.getWidth() > 0 && laserAlive) {
        GameScreen.power.setSize(GameScreen.power.getWidth() - Gdx.graphics.getWidth() / 500,
            GameScreen.power.getHeight());
      }
      contador++;
      if (GameScreen.power.getWidth() <= 0) {
        la.remove();
        laserAlive = false;
        laserShoot.stop();
      }
    } else {
      if (contador != 0) {
        la.remove();
        laserAlive = false;
        laserShoot.stop();
      }
      contador = 0;
      if (GameScreen.power.getWidth() < Gdx.graphics.getWidth() / 20 * 5) {
        GameScreen.power.setSize(GameScreen.power.getWidth() + Gdx.graphics.getWidth() / 600,
            GameScreen.power.getHeight());
      }
    }
  }

  public void rotateCannon() {
    if (Gdx.input.getRoll() > 8 && sCanon.getRotation() > -60f) {
      sCanon.rotate(-2);
    } else if (Gdx.input.getRoll() < -8 && sCanon.getRotation() < 60f) {
      sCanon.rotate(2);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      sCanon.rotate(-2);
    } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
      sCanon.rotate(2);
    }
  }

  public Rectangle getRectangleCity() {
    return rCity;
  }

  public void removeLife() {
    life--;
  }

  public int getLife() {
    return life;
  }
}
