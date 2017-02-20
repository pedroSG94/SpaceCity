package com.pedro.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pedro.game.Main;
import com.pedro.game.actors.BulletActor;
import com.pedro.game.actors.CityActor;
import com.pedro.game.actors.LaserActor;
import com.pedro.game.actors.meteorite.MeteoriteActor;
import com.pedro.game.actors.meteorite.MeteoriteActorFast;
import com.pedro.game.actors.meteorite.MeteoriteActorNormal;
import com.pedro.game.actors.meteorite.MeteoriteActorSlow;
import com.pedro.game.utils.BaseScreen;
import com.pedro.game.utils.MakeAnimation;

import java.util.ArrayList;

/**
 * Created by pedro on 13/02/16.
 */
public class GameScreen extends BaseScreen {

  private Label lbScore;
  private int numScore;
  private TextureRegion noHp, hp;
  private Sprite noPower;
  public static Sprite power;

  private Sound sound, meteoriteDeathSound;
  private Music backgroundMusic;

  private Texture tBackground;
  private Animation animationExplosionCity, animationExplosionBullet;
  private SpriteBatch batch;
  private float stateExplosionCity, stateExplosionBullet;
  private Vector2 positionBullet;

  public static int controlExplosionCity, controlExplosionBullet;
  public static Vector2 positionMeteorite, meteoriteSize, meteoriteDeathSize;

  public static Stage stage;
  private CityActor city;
  private float spawn;

  public static ArrayList<MeteoriteActor> listMeteorite;

  public static boolean reset, remove;
  public static BulletActor bulletRemove;
  public static LaserActor laserRemove;
  public static MeteoriteActor meteoriteRemove;

  public static boolean changeScreen;

  private Main game;
  public static LaserActor killLaser;

  public GameScreen(Main game) {
    super(game);
    this.game = game;
  }

  @Override
  public void show() {

    numScore = 0;
    stateExplosionBullet = 0;
    stateExplosionCity = 0;
    controlExplosionCity = 16;
    controlExplosionBullet = 17;
    spawn = 1f;

    reset = false;
    remove = false;
    changeScreen = false;

    Pixmap background =
        new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
    for (int i = 0; i < 1000; i++) {
      float grey = MathUtils.random(0, 255);
      background.setColor(Color.argb8888(grey, grey, grey, grey));
      background.drawPixel((MathUtils.random(0, Gdx.graphics.getWidth())),
          MathUtils.random(0, Gdx.graphics.getHeight()));
    }
    tBackground = new Texture(background);
    background.dispose();

        /*Load the sounds*/
    sound = game.getAssetManager().get("effects/explosionSoundCity.mp3");
    meteoriteDeathSound = game.getAssetManager().get(("effects/meteoriteDeathSound.mp3"));
    backgroundMusic = game.getAssetManager().get("musics/gamePlayMusic.mp3");

    positionBullet = new Vector2(0, 0);

    listMeteorite = new ArrayList<MeteoriteActor>();
    stage = new Stage();
    stage.setViewport(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        /* Draw the score*/
    lbScore = new Label("Score: " + numScore, new Skin(Gdx.files.internal("skin/uiskin.json")));
    lbScore.setColor(Color.GOLD);
    Container container = new Container(lbScore);
    container.setTransform(true);
    container.addAction(Actions.scaleBy(1, 1));
    container.setPosition(-Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 10,
        Gdx.graphics.getHeight() / 2 - Gdx.graphics.getWidth() / 10);
    stage.addActor(container);

        /*Draw the city*/
    city = new CityActor(stage, game);
    stage.addActor(city);

    animationExplosionCity =
        new MakeAnimation(game.getAssetManager().get("texture/explosionCity.png", Texture.class), 5,
            3).getAnimation();
    animationExplosionBullet =
        new MakeAnimation(game.getAssetManager().get("texture/explosionBullet.png", Texture.class),
            4, 4).getAnimation();
    batch = new SpriteBatch();

    backgroundMusic.setLooping(true);
    backgroundMusic.setVolume(0.5f);
    backgroundMusic.play();

    Texture allHp = game.getAssetManager().get("texture/hp.png", Texture.class);
    hp = new TextureRegion(allHp, allHp.getWidth(), allHp.getHeight() / 2);
    noHp =
        new TextureRegion(allHp, 0, allHp.getHeight() / 2, allHp.getWidth(), allHp.getHeight() / 2);

    noPower = new Sprite(noHp);
    power = new Sprite(hp);
    power.setColor(Color.BLUE);

    power.setBounds(0,
        Gdx.graphics.getHeight() - ((Gdx.graphics.getHeight() / 8) + Gdx.graphics.getWidth() / 15)
            + 6, Gdx.graphics.getWidth() / 20 * city.getLife(), Gdx.graphics.getWidth() / 15);
    noPower.setBounds(0,
        Gdx.graphics.getHeight() - ((Gdx.graphics.getHeight() / 8) + Gdx.graphics.getWidth() / 15),
        Gdx.graphics.getWidth() / 20 * 5, Gdx.graphics.getWidth() / 15);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    if (remove) deleteActors();

    if (changeScreen) {
      dispose();
      backgroundMusic.stop();
      game.setScreen(new GameOverScreen(game, numScore, ""));
    }

    if (spawn <= 0) {
      MeteoriteActor meteorite;
      int random = MathUtils.random(0, 100);
      if (random < 70) {
        meteorite = new MeteoriteActorNormal(city, game);
      } else if (random >= 70 && random < 90) {
        meteorite = new MeteoriteActorFast(city, game);
      } else {
        meteorite = new MeteoriteActorSlow(city, game);
      }
      listMeteorite.add(meteorite);
      stage.addActor(meteorite);
      spawn = 1f;
    }

    batch.begin();
    batch.draw(tBackground, 0, 0);
    batch.draw(noHp, 0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 8,
        Gdx.graphics.getWidth() / 20 * 5, Gdx.graphics.getWidth() / 15);
    batch.draw(hp, 0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 8 + 6,
        Gdx.graphics.getWidth() / 20 * city.getLife(), Gdx.graphics.getWidth() / 15);

    noPower.draw(batch);
    power.draw(batch);
    batch.end();

    stage.act();
    stage.draw();
    spawn -= delta;

    drawExplosionCity();
    drawExplosionBullet(positionBullet);
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height);
  }

