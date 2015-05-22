package org.bitoo.abit.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.image.Mission;
import org.bitoo.abit.ui.custom.BitMapAdapter;


/**
 * Activities that contain this fragment must implement the
 * {@link ImageFragmentDemo.OnItemSelectedListener} interface
 * to handle interaction events.
 * Use the {@link ImageFragmentDemo#getInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageFragmentDemo extends Fragment {
    GridView mGridView;
    Mission mission;

    private OnItemSelectedListener mListener;
    private static final String COLOR_KEY = "img_pixel";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ImageFragmentDemo.
     */
    public static ImageFragmentDemo getInstance() {
        ImageFragmentDemo fragment = new ImageFragmentDemo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ImageFragmentDemo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        mission = new Mission(getActivity(), 1, 210);
        BitMapAdapter adapter = new BitMapAdapter(getActivity(), mission);
        mGridView = (GridView)getActivity().findViewById(R.id.gv_prog_image);

        mGridView.setNumColumns(mission.getProgressImage().getWidth());
        mGridView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnItemSelectedListener {
        // TODO: Update argument type and name
        public void onItemSelected(int position);
    }

}
