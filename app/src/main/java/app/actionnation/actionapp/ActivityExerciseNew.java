package app.actionnation.actionapp;

import android.content.Intent;
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

public class ActivityExerciseNew extends BaseClassUser implements View.OnClickListener {
    private static FragmentManager fragmentManager;

    Button btnFinish;
    DbHelper db = new DbHelper(ActivityExerciseNew.this);
    int countData = 0;
    FirebaseAuth mAuth;
    String usrId = "";
    int dayOfTheYear, yr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_new);
        generatePublicMenu();
        btnFinish = findViewById(R.id.btn_exe_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usrId = fetchUserId();
                dayOfTheYear = fetchDate(0);
                yr = fetchDate(1);
                CommonClass cls = new CommonClass();
                cls.SubmitGenericGame(Constants.GS_exerciseScore, db, usrId, dayOfTheYear, yr);
            }
        });
        fragmentManager = getSupportFragmentManager();
        Fragment argumentFragment = new timingset();//Get Fragment Instance
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString(getString(R.string.Activity_Number), String.valueOf(Constants.GS_exerciseScore));

        argumentFragment.setArguments(data);//Finally set argument bundle to fragment
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerExercise, argumentFragment).commit();

    }

    protected String fetchUserId() {
        mAuth = FirebaseAuth.getInstance();
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
        Toast.makeText(ActivityExerciseNew.this, "There is no back action", Toast.LENGTH_LONG).show();
        return;
    }

    public void RedirectExcercise(View view) {
        Intent homepage = new Intent(ActivityExerciseNew.this, ActivityExercise.class);
        Bundle mBundle = new Bundle();
        mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
        mBundle.putString(getString(R.string.Page_Redirect), getString(R.string.Page_Redirect_Habit));
        homepage.putExtras(mBundle);
        startActivity(homepage);
    }

    @Override
    public void onClick(View v) {

    }
}