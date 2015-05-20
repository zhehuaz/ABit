package org.bitoo.abit.mission.image;

/**
 * The element in a {@link ProgressImage}, which contain a
 * two-dimentional array of Pixels.
 */
public interface Pixel<T> {
    /** Position of pixel in image*/
    int getX();
    int getY();

    /** 'T' stands for data structure of the element.*/
    T getPixel();
}
