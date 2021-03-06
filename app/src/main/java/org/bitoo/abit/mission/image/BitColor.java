package org.bitoo.abit.mission.image;

/**
 * A Bit in a BitMap Image contains its position and color.
 */
public class BitColor implements Pixel<Integer>{
    /** X-axis of the pixel square.*/
    int x;

    /** Y-axis of the pixel square.*/
    int y;

    /** Color of the pixel square.*/
    int color;

    public BitColor(int x,int y,int color){
        this.x = x;
        this.y = y;
        this.color = color;
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
