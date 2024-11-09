package ru.saturday.satcow;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    public static final float SCR_WIDTH = 1280;
    public static final float SCR_HEIGHT = 720;
    Cow cow;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("cow0.png");
        cow = new Cow();
    }

    @Override
    public void render() {
        cow.fly();
        ScreenUtils.clear(0.55f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, cow.x, cow.y, cow.width, cow.height);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
