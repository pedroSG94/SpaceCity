package com.pedro.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pedro.game.Main;
import com.pedro.game.utils.BaseScreen;

/**
 * Created by pedro on 13/02/16.
 */
public class GameOverScreen extends BaseScreen {

  private TextField tf;
  private Stage stage;
  private int score;
  private String lastName;
  private Music gameOverMusic;

  public GameOverScreen(Main game, int score, String lastName) {
    super(game);
    this.game = game;
    this.score = score;
    this.lastName = lastName;
    GameScreen.changeScreen = false;
  }

  @Override
  public void show() {

    gameOverMusic = game.getAssetManager().get("musics/gameOverMusic.mp3");
    gameOverMusic.setLooping(true);
    gameOverMusic.setVolume(1f);
    gameOverMusic.play();

    stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    Table table = new Table();

    table.setTransform(true);
    table.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    table.setScale(Gdx.graphics.getWidth() / 200);
    stage.addActor(table);

    Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

    Label lbGameOver = new Label("Game Over", skin);
    lbGameOver.setColor(Color.ORANGE);

    Container container = new Container(lbGameOver);
    container.setTransform(true);
    container.setFillParent(true);
    container.addAction(Actions.forever(
        Actions.sequence(Actions.scaleBy(2, 2, 1f), Actions.scaleBy(-2f, -2f, 1f))));
    table.add(container).padBottom(Gdx.graphics.getWidth() / 25).right();
    table.row();

    Label lbScore = new Label("Score: " + score, skin);
    lbScore.setColor(Color.RED);
    table.add(lbScore).padBottom(Gdx.graphics.getWidth() / 40);
    table.row();

    Label name = new Label("Name: ", skin);
    name.setColor(Color.YELLOW);
    table.add(name);
    table.row();

    tf = new TextField(lastName, skin);
    tf.setColor(Color.RED);
    table.add(tf).padBottom(Gdx.graphics.getWidth() / 40);
    table.row();

    TextButton bPlayAgain = new TextButton("Play again", skin);
    bPlayAgain.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        gameOverMusic.stop();
        game.changeToAndroid.insertScore(score, tf.getText());
        game.setScreen(new LoadingScreen(game));
        stage.dispose();
      }
    });
    table.add(bPlayAgain).padBottom(Gdx.graphics.getWidth() / 40);
    table.row();

    TextButton bMenu = new TextButton("Menu", skin);
    bMenu.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.changeToAndroid.insertScore(score, tf.getText());
        game.changeToAndroid.startActivity();
        stage.dispose();
        game.getAssetManager().dispose();
        Gdx.app.exit();
      }
    });
    table.add(bMenu);
    table.row();

    Gdx.input.setInputProcessor(stage);
  }

  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
  }
}
