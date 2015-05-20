package org.bitoo.abit.mission.image;

import android.graphics.Color;

/**
 * A Bit in a BitMap Image contains its position and color.
 */
public class BitColor implements Pixel<Integer>{
    int x;
    int y;
    int color;

    public BitColor(int x,int y,String color){
        this.x = x;
        this.y = y;
        this.color = Color.parseColor(color);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Integer getPixel() {
        return color;
    }
}
