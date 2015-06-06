package org.bitoo.abit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.Iterator;
import java.util.List;

public class MainActivityFragment extends Fragment implements View.OnClickListener {
    private final static String TAG = "MainActivityFragment";


    private MissionSQLiteHelper sqLiteHelper;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Mission> missions;
    private Intent intent;

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
        Mission mission = null;
        try {
            mission = new Mission(getActivity(),
                    "减肥 测试",
                    System.currentTimeMillis(),
                    "mario.xml");
            mission.setId(sqLiteHelper.addMission(mission));
            missions.add(mission);
            adapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(recyclerView.getChildCount() - 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case MainActivity.REQUEST_IS_DELETE :
                if(data.getBooleanExtra(MainActivity.ACTION_IS_DELETE, false)) {
                    long id = data.getLongExtra(MainActivity.ACTION_ID_DELETED, -1);
                    if(id != -1) {
                        for(Mission mission : missions) {
                            if (mission.getId() == id) {
                                missions.remove(mission);
                                break;
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
