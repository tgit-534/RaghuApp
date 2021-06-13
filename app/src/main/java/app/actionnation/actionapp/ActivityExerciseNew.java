package app.actionnation.actionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.Storage.UserStorageGameObject;
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

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
                ArrayList<String> strUser = fetchUserArray();
                dayOfTheYear = fetchDate(0);
                yr = fetchDate(1);
                CommonClass cls = new CommonClass();
                cls.SubmitGenericGame(Constants.GS_exerciseScore, db, usrId, dayOfTheYear, yr);

                DbHelperClass dbHelperClass = new DbHelperClass();
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

                UserStorageGameObject userStorageGameObject = new UserStorageGameObject();
                userStorageGameObject.setGameDocumentId(getIntent().getStringExtra(Constants.Intent_GameDocumentId));
                userStorageGameObject.setUserCoinsPerDay(getIntent().getIntExtra(Constants.Intent_GameCoinsPerDay, Constants.Status_Zero));
                userStorageGameObject.setUserExellenceBar(getIntent().getIntExtra(Constants.Intent_ExcellenceBar, Constants.Status_Zero));

                UserGame userGame = cls.loadUserGame(strUser.get(0), dayOfTheYear, yr, strUser.get(1), userStorageGameObject);

                userGame.setUserExerciseScore(Constants.Game_Exercise);


                int totalGameScore = 0;
                ArrayList<Integer> arrayGameScore = getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore)));

                ArrayList<Integer> arrayNewGameScore = cls.createGameScore(Constants.Game_CP__UserExerciseScore, Constants.Game_Exercise, arrayGameScore, userGame, ActivityExerciseNew.this);

                if (arrayNewGameScore.size() == 20) {
                    userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Game_CP__UserTotatScore));
                    arrayGameScore = arrayNewGameScore;
                    totalGameScore = arrayGameScore.get(Constants.Game_CP__UserTotatScore);
                } else {
                    userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Status_Zero));
                    totalGameScore = arrayNewGameScore.get(Constants.Status_Zero);

                }


                dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), ActivityExerciseNew.this, userGame, rootRef, getString(R.string.fs_Usergame_userExerciseScore), Constants.Game_Exercise, totalGameScore);

            }
        });
        fragmentManager = getSupportFragmentManager();
        Fragment argumentFragment = new timingset();//Get Fragment Instance
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString(getString(R.string.Activity_Number), String.valueOf(Constants.GS_exerciseScore));

        argumentFragment.setArguments(data);//Finally set argument bundle to fragment
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerExercise, argumentFragment).commit();

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