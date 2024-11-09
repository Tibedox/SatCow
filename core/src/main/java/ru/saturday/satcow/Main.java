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
    Cow cow, cow1, cow2;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("cow0.png");
        cow = new Cow(0, 0, 100, 100);
        cow1 = new Cow(500, 500, 200, 200);
        cow2 = new Cow(800, 300, 150, 150);
    }

    @Override
    public void render() {
        cow.fly();
        cow1.fly();
        cow2.fly();
        ScreenUtils.clear(0.55f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, cow.x, cow.y, cow.width, cow.height);
        batch.draw(image, cow1.x, cow1.y, cow1.width, cow1.height);
        batch.draw(image, cow2.x, cow2.y, cow2.width, cow2.height);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
