package org.bitoo.abit.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.Mission;
import org.bitoo.abit.mission.Tweet;
import org.bitoo.abit.ui.custom.progress.BitmapAdapter2;
import org.bitoo.abit.ui.custom.progress.MissionGridView;
import org.bitoo.abit.mission.MissionSQLiteHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DetailedMissionFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "DetailedMissionFragment";
    private MissionSQLiteHelper sqlHelper;

    private MissionGridView missionGridView;
    private BitmapAdapter2 missionGridAdapter;
    private FloatingActionButton checkButton;
    private Mission mission;
    private OnMissionCreatedListener mListener = null;

    private static final String COLOR_KEY = "img_pixel";

    private View header;
    private RecyclerView footer;
    private SimpleDraweeView headerBg;
    private TextView titleText;
    private TextView mottoText;
    private ImageButton backBtn;
    private ImageButton shareBtn;
    private ImageButton delBtn;

    Bitmap screenshot;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailedMissionFragment.
     */
    public static DetailedMissionFragment newInstance() {
        DetailedMissionFragment fragment = new DetailedMissionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DetailedMissionFragment() {
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

        View fragmentView = inflater.inflate(R.layout.fragment_detailed_mission, container, false);
//        mGridView = (GridViewWithHeaderAndFooter)fragmentView.findViewById(R.id.gv_prog_image);
//        missionGridView = (RecyclerView) inflater.inflate(R.layout.rv_progress, container, false);
        missionGridView = (MissionGridView) fragmentView.findViewById(R.id.mgv_prog_image);
        header = inflater.inflate(R.layout.header_detailed_mission, container, false);


        headerBg = (SimpleDraweeView) header.findViewById(R.id.dv_header_bg);
        titleText = (TextView) header.findViewById(R.id.tv_mission_title);
        mottoText = (TextView) header.findViewById(R.id.tv_mission_motto);
        backBtn = (ImageButton) header.findViewById(R.id.ibt_back);
        shareBtn = (ImageButton) header.findViewById(R.id.ibt_share);
        delBtn = (ImageButton) header.findViewById(R.id.ibt_del);
        //footer = (RecyclerView) inflater.inflate(R.layout.footer_detailed_mission, mGridView, false)
               // .findViewById(R.id.rv_history_list);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolbarAndGridView();
        initCheckButton();
        //initHistoryList();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DetailedMissionActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnMissionCreatedListener");
        }
    }

    private void initToolbarAndGridView() {
        //toolbar = (Toolbar) getActivity().findViewById(R.id.tb_main);
        //((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            long id = getActivity().getIntent().getLongExtra(MainActivity.MISSION_ID, 0);
            mission = sqlHelper.loadMission(id);
            if(mission == null)
                throw new FileNotFoundException();
            else
                mListener.onMissionCreated(mission);

            //toolbar.setTitle(mission.getTitle());
            //toolbar.setSubtitle(mission.getMotto());
            titleText.setText(mission.getTitle());
            mottoText.setText(mission.getMotto());

            headerBg.setImageURI(Uri.fromFile(new File(mission.getThemeImagePath())));

            backBtn.setOnClickListener(this);
            shareBtn.setOnClickListener(this);
            delBtn.setOnClickListener(this);


            missionGridView.setMission(mission);
            missionGridView.setHeaderView(header);
            missionGridView.build();
            missionGridAdapter = (BitmapAdapter2)missionGridView.getAdapter();

        } catch (FileNotFoundException e) {
            Toast.makeText(getActivity(), "Load Image source error.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Load Image source error.");
            e.printStackTrace();
        }
    }

    private void initCheckButton() {
        checkButton = (FloatingActionButton) getActivity().findViewById(R.id.bt_check);
        checkButton.setClickable(true);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mission.check()) {
                    TweetInputFragment input = TweetInputFragment.newInstance();
                    input.show(getActivity().getFragmentManager(), "hello");
                } else {
                    Toast.makeText(getActivity(), "今天已经打过卡啦～", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initHistoryList()
    {
        List<String> tweets = new ArrayList<>();

    }

    /**
     * To communicate with {@link TweetInputFragment}
     * @param tweet Text from {@link TweetInputFragment}
     */
    public void onAddTweet(Editable tweet) {
        if (mission.check()) {
            int position = mission.updateProgress(new Date(System.currentTimeMillis()));
            if(position >= 0) {
                sqlHelper.updateProgress(mission);
                try {
                    mission.addTweet(new Tweet(position, tweet.toString()));
                    missionGridAdapter.notifyDataSetChanged();
                } catch (IOException e) {
                    Toast.makeText(getActivity(), "Error when adding Tweet", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
    }

    private void deleteMission() {
        sqlHelper.deleteMission(mission.getId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ibt_back:
                getActivity().finish();
                break;
            case R.id.ibt_del:
                Intent intent = new Intent();
                intent.putExtra(MainActivity.ACTION_ID_DELETED, mission.getId());
                getActivity().setResult(DetailedMissionActivity.RESULT_OK, intent);
                deleteMission();
                getActivity().finish();
                break;
            case R.id.ibt_share:
                Toast.makeText(getActivity(), "SHARE ", Toast.LENGTH_SHORT).show();
                checkButton.setVisibility(View.INVISIBLE);
                View view = getActivity().getWindow().getDecorView();
                view.setDrawingCacheEnabled(true);
                view.buildDrawingCache();

               screenshot = Bitmap.createBitmap(view.getDrawingCache(),
                        0,
                        235,// FIXME calculate it!
                        getResources().getDisplayMetrics().widthPixels,
                        getResources().getDisplayMetrics().widthPixels + 400);
                checkButton.setVisibility(View.VISIBLE);
                if(mission != null) {
                    ShareFragment shareFragment = ShareFragment.newInstance(mission);
                    shareFragment.show(getActivity().getFragmentManager(), "share");
                }
                view.destroyDrawingCache();
                break;
            default:
                break;
        }
    }

    public interface OnMissionCreatedListener {
        void onMissionCreated(Mission mission);
    }

    public Bitmap getScreenshot() {
        return screenshot;
    }
}
