package org.bitoo.abit.mission.image;

import android.content.Context;
import android.util.Log;

import org.bitoo.abit.utils.XmlImageParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * A image standing for progress of mission consists of bitmap,
 * each bit in which is in one color.If the mission is completed,
 * these bits in image shows a specific object.
 *
 * Pixel in this class is {@link BitColor}.
 */
public class BitMapImage extends ProgressImage {
    private final static String TAG = "BitMapImage";

    private XmlImageParser imageParser = new XmlImageParser();

    BitMapImage() {
        id = -1;
    }
    BitMapImage(int id) {
        this.id = id;
    }
    BitMapImage(int height, int width, int id) {
        this(id);
        this.height = height;
        this.width = width;
    }

    @Override
    public void loadImage(InputStream is) {
        imageParser.loadImage(is);
        bitmap = imageParser.getBitmap();
        height = imageParser.getHeight();
        width = imageParser.getWidth();
    }

    @Override
    public boolean loadImage(Context context) {
        if(id != -1) {
            Log.d(TAG, "opening image " + id + "with res id of " + imageParser.findImageById(id));
            loadImage(context.getResources()
                    .openRawResource(imageParser.findImageById(id)));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void loadImage(Context context, String fileName)
            throws FileNotFoundException {
        loadImage(context.openFileInput(fileName));
    }

    @Override
    public void modifyImage(int id) throws IOException {

    }

    @Override
    public List<Pixel> getBitmap() {
        return bitmap;
    }
}
