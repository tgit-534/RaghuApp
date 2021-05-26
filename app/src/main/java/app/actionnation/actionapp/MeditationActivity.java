package app.actionnation.actionapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.Storage.UserStorageGameObject;
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

public class MeditationActivity extends BaseClassUser implements View.OnClickListener {
    private static FragmentManager fragmentManager;

    Button btnFinish;
    DbHelper db = new DbHelper(MeditationActivity.this);
    int countData = 0;
    FirebaseAuth mAuth;
    String usrId = "";
    int dayOfTheYear, yr;
    ArrayList<Integer> arrayGameScore = new ArrayList<>();
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CommonClass cls = new CommonClass();

        btnFinish = findViewById(R.id.btn_med_finish);
        coordinatorLayout = findViewById(R.id.cl_Meditation);

        usrId = fetchUserId(FirebaseAuth.getInstance());

        if (arrayGameScore.size() == 0 || arrayGameScore == null) {
            arrayGameScore = cls.getUserGameLocal(MeditationActivity.this, usrId);
        }
        if (arrayGameScore != null && arrayGameScore.size() > 0) {
            if (arrayGameScore.get(Constants.Game_CP__UserMeditationScore) > 0) {
                btnFinish.setTextColor(Color.BLACK);
            }
        }


        fragmentManager = getSupportFragmentManager();
        Fragment argumentFragment = new timingset();//Get Fragment Instance
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString(getString(R.string.Activity_Number), String.valueOf(Constants.GS_meditationScore));

        argumentFragment.setArguments(data);//Finally set argument bundle to fragment
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerMeditation, argumentFragment).commit();

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> userArray = fetchUserArray();
                int totalGameScore = 0;
                usrId = userArray.get(0);
                String userName = userArray.get(1);
                dayOfTheYear = fetchDate(0);
                yr = fetchDate(1);
                CommonClass cls = new CommonClass();
                cls.SubmitGenericGame(Constants.GS_meditationScore, db, usrId, dayOfTheYear, yr);

                DbHelperClass dbHelperClass = new DbHelperClass();

                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

                String usrId = fetchUserId(FirebaseAuth.getInstance());

                ArrayList<String> arrayCaptains = getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));


                UserStorageGameObject userStorageGameObject = new UserStorageGameObject();
                userStorageGameObject.setGameDocumentId(getIntent().getStringExtra(Constants.Intent_GameDocumentId));
                userStorageGameObject.setUserCoinsPerDay(getIntent().getIntExtra(Constants.Intent_GameCoinsPerDay, Constants.Status_Zero));
                userStorageGameObject.setUserExellenceBar(getIntent().getIntExtra(Constants.Intent_ExcellenceBar, Constants.Status_Zero));
                UserGame userGame = cls.loadUserGame(usrId, dayOfTheYear, yr, arrayCaptains, userName, userStorageGameObject);
                userGame.setUserMeditationScore(Constants.Game_Meditation);

                arrayGameScore = getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore)));

                ArrayList<Integer> arrayNewGameScore = cls.createGameScore(Constants.Game_CP__UserMeditationScore, Constants.Game_Meditation, arrayGameScore, userGame, MeditationActivity.this);

                if (arrayNewGameScore.size() == 20) {
                    userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Game_CP__UserTotatScore));
                    arrayGameScore = arrayNewGameScore;
                    totalGameScore = arrayGameScore.get(Constants.Game_CP__UserTotatScore);
                } else {
                    userGame.setUserTotatScore(Constants.Game_Meditation);
                    totalGameScore = arrayNewGameScore.get(Constants.Status_Zero);

                }

                dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), MeditationActivity.this, userGame, rootRef, getString(R.string.fs_Usergame_userMeditationScore), Constants.Game_Meditation, totalGameScore);
                makeSnackBar(coordinatorLayout);
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
