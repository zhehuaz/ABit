package org.bitoo.abit.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Handle all types of files to encapsulate file operations.
 */
public class FileHandler {
    /**
     * Copy file to internal storage.
     * @param is input stream of source file
     * @param fileName of new file
     */
    public static void copyFile(Context context, InputStream is, String fileName)
            throws IOException {
        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);

        int readCount;
        byte[] buff = new byte[1024];
        while((readCount = is.read(buff)) > 0) {
            fos.write(buff, 0, readCount);
        }
        fos.close();
    }
}
