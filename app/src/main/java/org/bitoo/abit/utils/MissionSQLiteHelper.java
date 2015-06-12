package org.bitoo.abit.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.bitoo.abit.mission.image.Mission;
import org.bitoo.abit.storage.MissionStorage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Missions are stored in SQLite database, and this class
 * is a helper to help operate missions' data.
 * Most functions in this class are almost called by Mission, I mean,
 * Mission handles data subjectively.So if you want to control data of
 * mission, use functions in {@link Mission} itself.
 */
public class MissionSQLiteHelper extends SQLiteOpenHelper implements MissionStorage{
    private static final String TAG = "MissionSQLiteHelper";
    public static final String DATABASE_NAME = "ABit";
    public static final String TABLE_NAME = "progressData";
    public static final int VERSION = 1;
    private Context context;

    /**
     * Constructor.
     * @param context ATTENTION, context here must be as a {@link android.app.Application}
     * @param name of database, use {@link #DATABASE_NAME}
     * @param factory is usually null
     * @param version of database, use {@link #VERSION}
     */
    private MissionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public MissionSQLiteHelper(Context context) {
        this(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * Called when database is created.
     * After the database is firstly created, a table of progressData is created.
     * This table stores all the information about missions.
     * @param db is the database created.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        /**
         * # To create the progressData table
         * CREATE TABLE {@link #TABLE_NAME}(
         * id INTEGER PRIMARY KEY,
         * title TEXT NOT NULL,
         * image_name VARCHAR(20) NOT NULL,
         * progress_mask BLOB NOT NULL,
         * first_day DATE NOT NULL,
         * last_day DATE NOT NULL,
         * tweet_path VARCHAR(100) NOT NULL,
         * motto VARCHAR(70) NOT NULL,
         * is_done BOOLEAN NOT NULL CHECK (is_done IN (0,1)),
         * theme_image_path VARCHAR(100) # if null, use default image
         * );
         */
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(\n" +
                " id INTEGER PRIMARY KEY,\n" +
                " title TEXT NOT NULL,\n" +
                " image_name VARCHAR(20) NOT NULL,\n" +
                " progress_mask BLOB NOT NULL,\n" +
                " first_day LONG NOT NULL,\n" +
                " last_day LONG NOT NULL,\n" +
                " tweet_path TEXT NOT NULL,\n" +
                " motto VARCHAR(70) NOT NULL,\n" +
                " is_done INT NOT NULL CHECK (is_done IN (0,1)),\n" +
                " theme_image_path VARCHAR(100)" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Sample:
     * INSERT INTO progressData (id, title, image_name, progress_mask, first_day, last_day, tweet_path)
     * VALUES( 1, 'hello', 'mario.xml', '', xxxxxxxx, xxxxxxxx, 'hello_xxxx');
     *
     * Tweet XML file path is generated when insert, so client don't need
     * to care for it, and the file is created in non-sense.
     */
    public long addMission(Mission mission) {
        String sqlStatement = "INSERT INTO " + TABLE_NAME +
                " (id, title, image_name, progress_mask, first_day, last_day, tweet_path, motto, is_done, theme_image_path)" +
                        " VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String fileName = mission.getTitle() + "_" + mission.getCreateDate();
        Log.v(TAG, "Executing :\n" + sqlStatement);
        Object[] args = new Object[]{
                mission.getTitle(),
                mission.getProgressImage().getName(),
                mission.getProgressMask(),
                mission.getCreateDate(),
                mission.getLastCheckDate(),
                fileName,
                mission.getMotto(),
                mission.isDone(),
                mission.getThemeImagePath()
        };

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlStatement, args);
        long id;
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        cursor.moveToFirst();
        id = cursor.getLong(0);

        TweetXmlParser tweetXmlParser = new TweetXmlParser(context, fileName);
        tweetXmlParser.generateXmlFile();

        db.close();
        cursor.close();
        return id;
    }

    /**
     * Sample:
     * SELECT * FROM {@link #TABLE_NAME}
     * WHERE id=1;
     * @return mission got from database.
     */
    public Mission loadMission(long id) throws FileNotFoundException {
        Mission mission = null;
        String sqlStatement = "SELECT * FROM " + TABLE_NAME + " WHERE id=" + id;
        Cursor cursor = getReadableDatabase().rawQuery(sqlStatement, null);

        if(cursor.moveToFirst()) {
            mission = new Mission(
                    context,
                    id,
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getLong(cursor.getColumnIndex("first_day")),
                    cursor.getLong(cursor.getColumnIndex("last_day")),
                    cursor.getString(cursor.getColumnIndex("image_name")),
                    cursor.getBlob(cursor.getColumnIndex("progress_mask")),
                    cursor.getString(cursor.getColumnIndex("tweet_path")),
                    cursor.getString(cursor.getColumnIndex("motto")),
                    cursor.getInt(cursor.getColumnIndex("is_done")) > 0,
                    cursor.getString(cursor.getColumnIndex("theme_image_path"))
            );
        }
        cursor.close();
        return mission;
    }

    /**
     * List fundamental information of missions, to show them in a list.
     * Information of missions is not complete.Image and progress is not to be returned.
     * @return list of missions
     * @param isDone
     */
    public List<Mission> loadMissions(boolean isDone) {
        List<Mission> missionList = new ArrayList<>();
        String whereClause = " where is_done " + (isDone ? ">" : "<=") + " 0";
        String sqlStatement =  "SELECT id,title,first_day,last_day,motto,theme_image_path FROM " + TABLE_NAME + whereClause;
        Cursor cursor = getReadableDatabase().rawQuery(sqlStatement, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            missionList.add(new Mission(context,
                    cursor.getLong(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getLong(cursor.getColumnIndex("first_day")),
                    cursor.getLong(cursor.getColumnIndex("last_day")),
                    cursor.getString(cursor.getColumnIndex("motto")),
                    isDone,
                    cursor.getString(cursor.getColumnIndex("theme_image_path"))
            ));
            cursor.moveToNext();
        }
        cursor.close();
        return missionList;
    }

    /**
     * Sample:
     *  DELETE FROM {@link #TABLE_NAME}
     *      WHERE id = 1;
     */
    public void deleteMission(long id) {
        String deleteSqlStatement = "DELETE FROM " + TABLE_NAME + " WHERE id=" + id;
        String querySqlStatement = "SELECT tweet_path FROM " + TABLE_NAME + " WHERE id=" + id;
        Cursor cursor = getReadableDatabase().rawQuery(querySqlStatement, null);
        cursor.moveToFirst();
        context.deleteFile(cursor.getString(cursor.getColumnIndex("tweet_path")));
        Log.d(TAG, "tweet file is deleted");
        getWritableDatabase().execSQL(deleteSqlStatement);
        cursor.close();
    }

    /**
     * Update progressMask of Mission in SQLite.
     * @param mission in which new progressMask stores in.
     */
    public void updateProgress(Mission mission) {
        ContentValues cv = new ContentValues();
        cv.put("progress_mask", mission.getProgressMask());
        cv.put("last_day", mission.getLastCheckDate());
        String[] whereClause = {mission.getId() + ""};
        getWritableDatabase().update(TABLE_NAME, cv, "id" + " = ?", whereClause);
    }
}
