package org.bitoo.abit.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconCompat;

import org.bitoo.abit.R;
/**
 *  MainActivity holds a fragment, {@link MainActivityFragment}
 *  to show a list of missions created.Besides, when a mission
 *  item in list is selected, a {@link DetailedMissionActivityFragment} is created
 *  with detailed information of the item.
 *  This activity doesn't have a visible view, is used as a container instead.
 */
public class MainActivity extends ActionBarActivity implements DetailedMissionActivityFragment.OnItemSelectedListener {
    public static final String MISSION_ID = "MissoinId";

    private MaterialMenuIconCompat materialMenu;
    private byte menuIconState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        materialMenu = new MaterialMenuIconCompat(this, Color.GRAY, MaterialMenuDrawable.Stroke.THIN);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        materialMenu.syncState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        materialMenu.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        } else if(id == android.R.id.home) {
            if(menuIconState == 0) {
                materialMenu.animateState(MaterialMenuDrawable.IconState.ARROW);
                menuIconState = 1;
            } else {
                materialMenu.animateState(MaterialMenuDrawable.IconState.BURGER);
                menuIconState = 0;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(int position) {

    }
}
