package br.com.piano.tiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private Random random;
    private int estado;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(TRUE);

        fileiras = new ArrayList<>();

        random = new Random();

        iniciar();
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

            if (estado == 0) {
                estado = 1;
            } else if (estado == 1) {
                for (int i = 0; i < fileiras.size(); i++) {
                    final int retorno = fileiras.get(i).toque(x, y);

                    if (retorno != 0) {
                        if (retorno == 1 && i == indexInferior) {
                            pontos++;
                            indexInferior++;
                        } else if (retorno == 1) {
                            fileiras.get(indexInferior).error();
                            finalizar(0);
                        } else {
                            // Encerrar o jogo da forma 2
                            finalizar(0);
                        }

                        break;
                    }
                }
            } else if (estado == 2) {
            	iniciar();
			}
        }
    }

    private void finalizar(final int opcao) {
        Gdx.input.vibrate(200);
        estado = 2;

        if (opcao == 1) {
            for (final Fileira fileira : fileiras) {
                fileira.setY(fileira.getY() + TILE_HEIGHT);
            }
        }
    }

    private void update(final float deltaTime) {
        if (estado == 1) {
            tempoTotal += deltaTime;

            VELOCIDADE_ATUAL = VELOCIDADE_INICIAL + TILE_HEIGHT * tempoTotal / 8f;

            for (int i = 0; i < fileiras.size(); i++) {
                final int retorno = fileiras.get(i).update(deltaTime);
                fileiras.get(1).animacao(deltaTime);

                if (retorno != 0) {
                    if (retorno == 1) {
                        fileiras.remove(i);
                        i--;
                        indexInferior--;

                        adicionar();
                    } else if (retorno == 2) {
                        finalizar(1);
                    }
                }
            }
        } else if (estado == 2) {
            for (final Fileira f : fileiras) {
                f.animacao(deltaTime);
            }
        }
    }

    private void adicionar() {
        final float y = fileiras.get(fileiras.size() - 1).getY() + TILE_HEIGHT;

        fileiras.add(new Fileira(y, random.nextInt(4)));
    }

    private void iniciar() {
        tempoTotal = 0;
        indexInferior = 0;
        pontos = 0;

        fileiras.clear();

        fileiras.add(new Fileira(TILE_HEIGHT, random.nextInt(4)));

        adicionar();
        adicionar();
        adicionar();
        adicionar();

        estado = 0;
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
