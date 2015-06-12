package org.bitoo.abit.mission.image;

import android.content.Context;
import android.util.Log;

import org.bitoo.abit.utils.TweetXmlParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;

/**
 * A Mission is a progress memoir that marks your progress
 * of a particular task.The progress displays as {@link ProgressImage}.
 */
public class Mission{

    private final static String TAG = "Mission";
    public final static long MILLIS_OF_ONE_DAY = 86400000;
    protected final static int MAX_MARK_CONTAIN = 50;
    protected ProgressImage progressImage;
    protected TweetXmlParser tweetXmlParser;

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
     * To obtain info of progress, call {@link #updateProgress(Date)}
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
    protected String motto;
    protected Context context;

    protected boolean isDone;
    protected String themeImagePath;

    /**
     * Initialize a mission, called when create a new mission and store it in database.
     * The image's pixel is of {@link BitColor}.
     * @param context to access local storage
     * @param XmlBitmapPath find this resource XML file
     * @throws FileNotFoundException when image is not found.
     */
    public Mission(Context context,
                   String title,
                   long createDate,
                   String XmlBitmapPath,
                   String motto,
                   String themeImagePath)
            throws FileNotFoundException {
        this(context, 0, title, createDate, createDate - MILLIS_OF_ONE_DAY, motto, false, themeImagePath);
        this.progressImage = new BitMapImage(XmlBitmapPath);
        this.progressMask = new byte[MAX_MARK_CONTAIN];
    }

    /**
     * Initialize a mission only for display its primary information.
     * Used in {@link @MainAcitivity} to generate a mission list to show.
     * @param context prepared to future usage.
     * @param isDone
     */
    public Mission(Context context,
                   long id,
                   String title,
                   long createDate,
                   long lastCheckDate,
                   String motto,
                   boolean isDone,
                   String themeImagePath ) {
        this.context = context;
        this.id = id;
        this.title = title;
        this.createDate = createDate;
        this.lastCheckDate = lastCheckDate;
        this.motto = motto;
        this.themeImagePath = themeImagePath;
        this.isDone = isDone;
    }

    /**
     * Initialize a mission for all detail information.
     * Called in order to show detailed mission.
     *
     * @param context to access local storage.
     * @param XmlBitmapPath to load {@link #progressImage}
     * @param progressMask to show progress
     * @param tweetFilePath to show tweet of each day
     * @throws FileNotFoundException
     */
    public Mission(Context context,
                   long id,
                   String title,
                   long createDate,
                   long lastCheckDate,
                   String XmlBitmapPath,
                   byte[] progressMask,
                   String tweetFilePath,
                   String motto,
                   boolean isDone,
                   String themeImagePath) throws FileNotFoundException {
        this(context, id, title, createDate, lastCheckDate, motto, isDone, themeImagePath);
        progressImage = new BitMapImage(XmlBitmapPath);
        progressImage.loadImage(context, XmlBitmapPath);
        this.progressMask = progressMask;
        this.tweetFilePath = tweetFilePath;
        this.motto = motto;
        tweetXmlParser = new TweetXmlParser(context, tweetFilePath);
    }

    public void setProgressImage(ProgressImage image){
        progressImage = image;
    }

    public ProgressImage getProgressImage(){
        return progressImage;
    }

    /**
     * Add 1 to current progress, and not add tweet.
     * So be careful to keep consistant of progress and tweet.
     *
     * ATTENTION : this method only update progress info in this object,
     * and not for SQLite.
     */
    public int updateProgress(Date curDate) {
        int position = (int)(curDate.getTime() / MILLIS_OF_ONE_DAY - createDate / MILLIS_OF_ONE_DAY);
        if(position < 0)
            return position;
        progressMask[position / 8] |= 1 << (position % 8);
        lastCheckDate = curDate.getTime();
        return position;
    }

    /**
     * To increase progress by one day.
     * @return If I can increase progress this day.
     */
    public boolean check() {
        Date curDate = new Date(System.currentTimeMillis());
        Date lastCheck = new Date(lastCheckDate);
        if (curDate.before(lastCheck)){
            Log.e(TAG, "current position is before last check day");
            return false;
        } else {
            return !curDate.toString().equals(lastCheck.toString());
        }
        //TODO ++ longestStreak;
    }

    public void addTweet(Tweet tweet) throws IOException {
        tweetXmlParser.addTweet(tweet);
    }

    public Tweet loadTweet(int position) throws FileNotFoundException {
        return tweetXmlParser.query(position);
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

    public String getMotto() {
        return motto;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getThemeImagePath() {
        return themeImagePath;
    }


    public void setId(long id) {
        this.id = id;
    }


}
