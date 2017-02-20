package com.pedro.game.actors.meteorite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.pedro.game.Main;
import com.pedro.game.actors.CityActor;
import com.pedro.game.utils.MakeAnimation;

/**
 * Created by pedro on 20/02/17.
 */

public class MeteoriteActorSlow extends MeteoriteActor{

  public MeteoriteActorSlow(CityActor city, Main game) {
    super(city, Gdx.graphics.getWidth() / 7, Gdx.graphics.getWidth() / 7);
    life = 5;
    speed = (Gdx.graphics.getWidth() / 360);
    animationAsteroid =
        new MakeAnimation(game.getAssetManager().get("texture/asteroidSlow.png", Texture.class),
            5, 4).getAnimation();
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
