package org.bitoo.abit.mission.image;

import org.bitoo.abit.storage.ImageStorage;

/**
 *  The image consists of grids, while one grid may contain
 *  one color(as a normal bitmap image) or a piece of the origin
 *  image(separated into pieces).
 *  The elements of ProgressImage is {@link #bitmap}
 *  in data structure of {@link Pixel}
 */
public abstract class ProgressImage implements ImageStorage {
    /** This is ID of R.row in /gen */
    protected int id;

    /** Attention, this name is usually complete fileName.*/
    protected String name;

    /** Size of the image*/
    protected int height;
    protected int width;

    protected Pixel[][] bitmap;

    public int getId() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
