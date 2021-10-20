package io.itzbenz.drop.pool;

import com.badlogic.gdx.utils.Pool;
import io.itzbenz.drop.RainDrop;

public class RainDropPool extends Pool<RainDrop> {
    
    @Override
    protected RainDrop newObject() {
        return new RainDrop();
    }
}
