package com.pedro.game.actors.meteorite;

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
import com.pedro.game.actors.CityActor;
import com.pedro.game.screens.GameScreen;
import com.pedro.game.utils.MakeAnimation;

/**
 * Created by pedro on 20/02/17.
 */

public class MeteoriteActorFast extends MeteoriteActor {

  public MeteoriteActorFast(CityActor city, Main game) {
    super(city, Gdx.graphics.getWidth() / 9, Gdx.graphics.getWidth() / 9);
    life = 1;
    speed = (Gdx.graphics.getWidth() / 120);
    animationAsteroid =
        new MakeAnimation(game.getAssetManager().get("texture/asteroidFast.png", Texture.class), 8,
            7).getAnimation();
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
  }

  @Override
  public void act(float delta) {
    super.act(delta);
  }
}
