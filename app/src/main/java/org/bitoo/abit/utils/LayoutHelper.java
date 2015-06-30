package org.bitoo.abit.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;

import org.bitoo.abit.R;

/**
 * Get information of screen and layout.
 */
public class LayoutHelper {

    /**
     * Get toolbar's height in current Activity or Fragment.
     * @param context The activity of Fragment.
     * @return The height.
     */
    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    /**
     * The height of tabs' height in {@link org.bitoo.abit.ui.MainActivity}
     * @param context The context to get resource.
     * @return The height.
     */
    public static int getTabsHeight(Context context) {
        return (int) context.getResources().getDimension(R.dimen.tabs_height);
    }

    /**
     * The total height of screen.
     * @param activity An Acitivity.
     * @return the height.
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return metrics.heightPixels;

    }
}
