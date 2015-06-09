package org.bitoo.abit.mission.image;

import android.content.Context;

import org.bitoo.abit.utils.ImageXmlParser;

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

    private ImageXmlParser imageParser = new ImageXmlParser();

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

    /**
     * I assume that name of image is initialized.
     * @param context is used to get resource.
     * @throws FileNotFoundException if the {@link #name} exists.
     */
    @Override
    public void loadImage(Context context) throws FileNotFoundException {
        if(name != null) {
            loadImage(context.openFileInput(name));
        } else {
            throw new FileNotFoundException();
        }
    }

    /**
     * I'm
     * @param context is used to openFileInput().
     * @param fileName Name of XML file.
     * @throws FileNotFoundException
     */
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
