package org.bitoo.abit.ui.custom;

import android.content.Context;
import android.content.pm.PermissionInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.image.Mission;
import org.bitoo.abit.mission.image.Pixel;

import java.util.List;

/**
 * Created by Administrator on 2015/9/29.
 */
public class BitmapAdapter2 extends RecyclerView.Adapter<BitmapAdapter2.BitmapViewHolder>{
    private final static String TAG = "BitmapAdapter2";
    Context context;

    /** I can get {@link Mission#progressMask} here.*/
    Mission mission;

    /** Pixels of the bitmap grid.*/
    List<Pixel> bitmap;

    public BitmapAdapter2(Context context, Mission mission) {
        this.context = context;
        this.mission = mission;
        bitmap = mission.getProgressImage().getBitmap();
    }

    @Override
    public BitmapViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BitmapViewHolder(LayoutInflater.from(context).inflate(R.layout.grid_pixel, parent, false));
    }

    @Override
    public void onBindViewHolder(BitmapViewHolder holder, int position) {
        holder.pixelView.setBackgroundColor((int) bitmap.get(position).getPixel());
    }

    @Override
    public int getItemCount() {
        return bitmap.size();
    }

    public static class BitmapViewHolder extends RecyclerView.ViewHolder{
        ImageView pixelView;
        public BitmapViewHolder(View itemView) {
            super(itemView);
            pixelView = (ImageView) itemView.findViewById(R.id.img_pixel);
        }
    }

}
