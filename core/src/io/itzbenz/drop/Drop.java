package io.itzbenz.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import io.itzbenz.drop.pool.RainDropPool;

public class Drop extends ApplicationAdapter {
    public static OrthographicCamera camera;
    public BitmapFont font;
    protected RainDropPool rainDropPool;
    long score = 0;
    long lastUpdate = TimeUtils.millis(), lastShow = TimeUtils.millis();
    float fps = 0;
    private Texture dropImage;
    private Texture bucketImage;
    private Sound dropSound;
    private Music rainMusic;
    private SpriteBatch batch;
    private Rectangle bucket;
    private Array<RainDrop> raindrops;
    private long lastDropTime;
    
    @Override
    public void create() {
        // load the images for the droplet and the bucket, 64x64 pixels each
        dropImage = new Texture(Gdx.files.internal("sprite/droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("sprite/bucket.png"));
        
        // load the drop sound effect and the rain background "music"
        dropSound = Gdx.audio.newSound(Gdx.files.internal("sounds/water_drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/under_tree_rain.mp3"));
        
        // start the playback of the background music immediately
        rainMusic.setLooping(true);
        rainMusic.play();
        
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        font = new BitmapFont();
        // create a Rectangle to logically represent the bucket
        bucket = new Rectangle();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<>();
        rainDropPool = new RainDropPool();
        rainDropPool.fill(12);//magic number
        spawnRaindrop();
    }
    
    private void spawnRaindrop() {
        raindrops.add(rainDropPool.obtain());
        lastDropTime = TimeUtils.nanoTime();
    }
    
    @Override
    public void render() {
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(0, 0, 0.2f, 1);
        
        // tell the camera to update its matrices.
        camera.update();
        
        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix(camera.combined);
        
        // begin a new batch and draw the bucket and
        // all drops
        batch.begin();
        batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
        for (Rectangle raindrop : new Array.ArrayIterator<>(raindrops)) {
            batch.draw(dropImage, raindrop.x, raindrop.y, raindrop.width, raindrop.height);
        }
        float space = camera.viewportHeight * 0.02f;
        space += font.getLineHeight();
        float textY = camera.viewportHeight - space;
        if (TimeUtils.timeSinceMillis(lastShow) > 200){
            try {
                fps = (((float) 1000 / TimeUtils.timeSinceMillis(lastUpdate)));
            }catch(ArithmeticException ignored){}
            lastShow = TimeUtils.millis();
        }
        font.draw(batch, "Score: " + score, 0, textY);
        font.draw(batch, "Droplet Peak: " + rainDropPool.peak, 0, textY = textY - space);
        font.draw(batch, "FPS: " + fps, 0, textY = textY - space);
    
        lastUpdate = TimeUtils.millis();
        batch.end();
        
        // process user input
        if (Gdx.input.isTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - (float) 64 / 2;
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
        
        // make sure the bucket stays within the screen bounds
        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > camera.viewportWidth - bucket.getWidth()) bucket.x = camera.viewportWidth - bucket.getWidth();
        
        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > (100000000 * (RainDrop.scale / (camera.viewportHeight / 500))))
            spawnRaindrop();
        
        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the latter case we play back
        // a sound effect as well.
        
        Array.ArrayIterator<RainDrop> iter = new Array.ArrayIterator<>(raindrops);
        while (iter.hasNext()) {
            RainDrop raindrop = iter.next();
            raindrop.y -= (camera.viewportHeight * (1 - RainDrop.scale)) * Gdx.graphics.getDeltaTime();
            if (raindrop.y + RainDrop.size < 0){
                rainDropPool.free(raindrop);
                iter.remove();
            }else if (raindrop.overlaps(bucket)){
                dropSound.play();
                rainDropPool.free(raindrop);
                iter.remove();
                score++;
            }
        }
        
        
    }
    
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.setToOrtho(false, width, height);
        bucket.x = (float) width / 2 - (float) height / 2; // center the bucket horizontally
        bucket.y = height * 0.01f; // 0.01% from bottom
        bucket.width = 64;
        bucket.height = 64;
        
    }
    
    @Override
    public void dispose() {
        // dispose of all the native resources
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
        batch.dispose();
        font.dispose();
    }
}