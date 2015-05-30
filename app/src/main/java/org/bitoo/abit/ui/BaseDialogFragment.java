package org.bitoo.abit.ui;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * This fragment
 */
public class BaseDialogFragment extends BlurDialogFragment {

    @Override
    protected float getDownScaleFactor() {
        return (float)2.6;
    }

    @Override
    protected int getBlurRadius() {
        return 5;
    }

    @Override
    protected boolean isActionBarBlurred() {
        return super.isActionBarBlurred();
    }

    @Override
    protected boolean isDimmingEnable() {
        return true;
    }

    @Override
    protected boolean isRenderScriptEnable() {
        return false;
    }

    @Override
    protected boolean isDebugEnable() {
        return false;
    }
}
