package org.bitoo.abit.ui.custom;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.image.Mission;
import org.bitoo.abit.mission.image.Pixel;

import java.util.List;

/**
 * The adapter of bitmap gridView.
 */
public class BitMapAdapter extends BaseAdapter {
    private final static String TAG = "BitMapAdapter";
    Context context;

    /** I can get {@link Mission#progress} here.*/
    Mission mission;

    /** Pixels of the bitmap grid.*/
    List<Pixel> bitmap;

    /**
     * Necessary information to paint the bitmap grid.
     * @param context is to get {@link java.util.zip.Inflater}.
     * @param mission Data of a mission.
     */
    public BitMapAdapter(Context context, Mission mission) {
        this.context = context;
        this.mission = mission;
        bitmap = mission.getProgressImage().getBitmap();
    }

    @Override
    public int getCount() {
        return bitmap.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_pixel, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_pixel);
        if(position <= mission.getProgress())
            imageView.setBackgroundColor((int) bitmap.get(position).getPixel());
        else
            imageView.setBackgroundColor(Color.GRAY);
        return convertView;
    }
}
