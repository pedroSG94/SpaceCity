package com.pedro.game.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by alumno on 12/02/16.
 */
public class MakeAnimation {

  private Texture texture;
  private int columns, rows;

  public MakeAnimation(Texture texture, int columns, int rows) {
    this.texture = texture;
    this.columns = columns;
    this.rows = rows;
  }

  public Animation getAnimation() {
    TextureRegion[][] tmp =
        new TextureRegion(texture).split(texture.getWidth() / columns, texture.getHeight() / rows);

    TextureRegion[] frames = new TextureRegion[columns * rows];

    int cont = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        frames[cont] = tmp[i][j];
        cont++;
      }
    }
    return new Animation(0.02f, frames);
  }
}
