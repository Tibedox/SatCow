package ru.saturday.satcow;

import com.badlogic.gdx.audio.Sound;

public class Pig extends Animal{
    public Pig(float x, float y, float width, float height){
        super(x, y, width, height);
    }

    @Override
    void say(Sound snd) {
        snd.play();
    }
}
