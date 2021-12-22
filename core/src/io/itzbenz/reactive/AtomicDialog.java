package io.itzbenz.reactive;

import Atom.Reflect.Reflect;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class AtomicDialog extends Dialog {

    public AtomicDialog(){
        this("Dialog");
    }

    public AtomicDialog(String title) {
        this(title, Skins.def);
    }

    public AtomicDialog(String title, Skin skin) {
        super(title, skin);
        setResizable(true);
        build();
    }


    protected abstract void build();

    @Override
    protected void result(Object object) {
        super.result(object);
        if(object instanceof Runnable){
            ((Runnable) object).run();
        }
    }
}
