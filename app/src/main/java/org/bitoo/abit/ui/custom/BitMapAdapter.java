package org.bitoo.abit.ui.custom;

import android.content.Context;
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
 * Created by admin on 2015/9/17.
 */
public class BitMapAdapter extends BaseAdapter {
    private final static String TAG = "ProgressBitMapAdapter";
    Context context;

    /** I can get {@link Mission#progressMask} here.*/
    Mission mission;

    /** Pixels of the bitmap grid.*/
    List<Pixel> bitmap;

    /**
     * Necessary information to paint the bitmap grid.
     * @param context is to get {@link LayoutInflater}.
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
        return bitmap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_pixel, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_pixel);
        imageView.setBackgroundColor((int) bitmap.get(position).getPixel());
        return convertView;
    }
}
