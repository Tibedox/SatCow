package ru.saturday.satcow;

import com.badlogic.gdx.audio.Sound;

public class Cow extends Animal{
    public Cow(float x, float y, float width, float height){
        super(x, y, width, height);


    }

    @Override
    void say(Sound snd) {
        snd.play();
    }
}
