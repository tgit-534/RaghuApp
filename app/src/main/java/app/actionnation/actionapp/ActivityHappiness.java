package app.actionnation.actionapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import app.actionnation.actionapp.Storage.Constants;

public class ActivityHappiness extends BaseClassUser implements FragmentDataInsertion.ListenFromActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    String TAG = "Activity Happiness";
    ExtendedFloatingActionButton fabGratitude, fabAbundance;
    displayHappinessTabsAdapter ds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happiness);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Log.d(TAG, "1");

        fabGratitude = findViewById(R.id.fab_act_gratitude_finish);
        fabAbundance = findViewById(R.id.fab_act_abundance_finish);


        viewPager = findViewById(R.id.view_pager_happinessData);
        tabLayout = findViewById(R.id.tab_happinessData);
        tabLayout.addTab(tabLayout.newTab().setText("Gratitude"));
        tabLayout.addTab(tabLayout.newTab().setText("Forgiveness"));
        tabLayout.addTab(tabLayout.newTab().setText("Dream"));
        Log.d(TAG, "2");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                animateFab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        ds = new displayHappinessTabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                animateFab(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        //  viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(ds);
        String strInsertionData = getIntent().getStringExtra(Constants.Intent_DataInsertion);
        if (strInsertionData != null) {
            if (strInsertionData.equals(getString(R.string.Page_Redirect_Abundance))) {
                viewPager.setCurrentItem(2);
            }
        }
        Log.d(TAG, "3");


        fabGratitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gratitude fragment = (gratitude) ds.instantiateItem(viewPager, 0);
                fragment.submitWin();
            }
        });


        fabAbundance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentAbundance fm = (FragmentAbundance) ds.instantiateItem(viewPager, 1);

                fm.submitWin();
            }
        });

    }


    private void animateFab(int position) {
        switch (position) {
            case 0:
                fabAbundance.hide();
                fabGratitude.show();
                break;
            case 1:
                fabAbundance.show();
                fabGratitude.hide();
                break;
            case 2:
                fabAbundance.hide();
                fabGratitude.hide();
                break;
            default:
                fabAbundance.show();
                fabGratitude.hide();
                break;
        }
    }


    @Override
    public void doSomethingInFragment(int windowNumber) {
        if (windowNumber == Integer.parseInt(getString(R.string.viewHolder_Insert_Abundance))) {
            FragmentAbundance fm = (FragmentAbundance) ds.instantiateItem(viewPager, 1);

            fm.fetch();
        } else
        if (windowNumber == Integer.parseInt(getString(R.string.viewHolder_Insert_Gratitude))) {
            gratitude fragment = (gratitude) ds.instantiateItem(viewPager, 0);

            fragment.fetch();
        }

    }
}