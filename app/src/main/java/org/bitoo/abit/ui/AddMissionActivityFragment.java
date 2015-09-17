package org.bitoo.abit.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.bitoo.abit.R;
import org.bitoo.abit.ui.custom.BitmapViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddMissionActivityFragment extends Fragment {
    private ViewPager viewPager;
    private List<String> xmlPaths;
    public AddMissionActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_mission, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.vp_prev);
        xmlPaths = new ArrayList<>();

        // TODO add these xml paths automatically
        xmlPaths.add("mario.xml");
        xmlPaths.add("pacmonster.xml");
        viewPager.setAdapter(new BitmapViewPagerAdapter(getActivity(), xmlPaths));
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
