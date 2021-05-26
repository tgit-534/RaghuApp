package app.actionnation.actionapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import app.actionnation.actionapp.utils.TabsAdapterGameTracking;

public class ActivityGameTracking extends BaseClassUser implements View.OnClickListener, FragmentStakeGame.OnFragmentSuccessBarListener {
    ViewPager viewPager;
    TabLayout tabLayout;
    private final String TAG = getClass().getSimpleName();
    ExtendedFloatingActionButton fabGamecreation;
    TabsAdapterGameTracking ds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_tracking);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeControls();
        loadTabs();


    }


    private void initializeControls() {
        viewPager = findViewById(R.id.view_pager_gameTracking);
        tabLayout = findViewById(R.id.tab_gameTracking);

    }

    private void loadTabs() {

        tabLayout.addTab(tabLayout.newTab().setText("Game Stake!"));
        tabLayout.addTab(tabLayout.newTab().setText("Game Tracking!"));

        Log.d(TAG, "2");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ds = new TabsAdapterGameTracking(getSupportFragmentManager(), tabLayout.getTabCount());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(ds);
    }

    @Override
    public void onClick(View v) {

    }



    @Override
    public void onSuccessBarSend(int coins) {

    }
}