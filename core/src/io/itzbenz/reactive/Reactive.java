package io.itzbenz.reactive;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.widget.PopupMenu;
import io.itzbenz.GdxTestingItz;

public class Reactive extends ApplicationAdapter {
    public Stage stage;
    public Viewport viewport;
    public Table root;
    @Override
    public void create() {
        super.create();
        GdxTestingItz.assets = new AssetManager();
        GdxTestingItz.bundle = new I18NBundle();

    }
    private static final int loadingTime = 1000/20;//maintain 20fps loading
    private boolean finishedLoading = false;



    public void init(){
        Skins.init();
        viewport = new ScreenViewport();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        root = new Table();
        root.setFillParent(true);
        stage.addActor(root);


        root.add(new MainMenu());
        //enable scroll somehow, cons ?
        //root.setTransform(true);
        //Gdx.input.setInputProcessor(stage);
    }
    public void update(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }
    @Override
    public void render() {
        super.render();
        if(finishedLoading){
            update();
            return;
        }
        if(GdxTestingItz.assets.update(loadingTime)){
            GdxTestingItz.assets.finishLoading();
            finishedLoading = true;
            init();
        }


    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        if (width == 0 && height == 0) return; //see https://github.com/libgdx/libgdx/issues/3673#issuecomment-177606278
        if(stage==null) return;
        stage.getViewport().update(width, height, true);
        PopupMenu.removeEveryMenu(stage);
        EventType.Resize resize = new EventType.Resize(width, height);
        for (Actor actor : stage.getActors()) {
            actor.fire(resize);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        GdxTestingItz.assets.clear();
    }
}
