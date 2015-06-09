package org.bitoo.abit.ui.custom;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

/**
 * To Scroll toolbar in {@link org.bitoo.abit.ui.MainActivity}.
 *
 */
public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
    private final static String TAG = "HidingScrollListener";
    private static final int HIDE_THRESHOLD = 50;
    private int scrolledDistance = 0;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        /* TODO : show toolbar when back to list and less than two were left.*/
        if(dy < 0) {
            scrolledDistance += dy;
        }

        if(dy > 0) {
            onHide(dy);
        } else if(scrolledDistance < -HIDE_THRESHOLD) {
            onShow();
            scrolledDistance = 0;
        }

    }

    public abstract void onHide(int dy);
    public abstract void onShow();
}
