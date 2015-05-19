package org.bitoo.abit.mission.image;

/**
 * A image standing for progress of mission consists of bitmap,
 * each bit in which is in one color.If the mission is completed,
 * these bits in image shows a specific object.
 */
public class BitMapImage extends ProgressImage {


    @Override
    public ProgressImage readImage(int id) {
        return null;
    }

    @Override
    public void modifyImage(int id, ProgressImage image) {

    }

    @Override
    public void deleteImage(int id) {

    }

    @Override
    public void insertImage(ProgressImage image) {

    }
}
