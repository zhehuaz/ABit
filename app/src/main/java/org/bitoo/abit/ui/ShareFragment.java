package org.bitoo.abit.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.FacebookRequestError;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.image.Mission;

import java.net.URL;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

public class ShareFragment extends BaseDialogFragment {
    private ImageView screenshotView;
    private Mission mission;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ShareFragment.
     */
    public static ShareFragment newInstance(Mission mission) {

        ShareFragment fragment = new ShareFragment();
        fragment.setMission(mission);
        return fragment;
    }

    public ShareFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_share, null);
        screenshotView = (ImageView) view.findViewById(R.id.iv_screenshot);
        if(screenshotView != null) {
            final Bitmap screenshot = ((DetailedMissionActivity)getActivity()).getScreen();
            if(!screenshot.isRecycled()) {
                screenshotView.setImageBitmap(screenshot);
                builder.setPositiveButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), screenshot, null, null));
//                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                        shareIntent.setType("image/*");
//                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//                        startActivity(Intent.createChooser(shareIntent, "Choose"));
                        SharePhoto sharedShot = new SharePhoto.Builder()
                                .setBitmap(screenshot)
                                .setUserGenerated(true)
                                .build();
//                        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
//                                .putString("og:type", "games.achievement")
//                                .putString("og:title", mission.getTitle())
//                                .putString("og:description", mission.getMotto())
//                                .putPhoto("og:image", sharedShot)
//                                .putInt("game.points", 12)
//                                .build();
//                        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
//                                .setActionType("games.achieves")
//                                .putObject("achievement", object)
//                                .build();
//                        ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
//                                .setPreviewPropertyName("achievement")
//                                .setAction(action)
//                                .build();
                        SharePhotoContent content = new SharePhotoContent.Builder()
                                .addPhoto(sharedShot)
                                .build();
                        ShareDialog.show(getActivity(), content);
                    }
                });
            }
        }
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void setMission(Mission mission) {
        this.mission = mission;
    }
}
