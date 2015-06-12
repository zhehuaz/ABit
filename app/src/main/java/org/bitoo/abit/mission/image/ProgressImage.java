package org.bitoo.abit.mission.image;

import org.bitoo.abit.storage.ImageStorage;

import java.util.List;

/**
 *  The image consists of grids, while one grid may contain
 *  one color(as a normal bitmap image) or a piece of the origin
 *  image(separated into pieces).
 *  The elements of ProgressImage is {@link #bitmap}
 *  in data structure of {@link Pixel}
 */
public abstract class ProgressImage implements ImageStorage {

    /** Attention, this name is usually complete fileName.*/
    protected String name;

    /** Size of the image*/
    protected int height;
    protected int width;

    /** Amount of Pixels*/
    protected int amount;

    protected List<Pixel> bitmap;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getAmount() {
        return amount;
    }

    public abstract List<Pixel> getBitmap();

    public String getName() {
        return name;
    }

}
