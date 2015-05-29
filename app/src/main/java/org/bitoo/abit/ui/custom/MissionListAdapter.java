package org.bitoo.abit.ui.custom;

import android.content.Context;
import android.content.Intent;
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
        public TextView mTextView;
        public ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_mission_title);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_mission_preview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailedMissionActivity.class);
                    intent.putExtra(MainActivity.MISSION_ID, missions.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
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
        holder.mTextView.setText(missions.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return missions.size();
    }
}
