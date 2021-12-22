package io.itzbenz.reactive;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.itzbenz.GdxTestingItz;

public class Reactive extends ApplicationAdapter {
    public Stage stage;
    public Viewport viewport;
    @Override
    public void create() {
        super.create();


    }
    private static final int loadingTime = 1000/20;//maintain 20fps loading
    private boolean finishedLoading = false;

    public void update(){
        stage.act();
        stage.draw();
    }

    public void init(){
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        //create main menu
        stage.addActor(new MainMenu());
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
    public void dispose() {
        super.dispose();
        stage.dispose();
        GdxTestingItz.assets.clear();
    }
}
