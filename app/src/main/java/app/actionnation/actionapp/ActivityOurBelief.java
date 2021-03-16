package app.actionnation.actionapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

public class ActivityOurBelief extends BaseClassUser implements View.OnClickListener {

    RecyclerView recyclerView;
    ArrayList<String> strAttPattern = new ArrayList<>();
    FirebaseAuth mAuth;
    String usrId;
    DbHelper db = new DbHelper(ActivityOurBelief.this);

    String TAG = "Belief System";
    Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_belief);
        generatePublicMenu();

        usrId = fetchUserId();


        btnFinish = findViewById(R.id.btn_belief_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> userArray = fetchUserArray();

                usrId = userArray.get(0);
                int dayOfTheYear = fetchDate(0);
                int yr = fetchDate(1);
                CommonClass cls = new CommonClass();
                cls.SubmitGenericGame(Constants.GS_ourBeliefScore, db, usrId, dayOfTheYear, yr);

                DbHelperClass dbHelperClass = new DbHelperClass();
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();


                ArrayList<String> arrayCaptains = getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));
                UserGame userGame = cls.loadUserGame(usrId, dayOfTheYear, yr, arrayCaptains, userArray.get(1));
                userGame.setUserOurBeliefScore(Constants.Game_OurBeliefSystem);


                int totalGameScore = 0;
                ArrayList<Integer> arrayGameScore = getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore)));

                ArrayList<Integer> arrayNewGameScore = cls.createGameScore(Constants.Game_CP__UserOurBeliefScore, Constants.Game_OurBeliefSystem, arrayGameScore, userGame, ActivityOurBelief.this);

                if (arrayNewGameScore.size() == 20) {
                    userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Game_CP__UserTotatScore));
                    arrayGameScore = arrayNewGameScore;
                    totalGameScore = arrayGameScore.get(Constants.Game_CP__UserTotatScore);
                } else {
                    userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Status_Zero));
                    totalGameScore = arrayNewGameScore.get(Constants.Status_Zero);
                }

                dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), ActivityOurBelief.this, userGame, rootRef, getString(R.string.fs_Usergame_userOurBeliefScore), Constants.Game_OurBeliefSystem, totalGameScore);


            }
        });
        Cursor res = db.getBeliefList(usrId);
        if (res.getCount() > 0) {
            while (res.moveToNext()) {

                strAttPattern.add(res.getString(1));

                if (res.getString(3).equals("1")) {
                    strAttPattern.add(res.getString(1));
                }
            }
        }
        res.moveToFirst();
        recyclerView = findViewById(R.id.listBelief);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityOurBelief.this));
        BeliefAdapter mAdapter = new BeliefAdapter(ActivityOurBelief.this, res);
        recyclerView.setAdapter(mAdapter);
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
    public void onClick(View v) {

    }

    public void RedirectBelief(View view) {
        Intent homepage = new Intent(ActivityOurBelief.this, HappinessContent.class);
        Bundle mBundle = new Bundle();
        mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
        mBundle.putString(getString(R.string.Page_Redirect), getString(R.string.Page_Redirect_Belief));
        homepage.putExtras(mBundle);
        startActivity(homepage);
    }
}