package org.bitoo.abit.mission.image;

import org.bitoo.abit.storage.ImageStorage;

/**
 *  The image consists of grids, while one grid may contain
 *  one color(as a normal bitmap image) or a piece of the origin
 *  image(separated into pieces).
 *  You can implement ProgressImage as a type of separating.
 */
public abstract class ProgressImage implements ImageStorage {
    protected int id;

    /** Size of the image*/
    protected int height;
    protected int width;

}
