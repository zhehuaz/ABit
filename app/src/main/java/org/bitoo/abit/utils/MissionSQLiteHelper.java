package org.bitoo.abit.utils;

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
 * Mission saves settings in SQLite database, and this class
 * is a helper to help operate mission's data.
 * Most functions are called by Mission, I mean, Mission handles
 * data subjectively.
 */
public class MissionSQLiteHelper extends SQLiteOpenHelper implements MissionStorage{
    private static final String TAG = "MissoinSQLiteHelper";
    public static final String DATABASE_NAME = "ABit";
    public static final String TABLE_NAME = "progressData";
    public static final int VERSION = 1;
    private Context context;

    /**
     * Constructor.
     * @param context ATTENTION, context here must be {@link android.app.Application}
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
         * tweet_path TEXT NOT NULL TODO : insert this path
         * );
         */
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(\n" +
                " id INTEGER PRIMARY KEY    ,\n" +
                " title TEXT NOT NULL UNIQUE,\n" +
                " image_name VARCHAR(20) NOT NULL,\n" +
                " progress_mask BLOB NOT NULL,\n" +
                " first_day LONG NOT NULL,\n" +
                " last_day LONG NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Sample:
     * INSERT INTO progressData (id, title, image_name, progress_mask, first_day, last_day)
     * VALUES( 1, 'hello', 'mario.xml', '', xxxxxxxx, xxxxxxxx);
     * If mission is new, id should be null.
     */
    public long addMission(Mission mission) {
        String sqlStatment = "INSERT INTO " + TABLE_NAME +
                " (id, title, image_name, progress_mask, first_day, last_day)" +
                        " VALUES(NULL, ?, ?, ?, ?, ?)";
        Log.v(TAG, "Executing :\n" + sqlStatment);
        Object[] args = new Object[]{
                mission.getTitle(),
                mission.getProgressImage().getName(),
                mission.getProgressMask(),
                mission.getCreateDate(),
                mission.getLastCheckDate()
        };

        Log.d(TAG, "Mission : " +
                mission.getId() + "\n" +
                mission.getTitle() + "\n" +
                mission.getProgressImage().getName() + "\n" +
                mission.getProgressMask() + "\n" +
                mission.getCreateDate() + "\n" +
                mission.getLastCheckDate());
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlStatment, args);
        long id;
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        cursor.moveToFirst();
        id = cursor.getLong(0);
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
    public Mission loadMission(Context context, long id) throws FileNotFoundException {
        Mission mission = null;
        String sqlStatment = "SELECT * FROM " + TABLE_NAME + " WHERE id=" + id;
        Cursor cursor = getReadableDatabase().rawQuery(sqlStatment, null);
        ;

        if(cursor.moveToFirst()) {
            mission = new Mission(
                    context,
                    id,
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getLong(cursor.getColumnIndex("first_day")),
                    cursor.getLong(cursor.getColumnIndex("last_day")),
                    cursor.getString(cursor.getColumnIndex("image_name")),
                    cursor.getBlob(cursor.getColumnIndex("progress_mask"))
            );
        }
        cursor.close();
        return mission;
    }

    /**
     * List fundamental information of missions, to show them in a list.
     * Information of missions is not complete.Image and progress is not to be returned.
     * @return list of missions
     */
    public List<Mission> loadMissions() {
        List<Mission> missionList = new ArrayList<>();
        String sqlStatment =  "SELECT id,title,first_day,last_day FROM " + TABLE_NAME;
        Cursor cursor = getReadableDatabase().rawQuery(sqlStatment, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            missionList.add(new Mission(context,
                    cursor.getLong(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getLong(cursor.getColumnIndex("first_day")),
                    cursor.getLong(cursor.getColumnIndex("last_day"))));
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
    public void deleteMission(Context context, long id) {
        String sqlStatement = "DELETE FROM " + TABLE_NAME + " WHERE id=" + id;
        getWritableDatabase().execSQL(sqlStatement);
    }

}
