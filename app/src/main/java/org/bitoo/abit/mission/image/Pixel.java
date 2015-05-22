package org.bitoo.abit.mission.image;

/**
 * The element in a {@link ProgressImage}, which contain an
 * array of Pixels.
 */
public interface Pixel {
    /** Position of pixel in image*/
    int getX();
    int getY();

    /** 'T' stands for data structure of the element.*/
    Object getPixel();
}
