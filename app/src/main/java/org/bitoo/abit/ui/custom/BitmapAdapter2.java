package org.bitoo.abit.ui.custom;

import android.content.Context;
import android.content.pm.PermissionInfo;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
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
public class BitmapAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final static String TAG = "BitmapAdapter2";

    private final static int VIEW_TYPE_GRID = 0;
    private final static int VIEW_TYPE_HEADER = 1;
    private final static int VIEW_TYPE_FOOTER = 2;



    private Context context;

    /** I can get {@link Mission#progressMask} here.*/
    private Mission mission;

    /** Pixels of the bitmap grid.*/
    private List<Pixel> bitmap;

    private View header;
    private View footer;
    private boolean hasHeader;
    private boolean hasFooter;

    public BitmapAdapter2(Context context, Mission mission) {
        this.context = context;
        this.mission = mission;
        bitmap = mission.getProgressImage().getBitmap();
        header = LayoutInflater.from(context).inflate(R.layout.empty, null);
        footer = LayoutInflater.from(context).inflate(R.layout.empty, null);
        hasHeader = false;
        hasFooter = false;
    }

    public void addHeader(View view) {
        header = view;
        hasHeader = true;
    }

    public void addFooter(View view) {
        footer = view;
        hasFooter = true;
    }

    public boolean hasHeader() {
        return hasHeader;
    }

    public boolean hasFooter() {
        return hasFooter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_GRID :
                return new BitmapViewHolder(LayoutInflater.from(context).inflate(R.layout.grid_pixel, parent, false));
            case VIEW_TYPE_FOOTER :
                if(!hasFooter)
                    footer.setVisibility(View.GONE);
                return new FooterViewHolder(footer);
            case VIEW_TYPE_HEADER :
                if(!hasHeader)
                    header.setVisibility(View.GONE);
                return new HeaderViewHolder(header);
            default:
                Log.e(TAG, "unknown view type");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BitmapViewHolder)
            ((BitmapViewHolder)holder).pixelView.setBackgroundColor((int) bitmap.get(position - 1).getPixel());
    }

    @Override
    public int getItemCount() {
        return bitmap.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return VIEW_TYPE_HEADER;
        else if(position <= bitmap.size())
            return VIEW_TYPE_GRID;
        else if(position == bitmap.size() + 1)
            return VIEW_TYPE_FOOTER;
        return -1;
    }

    public static class BitmapViewHolder extends RecyclerView.ViewHolder {
        ImageView pixelView;
        public BitmapViewHolder(View itemView) {
            super(itemView);
            pixelView = (ImageView) itemView.findViewById(R.id.img_pixel);
        }
    }

    public static class HeaderViewHolder extends  RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
