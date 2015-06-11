package org.bitoo.abit.ui.custom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.image.Mission;
import org.bitoo.abit.ui.DetailedMissionActivity;
import org.bitoo.abit.ui.MainActivity;

import java.sql.Date;
import java.util.List;

/**
 * Adapter to mission list in {@link org.bitoo.abit.ui.MainActivityFragment} using CardView.
 */
public class MissionListAdapter extends RecyclerView.Adapter<MissionListAdapter.ViewHolder> {
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
                        ((MainActivity)context).mainFragment.startActivityForResult(intent, MainActivity.REQUEST_IS_DELETE);
                    }
                });
            }

            if(mImageView != null) {
                Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
                if (bitmap != null) {
                    Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            Palette.Swatch lightMuted = palette.getLightMutedSwatch();
                            Palette.Swatch darkMuted = palette.getDarkMutedSwatch();
                            Palette.Swatch lightVibrant = palette.getLightVibrantSwatch();
                            Palette.Swatch darkVibrant = palette.getDarkVibrantSwatch();
                            if (card != null) {
                                if(lightVibrant != null) {
                                    card.setCardBackgroundColor(lightVibrant.getRgb());
                                } else if(lightMuted != null) {
                                    card.setCardBackgroundColor(lightMuted.getRgb());
                                }
                            }
                            if (mDateText != null) {
                                if(darkMuted != null) {
                                    mDateText.setTextColor(darkMuted.getTitleTextColor());
                                } else if(darkVibrant != null) {
                                    mDateText.setTextColor(darkVibrant.getTitleTextColor());
                                }
                            }
                        }
                    });
                }
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
    public void onBindViewHolder(MissionListAdapter.ViewHolder holder, int position) {
        holder.mTitleText.setText(missions.get(position).getTitle());
        holder.mDateText.setText(new Date(missions.get(position).getCreateDate()).toString());
    }

    @Override
    public int getItemCount() {
        return missions.size();
    }
}
