package org.bitoo.abit.ui.custom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.image.Mission;
import org.bitoo.abit.mission.image.Pixel;
import org.bitoo.abit.utils.ColorPalette;
import org.bitoo.abit.utils.TweetXmlParser;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * The adapter of bitmap gridView.
 */
public class BitMapAdapter extends BaseAdapter {
    private final static String TAG = "BitMapAdapter";
    Context context;

    /** I can get {@link Mission#progressMask} here.*/
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
