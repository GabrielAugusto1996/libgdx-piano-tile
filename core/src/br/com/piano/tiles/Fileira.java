package br.com.piano.tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static br.com.piano.tiles.CorConstants.VERDE;
import static br.com.piano.tiles.GameConstants.TILE_HEIGHT;
import static br.com.piano.tiles.GameConstants.TILE_WIDTH;
import static br.com.piano.tiles.GameConstants.VELOCIDADE_ATUAL;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Fileira {

    private float y;

    private int correta; // 0 a 3

    private int posicao;

    private boolean ok;

    public Fileira(float y, int correta) {
        this.y = y;
        this.correta = correta;
        this.ok = FALSE;
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

    public int update(final float time) {
        y -= time * VELOCIDADE_ATUAL;

        if (y < 0 - TILE_HEIGHT) {
            if (ok) {
                return 1;
            } else {
                return 2;
            }
        }

        return 0;
    }

    public int toque(final int toqueX, final int toqueY) {
        if (toqueY >= y && toqueY <= y + TILE_HEIGHT) {
            posicao = toqueX / TILE_WIDTH;

            if (posicao == correta) {
                ok = TRUE;
                return 1;
            } else {
                ok = FALSE;
                return 2;
            }
        }

        return 0;
    }
}