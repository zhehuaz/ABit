package org.bitoo.abit.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.bitoo.abit.mission.image.Mission;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Mission saves settings in SQLite database, and this class
 * is a helper to help operate mission's data.
 * Most functions are called by Mission, I mean, Mission handles
 * data subjectively.
 */
public class MissionSQLiteHelper extends SQLiteOpenHelper{
    private static final String TAG = "MissoinSQLiteHelper";
    public static final String DATABASE_NAME = "ABit";
    public static final String TABLE_NAME = "progressData";
    public static final int VERSION = 1;
    private Context context;
    /**
     *
     * @param context ATTENTION, context here must be {@link android.app.Application}
     * @param name of database, use {@link #DATABASE_NAME}
     * @param factory
     * @param version of database, use {@link #VERSION}
     */
    private MissionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public MissionSQLiteHelper(Context context) {
        this(context, DATABASE_NAME, null, VERSION);
    }

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
         * last_day DATE NOT NULL
         * );
         */
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(\n" +
                " id INTEGER PRIMARY KEY    ,\n" +
                " title TEXT NOT NULL,\n" +
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
    public void addMission(Mission mission) {
        String sqlStatment = "INSERT INTO " + TABLE_NAME +
                " (id, title, image_name, progress_mask, first_day, last_day)" +
                        " VALUES(?, ?, ?, ?, ?, ?)";
        Log.v(TAG, "Executing :\n" + sqlStatment);
        long id = mission.getId();
        Object[] args = new Object[]{
                id == 0 ? null : id,
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
    }

    /**
     * Sample:
     * SELECT * FROM {@link #TABLE_NAME}
     * WHERE id=1;
     * @return mission got from database.
     */
    public Mission loadMission(Context context, int id) throws FileNotFoundException {
        Mission mission;
        String sqlStatment = "SELECT * FROM " + TABLE_NAME + " WHERE id=" + id;
        Cursor cursor = getReadableDatabase().rawQuery(sqlStatment, null);
        cursor.moveToFirst();

        mission = new Mission(
                context,
                id,
                cursor.getString(cursor.getColumnIndex("title")),
                cursor.getLong(cursor.getColumnIndex("first_day")),
                cursor.getLong(cursor.getColumnIndex("last_day")),
                cursor.getString(cursor.getColumnIndex("image_name")),
                cursor.getBlob(cursor.getColumnIndex("progress_mask"))
        );
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
    public void deleteMission(Context context, int id) {
        String sqlStatement = "DELETE FROM " + TABLE_NAME + "WHERE id = " + id;
        getWritableDatabase().execSQL(sqlStatement);
    }

}
