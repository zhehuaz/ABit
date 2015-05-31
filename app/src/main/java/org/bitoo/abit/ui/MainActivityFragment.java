package org.bitoo.abit.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gc.materialdesign.views.Button;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.image.Mission;
import org.bitoo.abit.ui.custom.HidingScrollListener;
import org.bitoo.abit.ui.custom.MissionListAdapter;
import org.bitoo.abit.utils.MissionSQLiteHelper;

import java.io.FileNotFoundException;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {
    private final static String TAG = "MainActivityFragment";

    private MissionSQLiteHelper sqLiteHelper;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Mission> missions;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mission_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button addButton = (Button) getActivity().findViewById(R.id.bt_add);
        addButton.setOnClickListener(this);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_mission_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        sqLiteHelper = new MissionSQLiteHelper(getActivity().getApplication());
        missions = sqLiteHelper.loadMissions();
        adapter = new MissionListAdapter(getActivity(), missions);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide(int dy) {
                ((MainActivity) getActivity()).moveUpToolBar(dy);
            }

            @Override
            public void onShow() {
                ((MainActivity) getActivity()).moveDownToolBar();
            }
        });
    }

    @Override
    public void onClick(View view) {
        byte[] progress = new byte[50];
        for(int i = 0;i < 50;i ++){
            if(i % 30 != 0)
                progress[i] = ~0;
        }
        Mission mission = null;
        try {
            mission = new Mission(getActivity(),
                    1, "减肥 测试",
                    System.currentTimeMillis(),
                    System.currentTimeMillis(),
                    "mario.xml",
                    progress);
            mission.setId(sqLiteHelper.addMission(mission));
            missions.add(mission);
            adapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(recyclerView.getChildCount() - 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
