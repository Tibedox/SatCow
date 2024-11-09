package ru.saturday.satcow;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    public static final float SCR_WIDTH = 1280;
    public static final float SCR_HEIGHT = 720;
    Cow[] cow = new Cow[33];

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("cow0.png");
        for (int i=0; i<cow.length; i++)
            cow[i] = new Cow(SCR_WIDTH/2, SCR_HEIGHT/2, MathUtils.random(100, 200), MathUtils.random(100, 200));
    }

    @Override
    public void render() {
        for (int i=0; i<cow.length; i++) cow[i].fly();
        ScreenUtils.clear(0.55f, 0.15f, 0.2f, 1f);
        batch.begin();
        for (int i=0; i<cow.length; i++)
            batch.draw(image, cow[i].x, cow[i].y, cow[i].width, cow[i].height);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
