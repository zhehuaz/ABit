package org.bitoo.abit.ui.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.image.Mission;
import org.bitoo.abit.ui.DetailedMissionActivity;
import org.bitoo.abit.ui.MainActivity;
import org.bitoo.abit.utils.LayoutHelper;

import java.io.File;
import java.sql.Date;
import java.util.List;

/**
 * Adapter to mission list in {@link org.bitoo.abit.ui.MainActivityFragment} using CardView.
 */
public class MissionListAdapter extends RecyclerView.Adapter<MissionListAdapter.ViewHolder> {
    private static final int ANIMATED_ITEMS_COUNT = 4;
    private int lastAnimatedPosition = -1;

    List<Mission> missions;
    public Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleText;
        public TextView mDateText;
        public ImageView mImageView;
        public CardView card;

        /**
         * In card, text of date and bottom panel of card has a dynamic color
         * determined by the color of picture you use.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            mDateText = (TextView) itemView.findViewById(R.id.tv_mission_date);
            mTitleText = (TextView) itemView.findViewById(R.id.tv_mission_title);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_mission_preview);
            card = (CardView) itemView.findViewById(R.id.cd_layout);

            if(card != null) {
                card.setOnClickListener(new View.OnClickListener() {
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

    public MissionListAdapter(Context context ,List<Mission> missions) {
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
        holder.mImageView.setImageBitmap(BitmapFactory.decodeFile(missions.get(position).getThemeImagePath()));
        holder.mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if(holder.mImageView != null) {
            Bitmap bitmap = ((BitmapDrawable) holder.mImageView.getDrawable()).getBitmap();
            if (bitmap != null) {
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch lightMuted = palette.getLightMutedSwatch();
                        Palette.Swatch darkMuted = palette.getDarkMutedSwatch();
                        Palette.Swatch lightVibrant = palette.getLightVibrantSwatch();
                        Palette.Swatch darkVibrant = palette.getDarkVibrantSwatch();
                        if (holder.card != null) {
                            if (lightVibrant != null) {
                                holder.card.setCardBackgroundColor(lightVibrant.getRgb());
                            } else if (lightMuted != null) {
                                holder.card.setCardBackgroundColor(lightMuted.getRgb());
                            }
                        }
                        if (holder.mDateText != null) {
                            if (darkMuted != null) {
                                holder.mDateText.setTextColor(darkMuted.getTitleTextColor());
                            } else if (darkVibrant != null) {
                                holder.mDateText.setTextColor(darkVibrant.getTitleTextColor());
                            }
                        }
                    }
                });
            }
        }
    }

    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        /** Mission list animation.*/
        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(LayoutHelper.getScreenHeight((Activity) context));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(1000 + position * 120)
                    .setStartDelay(400 + position * 20)
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return missions.size();
    }
}
