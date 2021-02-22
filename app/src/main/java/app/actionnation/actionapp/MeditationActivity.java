package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelper;

public class MeditationActivity extends BaseClassUser implements View.OnClickListener {
    private static FragmentManager fragmentManager;

    Button btnFinish;
    DbHelper db = new DbHelper(MeditationActivity.this);
    int countData = 0;
    FirebaseAuth mAuth;
    String usrId = "";
    int dayOfTheYear, yr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);
        generatePublicMenu();




        btnFinish = findViewById(R.id.btn_med_finish);



        fragmentManager = getSupportFragmentManager();
        Fragment argumentFragment = new timingset();//Get Fragment Instance
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString(getString(R.string.Activity_Number), String.valueOf(Constants.GS_meditationScore));

        argumentFragment.setArguments(data);//Finally set argument bundle to fragment
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerMeditation, argumentFragment).commit();


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usrId = fetchUserId(FirebaseAuth.getInstance());
                dayOfTheYear = fetchDate(0);
                yr = fetchDate(1);
                CommonClass cls = new CommonClass();
                cls.SubmitGenericGame(Constants.GS_meditationScore, db, usrId, dayOfTheYear, yr);
            }
        });

    }


    protected String fetchUserId(FirebaseAuth mAuth) {
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        }
        return usrId;
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(MeditationActivity.this, "There is no back action", Toast.LENGTH_LONG).show();
        return;
    }

    @Override
    public void onClick(View v) {

    }
}
