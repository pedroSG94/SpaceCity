package com.pedro.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pedro.game.Main;
import com.pedro.game.screens.GameScreen;
import com.pedro.game.utils.MakeAnimation;

/**
 * Created by pedro on 10/02/16.
 */
public class MeteoriteActor extends Actor {

  private int life, speed;

  private float state;
  private Animation animationAsteroid;
  private Rectangle rMeteorite;
  private Rectangle rCity;
  private SpriteBatch sBatch;
  private CityActor city;

  public MeteoriteActor(CityActor city, int width, int height, Main game) {
    this.rCity = city.getRectangleCity();
    this.city = city;

    state = 0;
    rMeteorite = new Rectangle();
    rMeteorite.set(MathUtils.random(0, Gdx.graphics.getWidth() - width), Gdx.graphics.getHeight(),
        width, height);
    sBatch = new SpriteBatch();

    if (width == Gdx.graphics.getWidth() / 15) {
      life = 2;
      speed = (Gdx.graphics.getWidth() / 180);
      animationAsteroid =
          new MakeAnimation(game.getAssetManager().get("texture/asteroidNormal.png", Texture.class),
              8, 8).getAnimation();
    } else if (width == Gdx.graphics.getWidth() / 9) {
      life = 1;
      speed = (Gdx.graphics.getWidth() / 120);
      animationAsteroid =
          new MakeAnimation(game.getAssetManager().get("texture/asteroidFast.png", Texture.class),
              8, 7).getAnimation();
    } else if (width == Gdx.graphics.getWidth() / 7) {
      life = 5;
      speed = (Gdx.graphics.getWidth() / 360);
      animationAsteroid =
          new MakeAnimation(game.getAssetManager().get("texture/asteroidSlow.png", Texture.class),
              5, 4).getAnimation();
    }
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);

    state += Gdx.graphics.getDeltaTime();

    Sprite asteroid = new Sprite(animationAsteroid.getKeyFrame(state, true));
    asteroid.setBounds(rMeteorite.x, rMeteorite.y, rMeteorite.width, rMeteorite.height);
    sBatch.begin();
    asteroid.draw(sBatch);
    sBatch.end();
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    colisionWithCity();
  }

  public void removeLife() {
    life--;
  }

  public void kill() {
    life = 0;
  }

  public void colisionWithCity() {
    if (rMeteorite.overlaps(rCity)) {
      GameScreen.listMeteorite.remove(this);
      GameScreen.positionMeteorite = new Vector2(rMeteorite.x, rMeteorite.y);
      GameScreen.meteoriteSize = new Vector2(rMeteorite.width, rMeteorite.height);
      GameScreen.controlExplosionCity = 0;
      city.removeLife();
      this.remove();
    } else {
      rMeteorite.setPosition(rMeteorite.x, rMeteorite.y - speed);
    }
  }

  public Rectangle getrMeteorite() {
    return rMeteorite;
  }

  public int getLife() {
    return life;
  }
}
