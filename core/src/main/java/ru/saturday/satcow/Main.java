package ru.saturday.satcow;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture imgCow;
    private Texture imgPig;
    public static final float SCR_WIDTH = 1280;
    public static final float SCR_HEIGHT = 720;
    Cow[] cow = new Cow[5000];
    Pig[] pig = new Pig[5000];
    int time;
    int n;

    @Override
    public void create() {
        batch = new SpriteBatch();
        imgCow = new Texture("cow0.png");
        imgPig = new Texture("pig.png");
        /*for (int i=0; i<cow.length; i++)
            cow[i] = new Cow(SCR_WIDTH/2, SCR_HEIGHT/2, MathUtils.random(100, 200), MathUtils.random(100, 200));*/
    }

    @Override
    public void render() {
        time++;
        if(time%60 == 0 && n<5000){
            float w = MathUtils.random(50, 200);
            cow[n] = new Cow(SCR_WIDTH/2, SCR_HEIGHT/2, w, w);
            w = MathUtils.random(20, 100);
            pig[n] = new Pig(SCR_WIDTH/2, SCR_HEIGHT/2, w, w);
            n++;
        }
        for (int i=0; i<n; i++) {
            cow[i].fly();
            pig[i].fly();
        }
        ScreenUtils.clear(0.55f, 0.15f, 0.2f, 1f);
        batch.begin();
        for (int i=0; i<n; i++) {
            batch.draw(imgCow, cow[i].x, cow[i].y, cow[i].width, cow[i].height);
            batch.draw(imgPig, pig[i].x, pig[i].y, pig[i].width, pig[i].height);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        imgCow.dispose();
    }
}
