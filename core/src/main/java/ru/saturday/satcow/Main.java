package ru.saturday.satcow;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
    public static final int PLAY_GAME = 0;
    public static final int ENTER_NAME = 1;
    public static final int GAME_OVER = 2;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;
    private BitmapFont font, font60, font80;

    InputKeyboard keyboard;

    private Texture imgCow;
    private Texture imgPig;
    private Texture imgGrass;
    private Sound sndPig;
    private Sound sndCow;

    CowButton btnRestart;
    CowButton btnClearTable;

    Cow[] cows = new Cow[12];
    Pig[] pigs = new Pig[13];
    Player[] players = new Player[6];
    long timeStartPlay;
    long timeCurrent;
    int countAnimals;
    int stateGame;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        touch = new Vector3();
        font = new BitmapFont(Gdx.files.internal("fonts/heffer.fnt"));
        font60 = new BitmapFont(Gdx.files.internal("fonts/heffer60.fnt"));
        font80 = new BitmapFont(Gdx.files.internal("fonts/heffer80.fnt"));

        keyboard = new InputKeyboard(font60, SCR_WIDTH, SCR_HEIGHT, 12);

        imgCow = new Texture("cow0.png");
        imgPig = new Texture("pig.png");
        imgGrass = new Texture("grass.jpg");
        sndCow = Gdx.audio.newSound(Gdx.files.internal("sound-cow.mp3"));
        sndPig = Gdx.audio.newSound(Gdx.files.internal("sound-pig.mp3"));

        btnRestart = new CowButton("RESTART", font60, 550, 120);
        btnClearTable = new CowButton("clear table", font60, 10, 70);

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player("Noname", 0);
        }
        loadTableOfRecords();
        gameRestart();
    }

    @Override
    public void render() {
        // касания
        if(Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            for (Cow cow : cows) {
                if (!cow.isDead && cow.hit(touch.x, touch.y)) {
                    sndCow.play();
                    cow.dead();
                    countAnimals++;
                }
            }
            for (Pig pig : pigs) {
                if (!pig.isDead && pig.hit(touch.x, touch.y)) {
                    sndPig.play();
                    pig.dead();
                    countAnimals++;
                }
            }

            if(stateGame == GAME_OVER){
                if(btnRestart.hit(touch.x, touch.y)){
                    gameRestart();
                }
                if(btnClearTable.hit(touch.x, touch.y)){
                    clearTableOfRecords();
                }
            }

            if(stateGame == ENTER_NAME) {
                if (keyboard.touch(touch.x, touch.y)) gameOver(keyboard.getText());
            }
        }

        // события
        for (Cow cow : cows) cow.fly();
        for (Pig pig : pigs) pig.fly();
        if(stateGame == PLAY_GAME) {
            if (countAnimals == pigs.length + cows.length) {
                stateGame = ENTER_NAME;
                keyboard.start();
            }
            timeCurrent = TimeUtils.millis() - timeStartPlay;
        }

        // отрисовка
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgGrass, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        for (Cow cow : cows) {
            batch.draw(imgCow, cow.x, cow.y, cow.width, cow.height, 0, 0, 842, 861, cow.flipX, cow.flipY);
        }
        for (Pig pig : pigs) {
            batch.draw(imgPig, pig.x, pig.y, pig.width, pig.height, 0, 0, 742, 708, pig.flipX, pig.flipY);
        }
        font.draw(batch, "Поймано: "+ countAnimals, 10, SCR_HEIGHT-10);
        font.draw(batch, showTime(timeCurrent), SCR_WIDTH-120, SCR_HEIGHT-10);
        if(stateGame == ENTER_NAME) {
            keyboard.draw(batch);
        }
        if(stateGame == GAME_OVER){
            font80.draw(batch, "Game Over", 0, SCR_HEIGHT-100, SCR_WIDTH, Align.center, true);
            for (int i = 0; i < players.length-1; i++) {
                font60.draw(batch, players[i].name, 400, 500-i*70);
                font60.draw(batch, showTime(players[i].time), 750, 500-i*70);
            }
            btnRestart.font.draw(batch, btnRestart.text, btnRestart.x, btnRestart.y);
            btnClearTable.font.draw(batch, btnClearTable.text, btnClearTable.x, btnClearTable.y);
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
        font60.dispose();
        font80.dispose();
        keyboard.dispose();
    }

    private String showTime(long t){
        long msec = t%1000;
        long sec = t/1000%60;
        long min = t/1000/60%60;
        //long hour = t/1000/60/60;
        return min/10+min%10+":"+sec/10+sec%10+":"+msec/100;
    }

    private void gameOver(String name){
        stateGame = GAME_OVER;
        players[players.length-1].set(name, timeCurrent);
        sortTableOfRecords();
        saveTableOfRecords();
    }

    private void gameRestart(){
        stateGame = PLAY_GAME;
        countAnimals = 0;
        for (int i = 0; i < cows.length; i++) {
            float w = MathUtils.random(50, 200);
            cows[i] = new Cow(SPAWN_X, SPAWN_Y, w, w);
        }
        for (int i = 0; i < pigs.length; i++) {
            float w = MathUtils.random(50, 200);
            pigs[i] = new Pig(SPAWN_X, SPAWN_Y, w, w);
        }
        timeStartPlay = TimeUtils.millis();
    }

    private void sortTableOfRecords(){
        for (Player p: players) {
            if(p.time == 0) p.time = Long.MAX_VALUE;
        }

        for(int j = 0; j < players.length; j++) {
            for (int i = 0; i < players.length - 1; i++) {
                if (players[i].time > players[i + 1].time) {
                    Player c = players[i];
                    players[i] = players[i + 1];
                    players[i + 1] = c;
                }
            }
        }

        for (Player p: players) {
            if(p.time == Long.MAX_VALUE) p.time = 0;
        }
    }

    private void saveTableOfRecords(){
        Preferences prefs = Gdx.app.getPreferences("CowPigsPrefs");
        for (int i = 0; i < players.length; i++) {
            prefs.putString("name"+i, players[i].name);
            prefs.putLong("time"+i, players[i].time);
        }
        prefs.flush();
    }

    private void loadTableOfRecords(){
        Preferences prefs = Gdx.app.getPreferences("CowPigsPrefs");
        for (int i = 0; i < players.length; i++) {
            players[i].name = prefs.getString("name"+i, "none");
            players[i].time = prefs.getLong("time"+i, 0);
        }
    }

    private void clearTableOfRecords(){
        for (int i = 0; i < players.length; i++) {
            players[i].set("Noname", 0);
        }
    }
}
