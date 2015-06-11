package org.bitoo.abit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFloatSmall;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.image.Mission;
import org.bitoo.abit.mission.image.Tweet;
import org.bitoo.abit.ui.custom.BitMapAdapter;
import org.bitoo.abit.utils.MissionSQLiteHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;

public class DetailedMissionActivityFragment extends Fragment {
    private static final String TAG = "ImageFramentDemo";
    private MissionSQLiteHelper sqlHelper;
    private GridView mGridView;
    private ButtonFloatSmall checkButton;
    private Mission mission;
    private BitMapAdapter bitmapAdapter;
    Toolbar toolbar;


    private static final String COLOR_KEY = "img_pixel";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailedMissionActivityFragment.
     */
    public static DetailedMissionActivityFragment getInstance() {
        DetailedMissionActivityFragment fragment = new DetailedMissionActivityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DetailedMissionActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        /**
         * Context here is identical to that in {@link MissionSQLiteHelper}
         * Global context required.
         */
        sqlHelper = new MissionSQLiteHelper(getActivity().getApplicationContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_display, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar = (Toolbar) getActivity().findViewById(R.id.tb_main);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkButton = (ButtonFloatSmall) getActivity().findViewById(R.id.bt_check);

        try {
            long id = getActivity().getIntent().getLongExtra(MainActivity.MISSION_ID, 0);
            mission = sqlHelper.loadMission(id);
            if(mission == null)
                throw new FileNotFoundException();

            toolbar.setSubtitle(mission.getMotto());

            bitmapAdapter = new BitMapAdapter(getActivity(), mission);
            mGridView = (GridView)getActivity().findViewById(R.id.gv_prog_image);
            mGridView.setNumColumns(mission.getProgressImage().getWidth());
            mGridView.setAdapter(bitmapAdapter);
        } catch (FileNotFoundException e) {
            Toast.makeText(getActivity(), "Load Image source error.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Load Image source error.");
            e.printStackTrace();
        }

        if(mission.check()) {
            checkButton.setClickable(true);
            checkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TweetInputFragment input = TweetInputFragment.newInstance(1, 3.6f, true);
                    input.show(getActivity().getFragmentManager(), "hello");
                }
            });
        }
    }

    /**
     * To communicate with
     * @param tweet
     */
    public void onAddTweet(Editable tweet) {
        if (mission.check()) {
            int position = mission.updateProgress(new Date(System.currentTimeMillis()));
            if(position >= 0) {
                sqlHelper.updateProgress(mission);
                try {
                    mission.addTweet(new Tweet(position, tweet.toString()));
                    bitmapAdapter.notifyDataSetChanged();
                    checkButton.setClickable(false);
                } catch (IOException e) {
                    Toast.makeText(getActivity(), "Error when add Tweet", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteMission() {
        sqlHelper.deleteMission(mission.getId());
    }

    public long getMissionId() {
        return mission.getId();
    }

}
