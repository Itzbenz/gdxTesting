package io.itzbenz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;

public class GdxTestingItz extends ApplicationAdapter {
	
	Skin skin;
	Stage stage;
	SpriteBatch batch;
	Label fpsLabel;
	@Override
	public void create () {
		batch = new SpriteBatch();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
		skin = new Skin();
		
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		
		// Store the default libGDX font under the name "default".
		BitmapFont bitmapFont = new BitmapFont();
		skin.add("default", bitmapFont);
		skin.add("default",new Label.LabelStyle(bitmapFont, Color.WHITE));
		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		
		// Create a table that fills the screen. Everything else will go inside this table.
		final Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		// Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
		final TextButton button = new TextButton("Click me!", skin);
		fpsLabel = new Label("this",skin);
		table.add(fpsLabel).top().left().growX().row();
		table.add(button).row();
		
		// Add a listener to the button. ChangeListener is fired when the button's checked state changes, eg when clicked,
		// Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
		// ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
		// revert the checked state.
		table.add(new Image(skin.newDrawable("white", Color.RED))).size(48).growX();
		table.add(new Image(skin.newDrawable("white", Color.RED))).size(48).growX();
		table.add(new Image(skin.newDrawable("white", Color.RED))).size(48).growX();
		table.add(new Image(skin.newDrawable("white", Color.RED))).size(48).growX();
		table.add(new Image(skin.newDrawable("white", Color.RED))).size(48).growX().row();
		button.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				button.setText(button.isChecked() ? "On" : "Off");
			}
		});
		
		// Add an image actor. Have to set the size, else it would be the size of the drawable (which is the 1x1 texture).
	
	}
	long last = System.currentTimeMillis();
	@Override
	public void render () {
		fpsLabel.setText("FPS: " + 1000/(System.currentTimeMillis()-last));
		ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		last = System.currentTimeMillis();
	}
	
	@Override
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void dispose () {
		stage.dispose();
		skin.dispose();
	}
}
