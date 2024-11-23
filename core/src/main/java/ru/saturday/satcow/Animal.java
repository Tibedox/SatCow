package ru.saturday.satcow;

import static ru.saturday.satcow.Main.SCR_HEIGHT;
import static ru.saturday.satcow.Main.SCR_WIDTH;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

public abstract class Animal {
    float x, y;
    float width, height;
    float stepX, stepY;
    boolean isDead;
    boolean flipX, flipY;

    public Animal(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        stepX = MathUtils.random(-5, 5f);
        stepY = MathUtils.random(-5, 5f);
        flipX = stepX > 0;
    }

    void fly(){
        x+=stepX;
        y+=stepY;
        if(!isDead){
            if (x < 0 || x > SCR_WIDTH - width) {
                stepX = -stepX;
                flipX = stepX > 0;
            }
            if (y < 0 || y > SCR_HEIGHT - height) {
                stepY = -stepY;
            }
        }
    }

    abstract void dead();

    boolean hit(float tx, float ty){
        return x<tx && tx<x+width && y<ty && ty<y+height;
    }
}
