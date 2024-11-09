package ru.saturday.satcow;
import static ru.saturday.satcow.Main.*;

public class Cow {
    float x, y;
    float width, height;
    float stepX = 5, stepY = 5;

    public Cow(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    void fly(){
        x+=stepX;
        y+=stepY;
        if(x<0 || x>SCR_WIDTH-width) stepX = -stepX;
        if(y<0 || y>SCR_HEIGHT-height) stepY = -stepY;
    }
}
