package io.itzbenz.reactive;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;

public class Skins {
    public static Skin material;
    public static Skin def;

    public static void init(){
        VisUI.load(VisUI.SkinScale.X2);

        material = new Skin();
        def = VisUI.getSkin();;
    }

    public static void dispose(){
        material.dispose();
        VisUI.dispose();
    }
}
