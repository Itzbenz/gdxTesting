package io.itzbenz.reactive;


import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public abstract class AtomicDialog extends Dialog {
    public static final Pixmap defaultBackground = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
    public static final Drawable defaultBackgroundDrawable;
    static {
        defaultBackground.setColor(1, 1, 1, 0.5f);
        defaultBackground.fill();
        defaultBackgroundDrawable = new TextureRegionDrawable(new com.badlogic.gdx.graphics.Texture(defaultBackground));
    }
    public AtomicDialog(){
        this("Dialog");
    }

    public AtomicDialog(String title) {
        this(title, Skins.def);
    }

    public AtomicDialog(String title, Skin skin) {
        super(title, skin);
        init();
    }

    protected void init(){
        setResizable(true);
        setFillParent(true);
        getContentTable().clear();
        build();
        pack();
        addListener(event -> {
            if(event.getClass() == EventType.Resize.class){
                build();
                pack();
            }
            return false;
        });
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
