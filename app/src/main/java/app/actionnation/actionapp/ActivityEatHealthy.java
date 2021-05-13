package app.actionnation.actionapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import app.actionnation.actionapp.Database_Content.Personal_Distraction;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.adapters.EatExerciseAdapter;

public class ActivityEatHealthy extends BaseClassUser implements View.OnClickListener {

    String TAG = "True Learning";

    ExtendedFloatingActionButton fabEatHealthy, fabAvoidFood;

    ViewPager viewPager;
    TabLayout tabLayout;
    EatExerciseAdapter ds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat_healthy);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabEatHealthy = findViewById(R.id.fab_act_eatFood_finish);
        fabAvoidFood = findViewById(R.id.fab_act_avoidFood_finish);
        viewPager = findViewById(R.id.view_pager_eatHealthy);


        tabLayout = findViewById(R.id.tab_eatHealthy);
        tabLayout.addTab(tabLayout.newTab().setText("Gratitude"));
        tabLayout.addTab(tabLayout.newTab().setText("Forgiveness"));
        tabLayout.addTab(tabLayout.newTab().setText("Forgiveness"));

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

        ds = new EatExerciseAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), Constants.TabEatHealthy);
        tabLayout.setupWithViewPager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                animateFab(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        viewPager.setAdapter(ds);



        fabEatHealthy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentEatHealthy fragment = (FragmentEatHealthy) ds.instantiateItem(viewPager, 0);
                fragment.submitWin();
            }
        });

        fabAvoidFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentEatHealthy fragment = (FragmentEatHealthy) ds.instantiateItem(viewPager, 1);
                fragment.submitWin();
            }
        });

    }


    private void animateFab(int position) {
        switch (position) {
            case 0:
                fabEatHealthy.show();
                fabAvoidFood.hide();
                break;
            case 1:
                fabEatHealthy.hide();
                fabAvoidFood.show();
                break;
            case 2:
                fabEatHealthy.hide();
                fabAvoidFood.hide();
                break;
            default:
                fabEatHealthy.show();
                fabAvoidFood.hide();
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }


    public static class ViewHolderEatHealthy extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mId;
        public final CheckBox chk;

        public Personal_Distraction mItem;

        public ViewHolderEatHealthy(View view) {
            super(view);
            mView = view;
            mId = view.findViewById(R.id.distraction_name);
            chk = view.findViewById(R.id.chkDistraction);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mId.getText() + "'";
        }
    }
}