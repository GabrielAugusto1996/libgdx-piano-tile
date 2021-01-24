package br.com.piano.tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static br.com.piano.tiles.CorConstants.VERDE;
import static br.com.piano.tiles.GameConstants.TILE_HEIGHT;
import static br.com.piano.tiles.GameConstants.TILE_WIDTH;

public class Fileira {

    private float y;

    private int correta; // 0 a 3

    public Fileira(float y, int correta) {
        this.y = y;
        this.correta = correta;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getCorreta() {
        return correta;
    }

    public void setCorreta(int correta) {
        this.correta = correta;
    }

    public void draw(final ShapeRenderer shapeRenderer) {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(VERDE);

        shapeRenderer.rect(correta * TILE_WIDTH, y, TILE_WIDTH, TILE_HEIGHT);

        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GRAY);

        for (int i = 0; i <= 3; i++) {
            shapeRenderer.rect(i * TILE_WIDTH, y, TILE_WIDTH, TILE_HEIGHT);
        }
    }
}