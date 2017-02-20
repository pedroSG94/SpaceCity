package com.pedro.game.actors.meteorite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pedro.game.actors.CityActor;
import com.pedro.game.screens.GameScreen;

/**
 * Created by pedro on 20/02/17.
 */
public abstract class MeteoriteActor extends Actor {

  //shared with all meteorites
  protected int life, speed;
  protected Animation animationAsteroid;

  private float state;
  private Rectangle rMeteorite;
  private Rectangle rCity;
  private SpriteBatch sBatch;
  private CityActor city;

  public MeteoriteActor(CityActor city, int width, int height) {
    this.rCity = city.getRectangleCity();
    this.city = city;
    state = 0;
    rMeteorite = new Rectangle();
    rMeteorite.set(MathUtils.random(0, Gdx.graphics.getWidth() - width), Gdx.graphics.getHeight(),
        width, height);
    sBatch = new SpriteBatch();
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
    collisionWithCity();
  }

  public void removeLife() {
    life--;
  }

  public void kill() {
    life = 0;
  }

  public void collisionWithCity() {
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

