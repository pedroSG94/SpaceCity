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

public class MeteoriteActorNormal extends MeteoriteActor {

  public MeteoriteActorNormal(CityActor city, Main game) {
    super(city, Gdx.graphics.getWidth() / 15, Gdx.graphics.getWidth() / 15);
    life = 2;
    speed = (Gdx.graphics.getWidth() / 180);
    animationAsteroid =
        new MakeAnimation(game.getAssetManager().get("texture/asteroidNormal.png", Texture.class),
            8, 8).getAnimation();
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
