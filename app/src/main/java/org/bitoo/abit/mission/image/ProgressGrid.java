package org.bitoo.abit.mission.image;

import java.util.List;

/**
 *  The image consists of grids, while one grid may contain
 *  one color(as a normal bitmap image) or a piece of the origin
 *  image(separated into pieces).
 *  The elements of ProgressGrid is {@link #bitmap}
 *  in data structure of {@link Pixel}
 */
public abstract class ProgressGrid<T extends Pixel> {

    /** Attention, this name is usually complete fileName.*/
    protected String name;

    /** Size of the image*/
    protected int height;
    protected int width;

    /** Amount of Pixels*/
    protected int amount;

    protected List<T> bitmap;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getAmount() {
        return amount;
    }

    public abstract List<T> getBitmap();

    public String getName() {
        return name;
    }

}
