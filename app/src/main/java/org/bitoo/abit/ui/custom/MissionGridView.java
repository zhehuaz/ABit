package org.bitoo.abit.ui.custom;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;

import org.bitoo.abit.mission.image.Mission;

/**
 * Created by Administrator on 2015/9/29.
 */
public class MissionGridView extends RecyclerView{

    private BitmapAdapter2 adapter;
    private Mission mission;

    public MissionGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MissionGridView(Context context) {
        super(context);
    }

    public MissionGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMission(Mission mission) {
        this.mission = mission;
        adapter = new BitmapAdapter2(getContext(), mission);
    }

    public void setHeaderView(View header) {
        adapter.addHeader(header);
    }

    public void setFooterView(View footer) {
        adapter.addFooter(footer);
    }

    public void build()
    {
        if(adapter != null)
            this.setAdapter(adapter);
        else
            throw new RuntimeException();
        final GridLayoutManager manager = new GridLayoutManager(getContext(), mission.getProgressImage().getWidth());

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? manager.getSpanCount() : 1;
            }
        });

        this.setLayoutManager(manager);
    }
}
