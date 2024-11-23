package ru.saturday.satcow;

public class Pig extends Animal{
    public Pig(float x, float y, float width, float height){
        super(x, y, width, height);
    }

    @Override
    void dead() {
        isDead = true;
        stepX = 0;
        stepY = 10;
    }
}
