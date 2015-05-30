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

import java.util.List;

/**
 * Created by langley on 5/29/15.
 */
public class MissionListAdapter extends RecyclerView.Adapter<MissionListAdapter.ViewHolder> {
    List<Mission> missions;
    public Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleText;
        public TextView mDateText;
        public ImageView mImageView;
        public CardView card;

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
                        context.startActivity(intent);
                    }
                });
            }

            if(mImageView != null) {
                Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
                if (bitmap != null) {
                    Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            Palette.Swatch light = palette.getLightVibrantSwatch();
                            Palette.Swatch dark = palette.getDarkMutedSwatch();
                            if (card != null) {
                                card.setCardBackgroundColor(light.getRgb());
                            }
                            if (mDateText != null && dark != null) {
                                mDateText.setTextColor(dark.getTitleTextColor());
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
    }

    @Override
    public int getItemCount() {
        return missions.size();
    }
}
