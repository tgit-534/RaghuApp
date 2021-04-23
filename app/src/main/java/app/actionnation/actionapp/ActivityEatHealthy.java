package app.actionnation.actionapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import app.actionnation.actionapp.Database_Content.Personal_Distraction;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.adapters.EatExerciseAdapter;

public class ActivityEatHealthy extends BaseClassUser implements View.OnClickListener {

    String TAG = "True Learning";


    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat_healthy);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewPager = findViewById(R.id.view_pager_eatHealthy);
        tabLayout = findViewById(R.id.tab_eatHealthy);
        tabLayout.addTab(tabLayout.newTab().setText("Gratitude"));
        tabLayout.addTab(tabLayout.newTab().setText("Forgiveness"));
        tabLayout.addTab(tabLayout.newTab().setText("Forgiveness"));

        Log.d(TAG, "2");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        EatExerciseAdapter ds;
        ds = new EatExerciseAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), Constants.TabEatHealthy);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(ds);

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