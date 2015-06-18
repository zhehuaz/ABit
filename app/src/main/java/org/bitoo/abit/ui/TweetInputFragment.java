package org.bitoo.abit.ui;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.bitoo.abit.R;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TweetInputFragment extends BaseDialogFragment {
    private OnTweetInputListener mListener;

    public static TweetInputFragment newInstance() {
        return new TweetInputFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_tweet_input, null);
        final EditText editText = (EditText) view.findViewById(R.id.et_tweet);

        builder.setPositiveButton(getActivity().getResources().getString(R.string.positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onTweetInput(editText.getText());
            }
        })
                .setNegativeButton(getActivity().getResources().getString(R.string.negative), null)
                .setView(view);
        return builder.create();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTweetInputListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnTweetInputListener {
        void onTweetInput(Editable input);
    }

    @Override
    protected boolean isActionBarBlurred() {
        return true;
    }
}
