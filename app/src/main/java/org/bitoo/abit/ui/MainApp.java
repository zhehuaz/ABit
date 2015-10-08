package org.bitoo.abit.ui;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.MissionSQLiteHelper;
import org.bitoo.abit.mission.image.BitmapImage;
import org.bitoo.abit.utils.FileHandler;

import java.io.IOException;

public class MainApp extends Application {
    private static final String TAG = "MainApp";
    public static final String VERSION_KEY = "first_launch";

    boolean first = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "In MainApp onCreate()");
        FacebookSdk.sdkInitialize(getApplicationContext());
        Fresco.initialize(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int checkNum = preferences.getInt(VERSION_KEY, 0);
        Log.i(TAG, "checkNum is " + checkNum);
        if (checkNum == 0) {
            Log.i(TAG, "First time to launch");
            initApp();
            first = true;
            preferences.edit().putInt(VERSION_KEY, 1).apply();
        }
    }

    /**
     * Called when app first launched.
     */
    private void initApp(){
        // Create database and table
        MissionSQLiteHelper missionSQLiteHelper =
                new MissionSQLiteHelper(this.getApplicationContext());
        SQLiteDatabase db = missionSQLiteHelper.getWritableDatabase();// create a new database this way
        Log.d(TAG, "database is created in : " + db.getPath());
        db.close();

        //save raw images into internal storage
        try {
            //
            FileHandler.createDirectory(getFilesDir().getAbsolutePath() + BitmapImage.STORAGE_PATH);
            FileHandler.copyFile(this, getResources().openRawResource(R.raw.mario), BitmapImage.STORAGE_PATH + "mario.xml");
            FileHandler.copyFile(this, getResources().openRawResource(R.raw.pacmonster), BitmapImage.STORAGE_PATH + "pacmonster.xml");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Can't load image source");
        }
    }

    public boolean isFirst() {
        return first;
    }

}
