package org.bitoo.abit.ui.custom;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

/**
 * Created by langley on 5/31/15.
 */
public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
    private final static String TAG = "HidingScrollListener";
    private static final int HIDE_THRESHOLD = 50;
    private final static int BARHEIGHT = 300;
    private int scrolledDistance = 0;
    private boolean controlVisible = false;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        Log.d(TAG, "Scrolled " + "y:" + dy);

//
//        if (scrolledDistance > HIDE_THRESHOLD && controlVisible) {
//            onHide(dy);
//            controlVisible = false;
//            scrolledDistance = 0;
//        } else if (scrolledDistance < -HIDE_THRESHOLD) {
//            onShow();
//            controlVisible = true;
//            scrolledDistance = 0;
//        }
//
//        if((controlVisible && dy > 0) ||  dy < 0) {
//            scrolledDistance += dy;
//        }

        if(dy > 0) {
            onHide(dy);
        } else {
            onShow();
        }
    }

    public abstract void onHide(int dy);
    public abstract void onShow();
}
