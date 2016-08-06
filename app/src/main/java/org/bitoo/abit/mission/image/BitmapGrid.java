package org.bitoo.abit.mission.image;

import android.content.Context;

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
public class BitmapGrid extends ProgressGrid<BitColor> {
    private final static String TAG = "BitmapGrid";
    public final static String STORAGE_PATH = "/bitmaps/";

    private BitmapXmlParser imageParser = new BitmapXmlParser();
    private Context context;

    public BitmapGrid() {
    }

    @Deprecated
    public BitmapGrid(String imageName) {
        this.name = imageName;
    }

    public BitmapGrid(Context context, String imageName) throws FileNotFoundException {
        this.context = context;
        name = imageName;
        if(bitmap == null) {
            this.loadImage();
        }
    }
    public BitmapGrid(int height, int width, int amount) {
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
    public List<BitColor> getBitmap() {
        return bitmap;
    }
}
