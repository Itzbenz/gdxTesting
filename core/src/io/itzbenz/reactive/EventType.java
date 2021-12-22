package io.itzbenz.reactive;

import com.badlogic.gdx.scenes.scene2d.Event;

public class EventType extends Event {

    public static class Resize extends EventType{
        public int width;
        public int height;
        public Resize(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
}
