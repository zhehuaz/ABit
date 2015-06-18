package org.bitoo.abit.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.bitoo.abit.R;


public class DetailedMissionActivity extends AppCompatActivity implements TweetInputFragment.OnTweetInputListener{
    Bitmap screenshot;

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

        switch (id) {
            case R.id.action_share :
                Toast.makeText(this, "SHARE ", Toast.LENGTH_SHORT).show();
                View view = this.getWindow().getDecorView();
                view.setDrawingCacheEnabled(true);
                view.buildDrawingCache();
                screenshot = view.getDrawingCache();

                ShareFragment shareFragment = ShareFragment.newInstance();
                shareFragment.show(this.getFragmentManager(), "share");
                view.destroyDrawingCache();
                break;
            case R.id.action_delete :
                DetailedMissionActivityFragment fragment =
                        (DetailedMissionActivityFragment) getSupportFragmentManager().findFragmentById(R.id.detailed_fragment);
                fragment.deleteMission();
                Intent intent = new Intent();
                intent.putExtra(MainActivity.ACTION_IS_DELETE, true);
                intent.putExtra(MainActivity.ACTION_ID_DELETED, fragment.getMissionId());
                setResult(MainActivity.REQUEST_IS_DELETE, intent);
                finish();
                break;
            default:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTweetInput(Editable input) {
        ((DetailedMissionActivityFragment) getSupportFragmentManager().findFragmentById(R.id.detailed_fragment)).onAddTweet(input);
    }

    public Bitmap getScreen() {
        return screenshot;
    }
}
