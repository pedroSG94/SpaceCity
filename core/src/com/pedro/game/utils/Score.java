package com.pedro.game.utils;

/**
 * Created by alumno on 17/02/16.
 */
public class Score {

  private int score;
  private String name;

  public Score() {
  }

  public Score(int score, String name) {
    this.score = score;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }
}
