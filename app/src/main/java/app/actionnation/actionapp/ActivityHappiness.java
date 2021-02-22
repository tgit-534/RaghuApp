package app.actionnation.actionapp;

import android.os.Bundle;
import android.util.Log;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class ActivityHappiness extends BaseClassUser {

    ViewPager viewPager;
    TabLayout tabLayout;
    String TAG = "Activity Happiness";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happiness);
        generatePublicMenu();

        Log.d(TAG, "1");

        viewPager = findViewById(R.id.view_pager_happinessData);
        tabLayout = findViewById(R.id.tab_happinessData);
        tabLayout.addTab(tabLayout.newTab().setText("Gratitude"));
        tabLayout.addTab(tabLayout.newTab().setText("Forgiveness"));
        tabLayout.addTab(tabLayout.newTab().setText("Dream"));
        Log.d(TAG, "2");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        displayHappinessTabsAdapter ds;
        ds = new displayHappinessTabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(ds);

        Log.d(TAG, "3");


    }

}