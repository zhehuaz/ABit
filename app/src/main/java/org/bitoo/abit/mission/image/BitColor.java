package org.bitoo.abit.mission.image;

/**
 * A Bit in a BitMap Image contains its position and color.
 */
public class BitColor {
    int x;
    int y;
    String color;

    public BitColor(int x,int y,String color){
        this.x = x;
        this.y = y;
        this.color = color;
    }
}
