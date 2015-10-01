package org.bitoo.abit.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.image.Mission;


public class DetailedMissionActivity extends AppCompatActivity implements TweetInputFragment.OnTweetInputListener, DetailedMissionFragment.OnMissionCreatedListener{
    Mission mission = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_mission);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_mission, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTweetInput(Editable input) {
        ((DetailedMissionFragment) getSupportFragmentManager().findFragmentById(R.id.detailed_fragment)).onAddTweet(input);
    }

    public Bitmap getScreen() {
        return ((DetailedMissionFragment)
                getSupportFragmentManager()
                .findFragmentById(R.id.detailed_fragment))
                .getScreenshot();
    }

    @Override
    public void onMissionCreated(Mission mission) {
        this.mission = mission;
    }
}
