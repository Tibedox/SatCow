package ru.saturday.satcow;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class CowButton {
    float x, y;
    float width, height;
    String text;
    BitmapFont font;

    public CowButton(String text, BitmapFont font, float x, float y) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.font = font;
        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        width = glyphLayout.width;
        height = glyphLayout.height;
    }

    boolean hit(float tx, float ty){
        return x<tx && tx<x+width && y-height<ty && ty<y;
    }
}
