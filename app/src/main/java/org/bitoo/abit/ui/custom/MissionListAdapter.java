package org.bitoo.abit.ui.custom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.image.Mission;
import org.bitoo.abit.ui.DetailedMissionActivity;
import org.bitoo.abit.ui.HomeFragment;
import org.bitoo.abit.ui.MainActivity;

import java.io.File;
import java.sql.Date;
import java.util.List;

/**
 * Adapter to mission list in {@link HomeFragment} using CardView.
 */
public class MissionListAdapter extends RecyclerView.Adapter<MissionListAdapter.ViewHolder> {
    private static final int ANIMATED_ITEMS_COUNT = 4;
    private int lastAnimatedPosition = -1;

    List<Mission> missions;
    public Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleText;
        public TextView mDateText;
        public SimpleDraweeView mDraweeView;
        public CardView mCard;

        /**
         * In mCard, text of date and bottom panel of mCard has a dynamic color
         * determined by the color of picture you use.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            mDateText = (TextView) itemView.findViewById(R.id.tv_mission_date);
            mTitleText = (TextView) itemView.findViewById(R.id.tv_mission_title);
            mDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.dv_mission_preview);
            mCard = (CardView) itemView.findViewById(R.id.cd_layout);

            if (mCard != null) {
                mCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, DetailedMissionActivity.class);
                        intent.putExtra(MainActivity.MISSION_ID, missions.get(getAdapterPosition()).getId());
                        ((MainActivity) context).mainFragment.startActivityForResult(intent, MainActivity.REQUEST_IS_DELETE);
                    }
                });
            }
        }
    }

    public MissionListAdapter(Context context, List<Mission> missions) {
        this.context = context;
        this.missions = missions;
    }

    @Override
    public MissionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cd_mission_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final MissionListAdapter.ViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        holder.mTitleText.setText(missions.get(position).getTitle());
        holder.mDateText.setText(new Date(missions.get(position).getCreateDate()).toString());
        holder.mDraweeView.setImageURI(Uri.fromFile(new File(missions.get(position).getThemeImagePath())));

        if (holder.mDraweeView != null) {
            // decode image async or jank
            new AsyncTask<String, Integer, Bitmap>() {
                @Override
                protected Bitmap doInBackground(String... params) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 5;// sample the image to acceleration
                    return BitmapFactory.decodeFile(params[0], options);
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    if (bitmap != null) {
                        new Palette.Builder(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                Palette.Swatch lightVibrant = palette.getLightVibrantSwatch();
                                Palette.Swatch vibrant = palette.getVibrantSwatch();
                                if (holder.mCard != null) {
                                    if (lightVibrant != null) {
                                        holder.mCard.setCardBackgroundColor(lightVibrant.getRgb());
                                    } else if (vibrant != null) {
                                        holder.mCard.setCardBackgroundColor(vibrant.getRgb());
                                    } else {
                                        holder.mCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                                    }
                                }
                                if (holder.mDateText != null) {
                                    if (lightVibrant != null) {
                                        holder.mDateText.setTextColor(lightVibrant.getTitleTextColor());
                                    } else if (vibrant != null) {
                                        holder.mDateText.setTextColor(vibrant.getTitleTextColor());
                                    } else {
                                        holder.mDateText.setTextColor(Color.parseColor("#DCDCDC"));
                                    }
                                }
                            }
                        });
                    }
                }
            }.execute(missions.get(position).getThemeImagePath());
        }
    }

    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        /** Mission list animation.*/
        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(300 + position * 100);
            view.setAlpha(.3f);
            view.animate()
                    .translationY(0)
                    .alpha((float) 1.0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(1000 + position * 100)
                    .setStartDelay(100 + position * 30)
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return missions.size();
    }
}
