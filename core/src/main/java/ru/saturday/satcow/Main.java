package ru.saturday.satcow;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

public class Main extends ApplicationAdapter {
    public static final float SCR_WIDTH = 1280;
    public static final float SCR_HEIGHT = 720;
    public static final float SPAWN_X = 786;
    public static final float SPAWN_Y = 283;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;
    private BitmapFont font, font80;

    private Texture imgCow;
    private Texture imgPig;
    private Texture imgGrass;
    private Sound sndPig;
    private Sound sndCow;

    Cow[] cow = new Cow[2];
    Pig[] pig = new Pig[3];
    long timeStartPlay;
    long timeCurrent;
    int countAnimals;
    boolean gameOver;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        touch = new Vector3();
        font = new BitmapFont(Gdx.files.internal("fonts/heffer.fnt"));
        font80 = new BitmapFont(Gdx.files.internal("fonts/heffer80.fnt"));

        imgCow = new Texture("cow0.png");
        imgPig = new Texture("pig.png");
        imgGrass = new Texture("grass.jpg");
        sndCow = Gdx.audio.newSound(Gdx.files.internal("sound-cow.mp3"));
        sndPig = Gdx.audio.newSound(Gdx.files.internal("sound-pig.mp3"));

        timeStartPlay = TimeUtils.millis();

        for (int i = 0; i < cow.length; i++) {
            float w = MathUtils.random(50, 200);
            cow[i] = new Cow(SPAWN_X, SPAWN_Y, w, w);
        }
        for (int i = 0; i < pig.length; i++) {
            float w = MathUtils.random(50, 200);
            pig[i] = new Pig(SPAWN_X, SPAWN_Y, w, w);
        }
    }

    @Override
    public void render() {
        // касания
        if(Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            for (int i = 0; i < cow.length; i++) {
                if(!cow[i].isDead && cow[i].hit(touch.x, touch.y)){
                    sndCow.play();
                    cow[i].dead();
                    countAnimals++;
                }
            }
            for (int i = 0; i < pig.length; i++) {
                if(!pig[i].isDead && pig[i].hit(touch.x, touch.y)){
                    sndPig.play();
                    pig[i].dead();
                    countAnimals++;
                }
            }
        }

        // события
        for (Cow a : cow) a.fly();
        for (Pig a : pig) a.fly();
        if(countAnimals == pig.length+cow.length){
            gameOver = true;
        }
        if(!gameOver) {
            timeCurrent = TimeUtils.millis() - timeStartPlay;
        }

        // отрисовка
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgGrass, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        for (Cow a : cow) {
            batch.draw(imgCow, a.x, a.y, a.width, a.height, 0, 0, 842, 861, a.flipX, a.flipY);
        }
        for (Pig a : pig) {
            batch.draw(imgPig, a.x, a.y, a.width, a.height, 0, 0, 742, 708, a.flipX, a.flipY);
        }
        font.draw(batch, "Поймано: "+ countAnimals, 10, SCR_HEIGHT-10);
        font.draw(batch, showTime(timeCurrent), SCR_WIDTH-170, SCR_HEIGHT-10);
        if(gameOver){
            font80.draw(batch, "Game Over", 0, SCR_HEIGHT-100, SCR_WIDTH, Align.center, true);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        imgCow.dispose();
        imgPig.dispose();
        imgGrass.dispose();
        sndCow.dispose();
        sndPig.dispose();
        font.dispose();
        font80.dispose();
    }

    String showTime(long t){
        long msec = t%1000;
        long sec = t/1000%60;
        long min = t/1000/60%60;
        long hour = t/1000/60/60;
        return hour+":"+min/10+min%10+":"+sec/10+sec%10+":"+msec/100;
    }
}
