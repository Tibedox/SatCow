package ru.saturday.satcow;

import static ru.saturday.satcow.Main.*;

import com.badlogic.gdx.math.MathUtils;

public abstract class Animal {
    float x, y;
    float width, height;
    float decreaseWidth, decreaseHeight;
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
            if(x < -width) x = SCR_WIDTH;
            else if (x>SCR_WIDTH) x = -width;
            if(y < -height) y = SCR_HEIGHT;
            else if (y>SCR_HEIGHT) y = -height;

            /*if (x < 0 || x > SCR_WIDTH - width) {
                stepX = -stepX;
                flipX = stepX > 0;
            }
            if (y < 0 || y > SCR_HEIGHT - height) {
                stepY = -stepY;
            }*/
        } else {
            width -= decreaseWidth;
            height -= decreaseHeight;
            if(SPAWN_X-Math.abs(stepX)/2 < x && x < SPAWN_X+Math.abs(stepX)/2 &&
                SPAWN_Y-Math.abs(stepY)/2 < y && y < SPAWN_Y+Math.abs(stepY)/2) {
                stepX = 0;
                stepY = 0;
                decreaseWidth = 0;
                decreaseHeight = 0;
            }
        }
    }

    void dead() {
        isDead = true;
        stepX = (SPAWN_X - x)/20;
        stepY = (SPAWN_Y - y)/20;
        decreaseWidth = width/20;
        decreaseHeight = height/20;
    }

    boolean hit(float tx, float ty){
        return x<tx && tx<x+width && y<ty && ty<y+height;
    }
}
