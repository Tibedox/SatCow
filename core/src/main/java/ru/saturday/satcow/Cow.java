package ru.saturday.satcow;
import static ru.saturday.satcow.Main.*;

import com.badlogic.gdx.math.MathUtils;

public class Cow {
    float x, y;
    float width, height;
    private float stepX, stepY;

    public Cow(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        stepX = MathUtils.random(-5, 5f);
        stepY = MathUtils.random(-5, 5f);
    }

    void fly(){
        x+=stepX;
        y+=stepY;
        if(x<0 || x>SCR_WIDTH-width) stepX = -stepX;
        if(y<0 || y>SCR_HEIGHT-height) stepY = -stepY;
    }
}
