package org.bitoo.abit.ui.custom;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.Mission;
import org.bitoo.abit.ui.ABitractorAcitivity;
import org.bitoo.abit.ui.AddMissionActivity;
import org.bitoo.abit.ui.custom.progress.MissionGridView;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Adapter of view pager for progress grids preview in {@link AddMissionActivity}.
 */
public class BitmapViewPagerAdapter extends PagerAdapter{

    List<String> xmlPathList;
    Activity context;

    public BitmapViewPagerAdapter(Activity context, List<String> list) {
        this.xmlPathList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return xmlPathList.size() + 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        if(position < xmlPathList.size()) {
            MissionGridView progressGrid = (MissionGridView) context.getLayoutInflater().inflate(R.layout.mgv_progress, container, false);
            try {
                Mission mission = new Mission(context, null, 0, xmlPathList.get(position), null, null);
                //mission.getProgressImage().loadImage(context);
                progressGrid.setMission(mission);
                progressGrid.build();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            container.addView(progressGrid);
            return progressGrid;
        } else {
            View view = context.getLayoutInflater().inflate(R.layout.iv_add, container, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ABitractorAcitivity.class);
                    ((AddMissionActivity)context).fragment.startActivityForResult(intent, AddMissionActivity.REQUEST_NEW_GRID);
                }
            });
            container.addView(view);
            return view;
        }
    }

    // TODO jank when scrolling
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
