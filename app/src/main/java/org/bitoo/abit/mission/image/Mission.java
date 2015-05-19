package org.bitoo.abit.mission.image;

import android.util.Log;

import java.util.Date;

/**
 * A Mission is a progress memoir that marks your progress
 * of a particular task.The progress displays as {@link ProgressImage}.
 */
public class Mission {

    private final static String TAG = "Mission";
    private final static long MILLIS_OF_ONE_DAY = 86400000;

    protected ProgressImage progressImage;

    /** Progress of the mission.*/
    protected int progress;
    protected int longestStreak;
    /** To check if the progress can be increased now.*/
    Date lastCheckDate;

    public Mission() {
        progress = 0;
        longestStreak = 0;
        // the date of last day.
        lastCheckDate = new Date(System.currentTimeMillis() - MILLIS_OF_ONE_DAY);
    }

    public void setProgressImage(ProgressImage image){
        progressImage = image;
    }

    public ProgressImage getProgressImage(){
        return progressImage;
    }

    /**
     * Add 1 to current progress.
     */
    public void updateProgress() {
        ++ progress;
    }

    /**
     * Set progress 0.
     */
    public void resetProgress() {
        progress = 0;
    }

    /**
     * To increase progress by one day.
     * @return If I can increase progress this day.
     */
    public boolean check() {
        Date curDate = new Date(System.currentTimeMillis());
        if (curDate.before(lastCheckDate)){
            Log.e(TAG, "current date is before last check day");
        }
        else if (curDate == lastCheckDate){
            return false;
        }
        updateProgress();
        ++ longestStreak;
        lastCheckDate = curDate;
        return true;
    }

    /**
     * To reset {@link #longestStreak}.
     */
    public void resetlongestStreak() {
        longestStreak = 0;
    }
}
