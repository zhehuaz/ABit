package org.bitoo.abit.mission.image;

import android.content.Context;
import android.util.Log;

import org.bitoo.abit.storage.MissionStorage;

import java.util.Date;

/**
 * A Mission is a progress memoir that marks your progress
 * of a particular task.The progress displays as {@link ProgressImage}.
 */
public class Mission implements MissionStorage{

    private final static String TAG = "Mission";
    protected final static long MILLIS_OF_ONE_DAY = 86400000;
    protected final static int MAX_MARK_CONTAIN = 50;

    protected ProgressImage progressImage;

    /**
     * Marks if the mission is done of each day.
     *
     * The progress is stored in this array as a BITMAP, which means
     * one Bit marks one day.Thus, a 50-byte-sized array contains progresss
     * information of 400 days.
     * To obtain info of progress, call {@link #updateProgress()}
     */
    protected byte[] progressMask;

    /** Progress of the mission.How many day passed by since the mission is created.*/
    protected int progressDayNum;
    protected int longestStreak;
    /** To check if the progress can be increased now.*/
    protected Date lastCheckDate;
    protected String title;

    public Mission() {
        progressDayNum = 0;
        longestStreak = 0;
        // the date of last day.
        lastCheckDate = new Date(System.currentTimeMillis() - MILLIS_OF_ONE_DAY);
        title = "";

    }

    public Mission(String title) {
        this();
        this.title = title;
    }

    /**
     * Load image when mission is created.
     * The image's pixel is of {@link BitColor}.
     * @param context to access /res
     * @param id to find this resource XML file
     * @param progress Current progress.
     */
    public Mission(Context context, int id, byte[] progress) {
        this();
        progressImage = new BitMapImage(id);
        progressImage.loadImage(context);
        this.progressMask = progress;
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
        // TODO : store mission info in SQLite.

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
    public void resetLongestStreak() {
        longestStreak = 0;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public int getProgress() {
        return progressDayNum;
    }
}
