package org.bitoo.abit.ui.custom.progress;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.Mission;
import org.bitoo.abit.mission.image.Pixel;
import org.bitoo.abit.mission.Tweet;
import org.bitoo.abit.utils.ColorPalette;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * The adapter for {@link MissionGridView}.
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
    private List<Pixel> bitmap = null;

    private View header;
    private View footer;
    private boolean hasHeader;
    private boolean hasFooter;

    public BitmapAdapter2(Context context, Mission mission) {
        this.context = context;
        this.mission = mission;
        if(mission != null)
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

    public void setHeaderVisibility(int visibility) {
        header.setVisibility(visibility);
    }

    public void setFooterVisibility(int visibility) {
        header.setVisibility(visibility);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_GRID :
                return new BitmapViewHolder(LayoutInflater.from(context).inflate(R.layout.grid_pixel, parent, false));
            case VIEW_TYPE_FOOTER :
                return new FooterViewHolder(footer);
            case VIEW_TYPE_HEADER :
                return new HeaderViewHolder(header);
            default:
                Log.e(TAG, "unknown view type");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof BitmapViewHolder) {
            if(mission.getTitle() != null) {
                if(mission.getProgressMask(position - 1)) {
                    ((BitmapViewHolder) holder).pixelView.setBackgroundColor((int) bitmap.get(position - 1).getPixel());
                    ((BitmapViewHolder) holder).pixelView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Tweet quaryResult = mission.loadTweet(position - 1);
                                if(quaryResult != null)
                                    Toast.makeText(context, position + " : " + mission.loadTweet(position - 1).getText(), Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(context, "Unable to show", Toast.LENGTH_SHORT).show();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                else
                    ((BitmapViewHolder) holder).pixelView.setBackgroundColor(ColorPalette.grayer((int) bitmap.get(position - 1).getPixel()));
            } else {
                ((BitmapViewHolder) holder).pixelView.setBackgroundColor((int) bitmap.get(position - 1).getPixel());
            }

        }
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
