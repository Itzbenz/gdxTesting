package io.itzbenz.drop;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

import java.util.Objects;

public class RainDrop extends Rectangle implements Pool.Poolable {
    public static int ids;
    public static float scale = 0.4f;
    public static float size = 64 * scale;
    private final int id = ids++;
    
    public RainDrop() {
        reset();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RainDrop rainDrop = (RainDrop) o;
        return id == rainDrop.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
    
    public void reset() {
        x = MathUtils.random(0, Drop.camera.viewportWidth - size);
        y = Drop.camera.viewportHeight * 0.98f;
        width = size;
        height = size;
    }
}
