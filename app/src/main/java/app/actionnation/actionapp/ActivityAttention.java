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

public class ActivityAttention extends BaseClassUser implements View.OnClickListener {
    private long START_TIME_IN_MILLIS = 1000;

    String TAG = "attention";
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewPager = findViewById(R.id.view_pager_attention);
        tabLayout = findViewById(R.id.tab_attention);
        tabLayout.addTab(tabLayout.newTab().setText("Gratitude"));
        tabLayout.addTab(tabLayout.newTab().setText("Forgiveness"));
        Log.d(TAG, "2");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TabsAdapterCommon ds;
        ds = new TabsAdapterCommon(getSupportFragmentManager(), tabLayout.getTabCount(), Constants.TabAttention);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(ds);

        Log.d(TAG, "3");


    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_att_Submit) {

        }
    }


    public static class ViewHolderDistraction extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdDistraction;
        public final CheckBox chkDistraction;

        public Personal_Distraction mItem;

        public ViewHolderDistraction(View view) {
            super(view);
            mView = view;
            mIdDistraction = view.findViewById(R.id.distraction_name);
            chkDistraction = view.findViewById(R.id.chkDistraction);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdDistraction.getText() + "'";
        }
    }

}
