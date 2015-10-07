package org.bitoo.abit.mission.image;

import android.content.Context;

import org.bitoo.abit.mission.BitmapXmlParser;

import java.io.File;
import java.io.FileInputStream;
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
public class BitmapImage extends ProgressImage {
    private final static String TAG = "BitmapImage";
    public final static String STORAGE_PATH = "/bitmaps/";

    private BitmapXmlParser imageParser = new BitmapXmlParser();
    private Context context;

    public BitmapImage() {
    }

    @Deprecated
    public BitmapImage(String imageName) {
        this.name = imageName;
    }

    public BitmapImage(Context context, String imageName) throws FileNotFoundException {
        this.context = context;
        name = imageName;
        if(bitmap == null) {
            this.loadImage();
        }
    }
    public BitmapImage(int height, int width, int amount) {
        this.height = height;
        this.width = width;
        this.amount = amount;
    }

    public void loadImage() throws FileNotFoundException {
        if(context != null && name != null)
            loadImage(context);
    }

    public void loadImage(InputStream is) {
        if(is != null) {
            imageParser.loadImage(is);
            bitmap = imageParser.getBitmap();
            height = imageParser.getHeight();
            width = imageParser.getWidth();
            amount = imageParser.getAmount();
        }
    }

    @Deprecated
    public void loadImage(Context context, String fileName) throws FileNotFoundException {
        if (fileName != null && context != null) {
            this.name = fileName;
            loadImage(context);
        }
    }

    /**
     * I assume that name of image is initialized.
     * @param context is used to get resource.
     * @throws FileNotFoundException if the {@link #name} exists.
     */
    public void loadImage(Context context) throws FileNotFoundException {
        if (name != null && context != null) {
            loadImage(new FileInputStream(new File(context.getFilesDir().getAbsolutePath() + STORAGE_PATH + name)));
        } else {
            throw new FileNotFoundException();
        }
    }

    @Override
    public List<Pixel> getBitmap() {
        return bitmap;
    }
}
