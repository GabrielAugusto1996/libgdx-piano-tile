package br.com.piano.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class Piano {

    private Map<String, Sound> sounds;
    private Array<String> notas;
    private int indice = 0;

    public Piano(final String musica) {
        final FileHandle file = Gdx.files.internal(musica + ".txt");

        final String texto = file.readString();

        notas = new Array<>(texto.split(" "));

        sounds = new HashMap<>();

        for (final String nota : notas) {
            if (!sounds.containsKey(nota)) {
                sounds.put(nota, Gdx.audio.newSound(Gdx.files.internal("sons/" + nota + ".wav")));
            }
        }
    }

    public void tocar() {
        sounds.get(notas.get(indice)).play();
        indice++;

        if (indice == notas.size) {
            indice = 0;
        }
    }

    public void reset() {
        indice = 0;
    }

    public void dispose() {
        for (final String key : sounds.keySet()) {
            sounds.get(key).dispose();
        }
    }
}