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

public class ActivityEatHealthy extends BaseClassUser implements View.OnClickListener {

    String TAG = "True Learning";


    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat_healthy);
        generatePublicMenu();
        viewPager = findViewById(R.id.view_pager_eatHealthy);
        tabLayout = findViewById(R.id.tab_eatHealthy);
        tabLayout.addTab(tabLayout.newTab().setText("Gratitude"));
        tabLayout.addTab(tabLayout.newTab().setText("Forgiveness"));
        Log.d(TAG, "2");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TabsAdapterCommon ds;
        ds = new TabsAdapterCommon(getSupportFragmentManager(), tabLayout.getTabCount(), Constants.TabEatHealthy);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(ds);


/*
        mAuth = FirebaseAuth.getInstance();
        fbUser = mAuth.getCurrentUser();
        usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        } else {
            return;
        }


        recyclerView = (RecyclerView) findViewById(R.id.listEatHealthy);


        Calendar c = Calendar.getInstance();

        int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
        int yr = c.get(Calendar.YEAR);
        DbHelper db = new DbHelper(ActivityEatHealthy.this);

        Log.d(TAG, "Enter Db");

        Cursor res = db.getEatHealthyList(usrId, dayOfYear, yr);
        if (res.getCount() == 0) {
            Log.d(TAG, "Enter Db no count");
            // return;
        } else {
            while (res.moveToNext()) {
                Log.d(TAG, "Enter Db no count inside while" + res.getString(1));

                if (res.getString(3).equals("1")) {
                    strEHPattern.add(res.getString(2));
                    Log.d(TAG, "Enter Db");
                }
            }
        }


        CommonClass cl = new CommonClass();

        mGoogleSignInClient = cl.GoogleStart(ActivityEatHealthy.this);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityEatHealthy.this));
        recyclerView.setHasFixedSize(false);
        fetch(strEHPattern);*/
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