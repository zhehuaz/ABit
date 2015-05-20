package org.bitoo.abit.mission.image;

import android.content.Context;

import org.bitoo.abit.utils.XmlImageParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A image standing for progress of mission consists of bitmap,
 * each bit in which is in one color.If the mission is completed,
 * these bits in image shows a specific object.
 *
 * Pixel in this class is {@link BitColor}.
 */
public class BitMapImage extends ProgressImage {
    private final static String TAG = "BitMapImage";

    private XmlImageParser imageParser;

    BitMapImage(int id) {
        this.id = id;
    }
    BitMapImage(int height, int width, int id) {
        this.height = height;
        this.width = width;
        this.id = id;
    }

    @Override
    public void loadImage(InputStream is) {
        imageParser = new XmlImageParser();
        imageParser.loadImage(is);
        bitmap = imageParser.getBitmap();
        height = imageParser.getHeight();
        width = imageParser.getWidth();
    }

    @Override
    public void loadImage(Context context) {
        loadImage(context.getResources().openRawResource(id));
    }

    @Override
    public void loadImage(Context context, String fileName) throws FileNotFoundException {
        loadImage(context.openFileInput(fileName));
    }

    @Override
    public void modifyImage(int id) throws IOException {

    }

    public BitColor[][] getBitmap() {
        return (BitColor[][])bitmap;
    }
}
