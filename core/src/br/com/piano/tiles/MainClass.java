package br.com.piano.tiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

import static br.com.piano.tiles.GameConstants.SCREEN_Y;
import static br.com.piano.tiles.GameConstants.TILE_HEIGHT;
import static br.com.piano.tiles.GameConstants.VELOCIDADE_ATUAL;
import static br.com.piano.tiles.GameConstants.VELOCIDADE_INICIAL;
import static java.lang.Boolean.TRUE;

public class MainClass extends ApplicationAdapter {

    private ShapeRenderer shapeRenderer;
    private List<Fileira> fileiras;
    private float tempoTotal;
    private int indexInferior;
    private int pontos;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(TRUE);

        fileiras = new ArrayList<>();

        fileiras.add(new Fileira(0, 0));
        fileiras.add(new Fileira(TILE_HEIGHT, 1));
        fileiras.add(new Fileira(2 * TILE_HEIGHT, 2));

        tempoTotal = 0;
        indexInferior = 0;

        pontos = 0;
    }

    @Override
    public void render() {
        input();
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin();

        for (Fileira fileira : fileiras) {
            fileira.draw(shapeRenderer);
        }

        shapeRenderer.end();
    }

    private void input() {
        if (Gdx.input.justTouched()) {
            final int x = Gdx.input.getX();
            final int y = SCREEN_Y - Gdx.input.getY();

            for (int i = 0; i < fileiras.size(); i++) {
                final int retorno = fileiras.get(i).toque(x, y);

                if (retorno != 0) {
                    if (retorno == 1 && i == indexInferior) {
                        pontos++;
                        indexInferior++;
                    } else if (retorno == 1) {
                        // Encerrar o jogo da forma 1
                        finalizar();
                    } else {
                        // Encerrar o jogo da forma 2
                        finalizar();
                    }

                    break;
                }
            }
        }
    }

    private void finalizar() {
        Gdx.input.vibrate(200);
    }

    private void update(final float deltaTime) {
        tempoTotal += deltaTime;

        VELOCIDADE_ATUAL = VELOCIDADE_INICIAL + TILE_HEIGHT * tempoTotal / 8f;

        for (int i = 0; i < fileiras.size(); i++) {
            final int retorno = fileiras.get(i).update(deltaTime);

            if (retorno != 0) {
                if (retorno == 1) {
                    fileiras.remove(i);
                    i--;
                    indexInferior--;

                    adicionar();
                }
            }
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
