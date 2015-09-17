package org.bitoo.abit.ui;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.appevents.AppEventsLogger;

import org.bitoo.abit.R;
import org.bitoo.abit.ui.custom.MainViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String MISSION_ID = "MissoinId";
    public static final String ACTION_IS_DELETE = "IsDelete";
    public static final String ACTION_ID_DELETED = "IdDeleted";

    public static final int REQUEST_IS_DELETE = 0x1;

    private ViewPager viewPager;
    private MainViewPagerAdapter pagerAdapter;
    private List<Fragment> fragments;
    private ImageView[] tabs;
    private Toolbar toolbar;
    public MainActivityFragment mainFragment;
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
        fragments.add(ReservedFragment.newInstance());
        fragments.add(ProfileFragment.newInstance());
        initTabs();
        pagerAdapter= new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);

    }

    protected void initTabs()
    {
        RelativeLayout tabLayout = (RelativeLayout) findViewById(R.id.rl_tabs);

        tabs = new ImageView[4];
        tabs[0] = (ImageView) tabLayout.findViewById(R.id.iv_tab0);
        tabs[1] = (ImageView) tabLayout.findViewById(R.id.iv_tab1);
        tabs[2] = (ImageView) tabLayout.findViewById(R.id.iv_tab2);
        tabs[3] = (ImageView) tabLayout.findViewById(R.id.iv_tab3);

        for(int i = 0; i < tabs.length; i ++) {
            tabs[i].setOnClickListener(new OnTabClickListener(i));
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        Point size = new Point();
        ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        final int screenWidth = size.x;

        final int wideWidth = tabs[0].getWidth();
        final int thinWidth = tabs[1].getWidth();
        final int delta = wideWidth - thinWidth;
        final ViewGroup.LayoutParams params[] = new ViewGroup.LayoutParams[4];
        for(int i = 0;i < tabs.length;i ++)
        {
            params[i] = tabs[i].getLayoutParams();
        }

        //tab.setBackgroundColor(Color.parseColor("#FF00FF"));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "position :" + position + "  positionOffset :" + positionOffset + "  positionOffsetPixels :" + positionOffsetPixels);

                params[position].width = wideWidth - (int) (delta * positionOffset);
                tabs[position].setLayoutParams(params[position]);
                if(position < 3) {
                    params[position + 1].width = thinWidth + (int) (delta * positionOffset);
                    tabs[position + 1].setLayoutParams(params[position + 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        toolbarContainer.animate().translationY(0).setInterpolator(new DecelerateInterpolator(5f)).start();
    }

    public void hideToolbar() {
        toolbarContainer.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(5f)).start();
    }

    private void showToolbarAnimation() {

        toolbarContainer.setTranslationY(- toolbar.getHeight());
        toolbarContainer.animate()
                .translationY(0)
                .setDuration(700)
                .setInterpolator(new DecelerateInterpolator(6.f))
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

    class OnTabClickListener implements View.OnClickListener{
        int index;
        OnTabClickListener(int index)
        {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }
    }
}
