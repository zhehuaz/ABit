package org.bitoo.abit.utils;

import android.content.Context;
import android.content.res.TypedArray;

import org.bitoo.abit.R;

/**
 * Created by langley on 6/20/15.
 */
public class LayoutHelper {

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    public static int getTabsHeight(Context context) {
        return (int) context.getResources().getDimension(R.dimen.tabs_height);
    }
}
