package org.bitoo.abit.mission.image;

/**
 * A Bit in a BitMap Image contains its position and color.
 */
public class BitColor implements Pixel<String>{
    int x;
    int y;
    String color;

    public BitColor(int x,int y,String color){
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
    public String getPixel() {
        return color;
    }
}
