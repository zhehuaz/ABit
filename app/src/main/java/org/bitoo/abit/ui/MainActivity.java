package org.bitoo.abit.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;


import org.bitoo.abit.R;
import org.bitoo.abit.ui.custom.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *  MainActivity holds a fragment, {@link MainActivityFragment}
 *  to show a list of missions created.Besides, when a mission
 *  item in list is selected, a {@link DetailedMissionActivityFragment} is created
 *  with detailed information of the item.
 *  This activity doesn't have a visible view, is used as a container instead.
 */
public class MainActivity extends AppCompatActivity implements DetailedMissionActivityFragment.OnItemSelectedListener {
    private static final String TAG = "MainActivity";
    public static final String MISSION_ID = "MissoinId";

    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private List<Fragment> fragments;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);

        fragments = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.vp_main);
        fragments.add(new MainActivityFragment());
        fragments.add(new AddMissionActivityFragment());
        pagerAdapter= new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void moveUpToolBar(int dy) {
        dy /= 2;
        float d = toolbar.getTranslationY() - dy;
        if(d > -toolbar.getHeight())
            toolbar.setTranslationY(d);
    }

    public void moveDownToolBar() {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection Simpl ifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                // translucent status bar
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                // translucent navigation bar
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
        }
    }

    @Override
    public void onItemSelected(int position) {

    }
}
