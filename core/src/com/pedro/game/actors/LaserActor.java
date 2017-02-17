package com.pedro.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pedro.game.screens.GameScreen;

/**
 * Created by alumno on 24/02/16.
 */
public class LaserActor extends Actor {

  public static Polygon pLaser;

  public LaserActor(float rotation) {
    Rectangle rLaser = new Rectangle((Gdx.graphics.getWidth() / 2)
        - (Gdx.graphics.getWidth() / 36)
        - Gdx.graphics.getWidth() / 36, Gdx.graphics.getWidth() / 9 + Gdx.graphics.getWidth() / 9,
        Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight());
    pLaser = new Polygon(new float[] {
        0, 0, rLaser.width * 2, 0, rLaser.width * 2, rLaser.height, 0, rLaser.height
    });
    pLaser.setPosition(rLaser.x, rLaser.y - Gdx.graphics.getWidth() / 9);
    pLaser.setOrigin(20, 40);
    pLaser.setRotation(rotation);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {

  }

  @Override
  public void act(float delta) {
    collision();
    rotateLaser();
    super.act(delta);
  }

  private void collision() {
    for (int i = 0; i < GameScreen.stage.getActors().size - 1; i++) {
      if (GameScreen.stage.getActors().get(i) instanceof MeteoriteActor) {
        if (collisionPolygonAndRectangle(pLaser,
            ((MeteoriteActor) GameScreen.stage.getActors().get(i)).getrMeteorite())) {
          GameScreen.laserRemove = this;
          ((MeteoriteActor) GameScreen.stage.getActors().get(i)).kill();
          GameScreen.meteoriteRemove = ((MeteoriteActor) GameScreen.stage.getActors().get(i));
          GameScreen.listMeteorite.remove(GameScreen.stage.getActors().get(i));
          GameScreen.remove = true;
        }
      }
    }
  }

  private void rotateLaser() {
    pLaser.setRotation(CityActor.rotation);
  }

  private boolean collisionPolygonAndRectangle(Polygon p, Rectangle r) {
    Polygon rPoly = new Polygon(new float[] { 0, 0, r.width, r.height, 0, r.height });
    rPoly.setPosition(r.x, r.y);
    return Intersector.overlapConvexPolygons(p, rPoly);
  }
}
