package ru.saturday.satcow;

public class Cow extends Animal{
    public Cow(float x, float y, float width, float height){
        super(x, y, width, height);
    }

    @Override
    void dead() {
        isDead = true;
        stepX = 0;
        stepY = -10;
        flipY = true;
    }
}
