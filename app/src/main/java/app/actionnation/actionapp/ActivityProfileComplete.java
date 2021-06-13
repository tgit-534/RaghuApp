package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class ActivityProfileComplete extends BaseClassUser implements View.OnClickListener, FragmentUserProfile.OnFragmentUpdateProfileListener {

    private static FragmentManager fragmentManager;
    FrameLayout flShowUserData, flInsertUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_complete);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        flShowUserData = findViewById(R.id.fragmentProfileData);
        flInsertUserData = findViewById(R.id.fragmentProfileInsert);


        ArrayList<String> arrayProfileObject = getIntent().getStringArrayListExtra(getString(R.string.Intent_profileObject));
        fragmentManager = getSupportFragmentManager();

        if (arrayProfileObject != null) {
            Fragment fragment = new FragmentUserProfile(arrayProfileObject);//Get Fragment Instance
            fragmentManager.beginTransaction().replace(R.id.fragmentProfileData, fragment).commit();
        } else {
            Fragment fragment = new Fragment_ProfileData();//Get Fragment Instance
            fragmentManager.beginTransaction().replace(R.id.fragmentProfileInsert, fragment).commit();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }


    @Override
    public void onProfileDataUpdate(ArrayList<String> profileDataObject) {

        flShowUserData.setVisibility(View.GONE);
        fragmentManager = getSupportFragmentManager();

        Fragment fragment = new Fragment_ProfileData(profileDataObject);//Get Fragment Instance
        fragmentManager.beginTransaction().replace(R.id.fragmentProfileInsert, fragment).commit();

    }
}






