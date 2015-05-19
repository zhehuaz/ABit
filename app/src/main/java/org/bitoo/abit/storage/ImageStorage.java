package org.bitoo.abit.storage;

import org.bitoo.abit.mission.image.ProgressImage;

/**
 * IO class of {@link ProgressImage}, defines all the operations
 * of image on local storage.
 */
public interface ImageStorage {
    /**
     * Read Image by id from local storage.
     * @param id Image id.
     * @return Image got from storage.
     */
    ProgressImage readImage(int id);

    /**
     * Modify image source
     */
    void modifyImage(int id, ProgressImage image);

    /**
     * Delete an image.
     */
    void deleteImage(int id);

    /**
     * insert a new image.
     */
    void insertImage(ProgressImage image);
}
