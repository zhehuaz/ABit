package org.bitoo.abit.ui.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.Mission;
import org.bitoo.abit.utils.ColorPalette;

import java.io.FileNotFoundException;

/**
 * The adapter of bitmap gridView.
 */
public class ProgressBitmapAdapter extends BitmapAdapter {
    private final static String TAG = "ProgressBitmapAdapter";

    /**
     * Necessary information to paint the bitmap grid.
     *
     * @param context is to get {@link LayoutInflater}.
     * @param mission Data of a mission.
     */
    public ProgressBitmapAdapter(Context context, Mission mission) {
        super(context, mission);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_pixel, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_pixel);

        if(mission.getProgressMask(position)) {
            imageView.setBackgroundColor((int) bitmap.get(position).getPixel());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Toast.makeText(context,position + " : " + mission.loadTweet(position).getText(), Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
        else
            imageView.setBackgroundColor(ColorPalette.grayer((int) bitmap.get(position).getPixel()));

        AlphaAnimation animation = new AlphaAnimation(0, 1.f);
        animation.setDuration(350);
        animation.setStartOffset((position * 2));
        imageView.startAnimation(animation);
        return convertView;
    }
}
