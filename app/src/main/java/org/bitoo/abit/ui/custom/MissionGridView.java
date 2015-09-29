package org.bitoo.abit.ui.custom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import org.bitoo.abit.mission.image.Mission;

/**
 * Created by Administrator on 2015/9/29.
 */
public class MissionGridView extends RecyclerView{

    private Mission mission = null;

    private Adapter adapter;

    public MissionGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setMission(Mission mission) {
        this.mission = mission;

        adapter = new BitmapAdapter2(getContext(), mission);
        this.setAdapter(adapter);
        //this.notify();
    }
}
