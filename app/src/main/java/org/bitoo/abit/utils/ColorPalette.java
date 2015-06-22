package org.bitoo.abit.utils;

/**
 * Created by langley on 6/22/15.
 */
public class ColorPalette {
    private static final String TAG = "ColorPalette";

    public static int grayer(int color) {

        int blue = color & 0xff;
        color >>= 8;
        int green = color & 0xff;
        color >>= 8;
        int red = color & 0xff;

        int gray = ((red * 38 + green * 75 + blue * 15) >> 7) + 35;
        if(gray > 0xff)
            gray = 0xff;


        return (gray << 16) | (gray << 8) | gray | 0xcc000000;
    }
}
