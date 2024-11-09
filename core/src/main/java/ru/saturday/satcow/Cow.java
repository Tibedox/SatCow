package ru.saturday.satcow;
import static ru.saturday.satcow.Main.*;

public class Cow {
    float x = 140, y = 210;
    float width = 300;
    float height = 300;
    float stepX = 5, stepY = 5;

    void fly(){
        x+=stepX;
        y+=stepY;
        if(x<0 || x>SCR_WIDTH-width) stepX = -stepX;
        if(y<0 || y>SCR_HEIGHT-height) stepY = -stepY;
    }
}
