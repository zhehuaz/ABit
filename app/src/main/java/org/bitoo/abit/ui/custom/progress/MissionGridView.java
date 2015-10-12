package org.bitoo.abit.ui.custom.progress;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import org.bitoo.abit.mission.Mission;

/**
 * <p>A grid view to show mission progress available with header and footer.
 * </p>
 *
 * ATTENTION: Call {@link #setHeaderView(View)} and {@link #setFooterView(View)}
 * before {@link #setMission(Mission)}, then call {@link #build()} to build the
 * view.
 */
public class MissionGridView extends RecyclerView{

    private BitmapAdapter2 adapter;
    private Mission mission;

    public MissionGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MissionGridView(Context context) {
        super(context);
    }

    public MissionGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Set necessary data to MissionGridView to set adapter.
     * @param mission A mission that contains progress grid information to draw of.
     *                If mission's title is null, the progress grid will be fulfilled.
     */
    public MissionGridView setMission(Mission mission) {
        this.mission = mission;
        adapter = new BitmapAdapter2(getContext(), mission);
        return this;
    }

    /**
     * Set a header view to progress grid.
     * @param header Header to be set.
     */
    public MissionGridView setHeaderView(View header) {
        if(adapter != null)
            adapter.setHeader(header);
        return this;
    }

    /**
     * Set a footer to progress grid.
     * @param footer Footer to be set.
     */
    public MissionGridView setFooterView(View footer) {
        if(adapter != null)
            adapter.setFooter(footer);
        return this;
    }

    /**
     * Build the view.This function should be called at last.
     */
    public MissionGridView build()
    {
        if(adapter != null)
            this.setAdapter(adapter);
        else
            throw new RuntimeException();
        final GridLayoutManager manager = new GridLayoutManager(getContext(), mission.getProgressImage().getWidth());

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? manager.getSpanCount() : 1;
            }
        });

        this.setLayoutManager(manager);
        return this;
    }
}
