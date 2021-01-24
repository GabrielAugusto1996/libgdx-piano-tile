package br.com.piano.tiles;

import com.badlogic.gdx.Gdx;

public class GameConstants {

    private GameConstants(){}

    public static final int SCREEN_X = Gdx.graphics.getWidth();
    public static final int SCREEN_Y = Gdx.graphics.getHeight();

    public static final int TILE_WIDTH = SCREEN_X / 4;
    public static final int TILE_HEIGHT = SCREEN_Y / 4;
}