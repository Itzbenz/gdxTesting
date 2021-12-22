package io.itzbenz.reactive;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Date;

public class MainMenu extends ScrollableDialog{

    @Override
    protected void build() {
        StringBuilder sb = new StringBuilder();

        getContentTable().add(new Date(TimeUtils.millis()).toString()).growX().row();
        for (int j = 0; j < 100; j++) {
            int i =0;
            for ( int o =0; o < 50; o++) {
                char c = (char) ('A'+i++);
                sb.append(c);
            }
            Label l = new Label(sb.toString(), getSkin());
            l.setColor(Color.WHITE);
            getContentTable().add(l).growX().row();
            sb.setLength(0);
        }
    }
}
