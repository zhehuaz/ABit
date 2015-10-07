package org.bitoo.abit.ui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.bitoo.abit.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnExtractCompleteListener} interface
 * to handle interaction events.
 * Use the {@link ABitractorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ABitractorFragment extends Fragment {

    private OnExtractCompleteListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ABitractorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ABitractorFragment newInstance() {
        ABitractorFragment fragment = new ABitractorFragment();
        return fragment;
    }

    public ABitractorFragment() {
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
        return inflater.inflate(R.layout.fragment_abitractor, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if(context instanceof OnExtractCompleteListener)
                mListener = (OnExtractCompleteListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnExtractCompleteListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnExtractCompleteListener {
        public void onExtractComplete(Uri uri);
    }

}
