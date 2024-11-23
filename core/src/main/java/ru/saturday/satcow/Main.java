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
import com.badlogic.gdx.utils.TimeUtils;

public class Main extends ApplicationAdapter {
    public static final float SCR_WIDTH = 1280;
    public static final float SCR_HEIGHT = 720;
    public static final float SPAWN_X = 786;
    public static final float SPAWN_Y = 283;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;
    private BitmapFont font;

    private Texture imgCow;
    private Texture imgPig;
    private Texture imgGrass;
    private Sound sndPig;
    private Sound sndCow;

    Cow[] cow = new Cow[5000];
    Pig[] pig = new Pig[5000];
    int numberLiveCows;
    int numberLivePigs;
    long timeLastSpawnCow, timeLastSpawnPig;
    long timeIntervalCow = 3000;
    long timeIntervalPig = 2000;
    int countDeads;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        touch = new Vector3();
        font = new BitmapFont(Gdx.files.internal("heffer.fnt"));

        imgCow = new Texture("cow0.png");
        imgPig = new Texture("pig.png");
        imgGrass = new Texture("grass.jpg");
        sndCow = Gdx.audio.newSound(Gdx.files.internal("sound-cow.mp3"));
        sndPig = Gdx.audio.newSound(Gdx.files.internal("sound-pig.mp3"));
    }

    @Override
    public void render() {
        // касания
        if(Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            for (int i = 0; i < numberLiveCows; i++) {
                if(!cow[i].isDead && cow[i].hit(touch.x, touch.y)){
                    sndCow.play();
                    cow[i].dead();
                    countDeads++;
                }
            }
            for (int i = 0; i < numberLivePigs; i++) {
                if(!pig[i].isDead && pig[i].hit(touch.x, touch.y)){
                    sndPig.play();
                    pig[i].dead();
                    countDeads++;
                }
            }
        }

        // события
        spawnAnimals();
        moveAnimals();

        // отрисовка
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgGrass, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        for (int i = 0; i< numberLiveCows; i++) {
            batch.draw(imgCow, cow[i].x, cow[i].y, cow[i].width, cow[i].height,
            0, 0, 842, 861, cow[i].flipX, cow[i].flipY);
        }
        for (int i = 0; i< numberLivePigs; i++) {
            batch.draw(imgPig, pig[i].x, pig[i].y, pig[i].width, pig[i].height,
                0, 0, 742, 708, pig[i].flipX, pig[i].flipY);
        }
        font.draw(batch, "Сбито: "+countDeads, 10, SCR_HEIGHT-10);
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
    }

    private void spawnAnimals(){
        if(TimeUtils.millis() > timeLastSpawnCow+timeIntervalCow && numberLiveCows < cow.length){
            float w = MathUtils.random(50, 200);
            cow[numberLiveCows] = new Cow(SPAWN_X, SPAWN_Y, w, w);
            numberLiveCows++;
            timeLastSpawnCow = TimeUtils.millis();
        }
        if(TimeUtils.millis() > timeLastSpawnPig+timeIntervalPig && numberLivePigs < pig.length){            float w = MathUtils.random(50, 100);
            pig[numberLivePigs] = new Pig(SPAWN_X, SPAWN_Y, w, w);
            numberLivePigs++;
            timeLastSpawnPig = TimeUtils.millis();
        }
    }

    private void moveAnimals(){
        for (int i=0; i<numberLiveCows; i++) {
            cow[i].fly();
        }
        for (int i=0; i<numberLivePigs; i++) {
            pig[i].fly();
        }
    }
}
