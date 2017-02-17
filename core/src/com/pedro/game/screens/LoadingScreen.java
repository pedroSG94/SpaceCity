package com.pedro.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pedro.game.Main;
import com.pedro.game.utils.BaseScreen;

/**
 * Created by pedro on 14/02/16.
 */
public class LoadingScreen extends BaseScreen {

  private Main game;
  private Label lbLoad;
  private Stage stage;

  public LoadingScreen(Main game) {
    super(game);
    this.game = game;
  }

  @Override
  public void show() {
    stage = new Stage();
    stage.setViewport(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    lbLoad = new Label("Loading...", new Skin(Gdx.files.internal("skin/uiskin.json")));
    lbLoad.setPosition(0, 0);
    stage.addActor(lbLoad);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    if (game.getAssetManager().update()) {
      game.finishLoading();
    } else {
      int progress = (int) (game.getAssetManager().getProgress() * 100);
      lbLoad.setText("Loading..." + progress + "%");
    }
    stage.act();
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height);
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {
    stage.dispose();
  }
}
