package io.itzbenz.reactive;

import Atom.String.WordGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MainMenu extends AtomicDialog{

    @Override
    protected void build() {
        for (int i = 0; i < 5; i++) {
            getContentTable().add(WordGenerator.randomSentence()).growX().row();
        }
        //add button
        button("Play", (Runnable) () -> {

        });
    }
}
