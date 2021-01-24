package br.com.piano.tiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

import static br.com.piano.tiles.GameConstants.TILE_HEIGHT;
import static br.com.piano.tiles.GameConstants.VELOCIDADE_ATUAL;
import static br.com.piano.tiles.GameConstants.VELOCIDADE_INICIAL;
import static java.lang.Boolean.TRUE;

public class MainClass extends ApplicationAdapter {

	private ShapeRenderer shapeRenderer;
	private List<Fileira> fileiras;
	private float tempoTotal;

	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(TRUE);

		fileiras = new ArrayList<>();

		fileiras.add(new Fileira(0, 0));
		fileiras.add(new Fileira(TILE_HEIGHT, 1));
		fileiras.add(new Fileira(2 * TILE_HEIGHT, 2));

		tempoTotal = 0;
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin();

		for (Fileira fileira : fileiras) {
			fileira.draw(shapeRenderer);
		}

		shapeRenderer.end();
	}

	private void update(final float deltaTime) {
		tempoTotal += deltaTime;

		VELOCIDADE_ATUAL = VELOCIDADE_INICIAL + TILE_HEIGHT * tempoTotal / 8f;

		for (final Fileira fileira : fileiras) {
			fileira.update(deltaTime);
		}
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
