package org.bitoo.abit.mission.image;

import android.content.Context;
import android.util.Log;

import org.bitoo.abit.storage.MissionStorage;

import java.io.FileNotFoundException;
import java.util.Date;

/**
 * A Mission is a progress memoir that marks your progress
 * of a particular task.The progress displays as {@link ProgressImage}.
 */
public class Mission{

    private final static String TAG = "Mission";
    public final static long MILLIS_OF_ONE_DAY = 86400000;
    protected final static int MAX_MARK_CONTAIN = 50;
    protected ProgressImage progressImage;

    /**
     * Misssion's local id doesn't have to be identical to that of remote.
     *
     * When a Mission is to be synchronized, server returns a new id
     * and local database update it.
     */
    protected long id;

    /**
     * Marks if the mission is done of each day.
     *
     * The progress is stored in this array as a BITMAP, which means
     * one Bit marks one day.Thus, a 50-byte-sized array contains progress
     * information of 400 days.
     * To obtain info of progress, call {@link #updateProgress()}
     */
    protected byte[] progressMask;

    /**
     * The path that the progress tweet is stored in as an XML file.
     * Only file path of tweets is stored but the whole tweets, in consider of
     * storage pressure of memory and efficiency.
     * Tweets would be loaded and parsed when the item is selected individually
     * from XML file using {@link #loadTweet(int)}
     */
    protected String tweetFilePath;

    /** Progress of the mission.How many day passed by since the mission is created.*/
    protected int progressDayNum;
    protected int longestStreak;

    /** To check if the progress can be increased now.*/
    protected long lastCheckDate;

    protected long createDate;
    protected String title;
    protected Context context;

    /**
     * Initialize a mission, called when create a new mission and store it in database.
     * The image's pixel is of {@link BitColor}.
     * @param context to access local storage
     * @param imageName find this resource XML file
     * @throws FileNotFoundException when image is not found.
     */
    public Mission(Context context,
                   String title,
                   long createDate,
                   String imageName)
            throws FileNotFoundException {
        this(context, 0, title, createDate, createDate - MILLIS_OF_ONE_DAY);
        this.progressImage = new BitMapImage(imageName);
        this.progressMask = new byte[MAX_MARK_CONTAIN];
    }

    /**
     * Initialize a mission only for display its primary information.
     * Used in {@link @MainAcitivity} to generate a mission list to show.
     * @param context prepared to future usage.
     */
    public Mission(Context context, long id, String title, long createDate, long lastCheckDate) {
        this.context = context;
        this.id = id;
        this.title = title;
        this.createDate = createDate;
        this.lastCheckDate = lastCheckDate;
    }

    /**
     * Initialize a mission for all detail information.
     * Called in order to show detailed mission.
     *
     * @param context to access local storage.
     * @param imageName to load {@link #progressImage}
     * @param progressMask to show progress
     * @param tweetFilePath to show tweet of each day
     * @throws FileNotFoundException
     */
    public Mission(Context context,
                   long id,
                   String title,
                   long createDate,
                   long lastCheckDate,
                   String imageName,
                   byte[] progressMask,
                   String tweetFilePath) throws FileNotFoundException {
        this(context, id, title, createDate, lastCheckDate);
        progressImage = new BitMapImage(imageName);
        progressImage.loadImage(context, imageName);
        this.progressMask = progressMask;
        this.tweetFilePath = tweetFilePath;
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
        Date lastCheck = new Date(lastCheckDate);
        if (curDate.before(lastCheck)){
            Log.e(TAG, "current date is before last check day");
        }
        else if (curDate == lastCheck){
            return false;
        }
        updateProgress();
        ++ longestStreak;
        lastCheckDate = curDate.getTime();
        return true;
    }

    public void addTweet(Tweet tweet, int position) {
        // TODO
    }

    public void loadTweet(int position) {
        // TODO
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

    public int getProgressDayNum() {
        return progressDayNum;
    }

    public boolean getProgressMask(int i) {
        return (progressMask[i / 8] >> (i % 8) & 0x1) == 0x1;
    }

    public byte[] getProgressMask() {
        return progressMask;
    }

    public long getLastCheckDate() {
        return lastCheckDate;
    }

    public String getTitle() {
        return title;
    }

    public long getId() {
        return id;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public class Tweet {
        String text;
    }
}
