package org.bitoo.abit.storage;

import android.content.Context;

import org.bitoo.abit.mission.image.ProgressImage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * IO interface of {@link ProgressImage}, defines all the operations
 * on local storage for each mission's image.
 */
public interface ImageStorage {
    /**
     * Read Image by id from local storage to initialize bitmap image.
     * @param context is used to getResourse().
     * @return If loading image successfully.
     */
    boolean loadImage(Context context);

    /**
     * Read Image by InputStream from local storage to initialize bitmap image.
     * @param is InputStream of XML file.
     */
    void loadImage(InputStream is);

    /**
     * Read Image by FileName from internal storage to initialize bitmap image.
     * @param context is used to openFileInput().
     * @param fileName Name of XML file.
     */
    void loadImage(Context context, String fileName) throws FileNotFoundException;

    /**
     * Replace image source by id.
     */
    void modifyImage(int id) throws IOException;

}
