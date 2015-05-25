package org.bitoo.abit.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.bitoo.abit.mission.image.Mission;

import java.io.FileNotFoundException;
import java.util.Date;

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
    public MissionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /**
         * # To create the progressData table
         * CREATE TABLE {@link #TABLE_NAME}(
         * id INTEGER PRIMARY KEY AUTOINCRECEMENT,
         * title TEXT NOT NULL,
         * image_name VARCHAR(20) NOT NULL,
         * progress_mask BLOB NOT NULL,
         * first_day DATE NOT NULL,
         * last_day DATE NOT NULL
         * );
         */
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(\n" +
                " id INTEGER PRIMARY KEY AUTOINCRECEMENT,\n" +
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
     */
    public void addMission(Mission mission) {
        String sqlStatment = "INSERT INTO " + TABLE_NAME +
                " (id, title, image_name, progress_mask, first_day, last_day)" +
                        " VALUES(?, ?, ?, ?, ?, ?)";
        Log.v(TAG, "Executing :\n" + sqlStatment);
        Object[] args = new Object[]{
                mission.getId(),
                mission.getTitle(),
                mission.getProgressImage().getName(),
                mission.getProgressMask(),
                mission.getCreateDate().getTime(),
                mission.getLastCheckDate().getTime()
        };

        Log.d(TAG, "Mission : " +
                mission.getId() + "\n" +
                mission.getTitle() + "\n" +
                        mission.getProgressImage().getName() + "\n" +
                mission.getProgressMask() + "\n" +
                mission.getCreateDate().getTime() + "\n" +
                mission.getLastCheckDate().getTime());
        getWritableDatabase().execSQL(sqlStatment, args);
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
                new Date(cursor.getLong(cursor.getColumnIndex("first_day"))),
                new Date(cursor.getLong(cursor.getColumnIndex("last_day"))),
                cursor.getString(cursor.getColumnIndex("image_name")),
                cursor.getBlob(cursor.getColumnIndex("progress_mask"))
        );
        cursor.close();
        return mission;
    }
}
