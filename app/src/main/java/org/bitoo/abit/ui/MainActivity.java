package org.bitoo.abit.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.facebook.appevents.AppEventsLogger;

import org.bitoo.abit.R;
import org.bitoo.abit.ui.custom.ViewPagerAdapter;
import org.bitoo.abit.utils.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String MISSION_ID = "MissoinId";
    public static final String ACTION_IS_DELETE = "IsDelete";
    public static final String ACTION_ID_DELETED = "IdDeleted";

    public static final int REQUEST_IS_DELETE = 0x1;

    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private List<Fragment> fragments;
    private Toolbar toolbar;
    public MainActivityFragment mainFragment;
    private ImageView tab;
    private RelativeLayout toolbarContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);
        toolbarContainer = (RelativeLayout) findViewById(R.id.rl_toolbar_container);

        // ViewPager and tabs
        fragments = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.vp_main);
        mainFragment = new MainActivityFragment();
        fragments.add(mainFragment);
        fragments.add(GalleryFragment.newInstance());
        initTabs();
        pagerAdapter= new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(pagerAdapter);

    }

    protected void initTabs()
    {
        LinearLayout tabLayout = (LinearLayout) findViewById(R.id.ll_tabs);

        tab = (ImageView) tabLayout.findViewById(R.id.iv_tab);
        //tab.setBackgroundColor(Color.parseColor("#FF00FF"));
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
        showToolbarAnimation();
        return true;
    }

    public void moveToolbar(int d) {
        toolbarContainer.setTranslationY(- d);
    }

    public void showToolbar() {
        toolbarContainer.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    public void hideToolbar() {
        toolbarContainer.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showToolbarAnimation() {

        toolbarContainer.setTranslationY(- toolbar.getHeight());
        toolbarContainer.animate()
                .translationY(0)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator(3.f))
                .setStartDelay(50);
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
    protected void onResume() {
        super.onResume();
        showToolbar();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }
}
