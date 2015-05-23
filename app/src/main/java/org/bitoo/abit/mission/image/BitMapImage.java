package org.bitoo.abit.mission.image;

import android.content.Context;

import org.bitoo.abit.utils.XmlImageParser;

import java.io.FileNotFoundException;
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
    }
    BitMapImage(String imageName) {
        name = imageName;
    }
    BitMapImage(int height, int width) {
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
    public boolean loadImage(Context context) throws FileNotFoundException {
        if(name != null) {
            loadImage(context.openFileInput(name));
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
    public List<Pixel> getBitmap() {
        return bitmap;
    }
}
