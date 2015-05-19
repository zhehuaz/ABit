package org.bitoo.abit.storage;

import org.bitoo.abit.mission.image.ProgressImage;

import java.io.IOException;

/**
 * IO class of {@link ProgressImage}, defines all the operations
 * on local storage for each mission's image.
 */
public interface ImageStorage {
    /**
     * Read Image by id from local storage to initialize image bitmap.
     */
    void LoadImage() throws IOException;

    /**
     * replace image source by id
     */
    void modifyImage(int id) throws IOException;

}
