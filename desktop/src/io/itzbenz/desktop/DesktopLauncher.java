package io.itzbenz.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.itzbenz.GdxTestingItz;

public class DesktopLauncher {
    
    public static void main(String[] arg) throws Throwable {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        ApplicationListener listener = new GdxTestingItz();
        config.title = listener.getClass().getSimpleName();
        new LwjglApplication(listener, config);
    }
}
