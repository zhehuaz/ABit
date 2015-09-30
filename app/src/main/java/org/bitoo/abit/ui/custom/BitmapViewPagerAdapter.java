package org.bitoo.abit.ui.custom;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.image.Mission;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by admin on 2015/9/17.
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
        return xmlPathList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //GridView gridView = (GridView)context.getLayoutInflater().inflate(R.layout.gv_preview, null);
        // TODO Change variable name
        MissionGridView recyclerView = (MissionGridView) context.getLayoutInflater().inflate(R.layout.mgv_progress, container, false);
        try {
            Mission mission = new Mission(context, null, 0, xmlPathList.get(position), null, null);
            mission.getProgressImage().loadImage(context);
            //gridView.setAdapter(new BitmapAdapter(context, mission));
            //gridView.setNumColumns(mission.getProgressImage().getWidth());
//            recyclerView.setAdapter(new BitmapAdapter2(context, mission));
//            recyclerView.setLayoutManager(new GridLayoutManager(context, mission.getProgressImage().getWidth()));
            recyclerView.setMission(mission);
            recyclerView.build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        container.addView(recyclerView);

        return recyclerView;
    }
}
