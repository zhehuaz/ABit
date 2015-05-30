package org.bitoo.abit.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.bitoo.abit.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddMissionActivityFragment extends Fragment {

    public AddMissionActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_mission, container, false);
    }
}
