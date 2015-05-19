package org.bitoo.abit.mission.image;

import java.io.IOException;

/**
 * A image standing for progress of mission consists of bitmap,
 * each bit in which is in one color.If the mission is completed,
 * these bits in image shows a specific object.
 */
public class BitMapImage extends ProgressImage {
    private final static String TAG = "BitMapImage";

    private ColorBit[][] bitMap;

    BitMapImage(int height, int width, int id){
        this.height = height;
        this.width = width;
        this.id = id;
        bitMap = new ColorBit[height][width];
    }

    @Override
    public void LoadImage() throws IOException {

    }

    @Override
    public void modifyImage(int id) throws IOException {

    }


    public class ColorBit{
        int x;
        int y;
        int color = 0xffffff;// in hex
    }
}
