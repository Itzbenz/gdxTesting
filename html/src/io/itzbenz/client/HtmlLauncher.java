package io.itzbenz.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.GwtGraphics;
import io.itzbenz.GdxTestingItz;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
            // Resizable application, uses available space in browser
            GwtApplicationConfiguration config = new GwtApplicationConfiguration(true);
            config.fullscreenOrientation = GwtGraphics.OrientationLockType.LANDSCAPE;
    
            return config;
        }
    
    @Override
    public ApplicationListener createApplicationListener() {
        return new GdxTestingItz();
    }
        /*
        @Override
        public Preloader.PreloaderCallback getPreloaderCallback() {
                return createPreloaderPanel(GWT.getHostPageBaseURL() + "preloadlogo.png");
        }
        
        @Override
        protected void adjustMeterPanel(Panel meterPanel, Style meterStyle) {
                meterPanel.setStyleName("gdx-meter");
                meterPanel.addStyleName("nostripes");
                meterStyle.setProperty("backgroundColor", "#ffffff");
                meterStyle.setProperty("backgroundImage", "none");
        }
        
         */
    
}