  @Override
  public void dispose() {
    stage.dispose();
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

  public void drawExplosionCity() {
    if (controlExplosionCity <= 15) {
      if (controlExplosionCity == 0) sound.play(3f);
      Gdx.input.vibrate(500);

      stateExplosionCity += Gdx.graphics.getDeltaTime();
      Sprite sExplosionCity =
          new Sprite(animationExplosionCity.getKeyFrame(stateExplosionCity, true));
      sExplosionCity.setSize(meteoriteSize.x * 2, meteoriteSize.y * 2);
      sExplosionCity.setPosition(positionMeteorite.x, 20);
      batch.begin();
      sExplosionCity.draw(batch);
      batch.end();
      controlExplosionCity++;
    }
  }

  public void drawExplosionBullet(Vector2 position) {
    if (controlExplosionBullet <= 16) {
      stateExplosionBullet += Gdx.graphics.getDeltaTime();
      Sprite sExplosionBullet =
          new Sprite(animationExplosionBullet.getKeyFrame(stateExplosionBullet, true));
      sExplosionBullet.setPosition(position.x, position.y);
      sExplosionBullet.setSize(meteoriteDeathSize.x, meteoriteDeathSize.y);
      batch.begin();
      sExplosionBullet.draw(batch);
      batch.end();
      controlExplosionBullet++;
    }
  }

  public void deleteActors() {
    for (Actor actor : stage.getActors()) {
      if (actor == killLaser) actor.remove();
      if (actor == bulletRemove || actor == laserRemove) {
        if (actor == bulletRemove) actor.remove();
        for (Actor meteorite : stage.getActors()) {
          if (meteorite == meteoriteRemove && meteoriteRemove.getLife() <= 0) {
            meteorite.remove();
            meteoriteDeathSize = new Vector2(meteoriteRemove.getrMeteorite().width,
                meteoriteRemove.getrMeteorite().height);
            positionBullet =
                new Vector2(meteoriteRemove.getrMeteorite().x, meteoriteRemove.getrMeteorite().y);
            meteoriteDeathSound.play(4f);
            controlExplosionBullet = 0;
            if (meteoriteDeathSize.x != 50) {
              numScore += 2;
            } else {
              numScore++;
            }
            lbScore.setText("Score: " + numScore);
            break;
          }
        }
        remove = false;
        break;
      }
    }
  }
}
