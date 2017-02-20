package com.pedro.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pedro.game.Main;
import com.pedro.game.actors.meteorite.MeteoriteActor;
import com.pedro.game.screens.GameScreen;

/**
 * Created by pedro on 10/02/16.
 */
public class BulletActor extends Actor {

  private Rectangle rBullet;
  private Sprite sBullet;
  private SpriteBatch sBatch;
  private float dispose, rotation;
  private float x, y;

  public BulletActor(float x, float y, float rotation, Main game) {
    this.rotation = rotation;

    dispose = 2.5f;
    rBullet = new Rectangle();
    rBullet.set(x, y, Gdx.graphics.getWidth() / 70, Gdx.graphics.getWidth() / 35);

    sBullet = new Sprite(game.getAssetManager().get("texture/bullet.png", Texture.class));
    sBullet.setBounds(rBullet.x, rBullet.y, rBullet.width, rBullet.height);
    sBullet.setOrigin(sBullet.getWidth() / 2, sBullet.getHeight() / 2);
    sBullet.rotate(90 + rotation);
    sBatch = new SpriteBatch();
    speed();
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);

    sBatch.begin();
    sBullet.draw(sBatch);
    sBatch.end();
  }

  @Override
  public void act(float delta) {
    super.act(delta);

    colision();

    rBullet.setPosition(rBullet.x - x, rBullet.y + y);
    sBullet.setPosition(rBullet.x, rBullet.y);
    dispose -= delta;
    if (dispose <= 0) {
      rBullet = null;
      GameScreen.reset = true;
      GameScreen.bulletRemove = this;
      this.remove();
    }
  }

  public void speed() {
    x = (Gdx.graphics.getWidth() / 60) - ((Gdx.graphics.getWidth() / 60) - (0.32f * rotation));
    y = (Gdx.graphics.getWidth() / 60);
  }

  public void colision() {
    for (int i = 0; i < GameScreen.stage.getActors().size - 1; i++) {
      if (GameScreen.stage.getActors().get(i) instanceof MeteoriteActor) {
        if (((MeteoriteActor) GameScreen.stage.getActors().get(i)).getrMeteorite()
            .overlaps(rBullet)) {
          GameScreen.bulletRemove = this;
          ((MeteoriteActor) GameScreen.stage.getActors().get(i)).removeLife();
          GameScreen.meteoriteRemove = ((MeteoriteActor) GameScreen.stage.getActors().get(i));
          GameScreen.listMeteorite.remove(GameScreen.stage.getActors().get(i));
          GameScreen.remove = true;
        }
      }
    }
  }

  public Rectangle getrBullet() {
    return rBullet;
  }
}
