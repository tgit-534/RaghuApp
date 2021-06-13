package app.actionnation.actionapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.Storage.UserStorageGameObject;
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

public class ActivityIntegrityMain extends BaseClassUser implements View.OnClickListener {

    Button btnSelf, BtnPlace, BtnWord, BtnWork, BtnDreamWin;
    DbHelper dbHelper;
    String fbId, placeWin, wordAgreement, respectWork, selfWin, wordAgreementItems;
    int dayOfTheYear, yr;
    Cursor csrIntegrityGame;
    Cursor csrGame;
    ArrayList<Integer> arrayGameScore = new ArrayList<>();
    FirebaseFirestore rootRef;

    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrity_main);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        coordinatorLayout = findViewById(R.id.cl_integrity);

        btnSelf = findViewById(R.id.btn_int_Self);
        BtnPlace = findViewById(R.id.btn_int_place);
        BtnWord = findViewById(R.id.btn_int_word);
        BtnWork = findViewById(R.id.btn_int_completion);
        BtnDreamWin = findViewById(R.id.btn_int_targetWin);

        btnSelf.setOnClickListener(this);
        BtnPlace.setOnClickListener(this);
        BtnWord.setOnClickListener(this);
        BtnWork.setOnClickListener(this);
        BtnDreamWin.setOnClickListener(this);

        CommonClass cls = new CommonClass();
        dbHelper = new DbHelper(ActivityIntegrityMain.this);
        ArrayList<String> userArray = fetchUserArray();

        fbId = userArray.get(0);
        dayOfTheYear = fetchDate(Constants.Status_Zero);
        yr = fetchDate(Constants.Status_One);

        arrayGameScore = getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore)));
        if (arrayGameScore == null) {
            arrayGameScore = cls.getUserGameLocal(ActivityIntegrityMain.this, fbId);
        }

        if (arrayGameScore != null && arrayGameScore.size() > 0) {
            btnSelf.setTag(arrayGameScore);

            if (arrayGameScore.get(Constants.Game_CP__UserSelfWinScore) > 0) {
                btnSelf.setTextColor(Color.BLACK);
            }
            if (arrayGameScore.get(Constants.Game_CP__UserPlaceWinScore) > 0) {
                BtnPlace.setTextColor(Color.BLACK);
            }
            if (arrayGameScore.get(Constants.Game_CP__UserWordWinScore) > 0) {
                BtnWord.setTextColor(Color.BLACK);
            }
            if (arrayGameScore.get(Constants.Game_CP__UserWorkWinScore) > 0) {
                BtnWork.setTextColor(Color.BLACK);
            }
            if (arrayGameScore.get(Constants.Game_CP__UserHabitsScore) > 0) {
                BtnDreamWin.setTextColor(Color.BLACK);
            }
        }

        /*csrIntegrityGame = dbHelper.getIntegrityScore(fbId, dayOfTheYear, yr);

        if (csrIntegrityGame.getCount() > Constants.Status_Zero) {

            while (csrIntegrityGame.moveToNext()) {

                selfWin = csrIntegrityGame.getString(Constants.DbSql_Integrity_SelfWin);
                placeWin = csrIntegrityGame.getString(Constants.DbSql_Integrity_PlaceWin);
                wordAgreement = csrIntegrityGame.getString(Constants.DbSql_Integrity_WordAgreement);
                respectWork = csrIntegrityGame.getString(Constants.DbSql_Integrity_RespectWork);
                wordAgreementItems = csrIntegrityGame.getString(Constants.HP_DbSql_Integrity_RespectWorkItems);


                if (selfWin.equals(String.valueOf(Constants.Game_SelfWin))) {
                    btnSelf.setTextColor(Color.RED);
                }
                if (placeWin.equals(String.valueOf(Constants.Game_SelfWin))) {
                    BtnPlace.setTextColor(Color.RED);
                }
            }
        }*/
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        DbHelperClass dbHelperClass = new DbHelperClass();
        rootRef = FirebaseFirestore.getInstance();
        CommonClass cls = new CommonClass();
        ArrayList<String> userArray = fetchUserArray();
        String fbId = userArray.get(0);
        String userName = userArray.get(1);
        int totalGameScore = 0;
        if (btnSelf.getTag() == null) {
            arrayGameScore = getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore)));
            btnSelf.setTag(arrayGameScore);

        } else {
            arrayGameScore = (ArrayList<Integer>) btnSelf.getTag();

            if (arrayGameScore.size() > 0 && arrayGameScore.size() != 20)
                arrayGameScore = cls.getUserGameLocal(ActivityIntegrityMain.this, fbId);

        }

        if (i == R.id.btn_int_word) {
            Intent homepage = new Intent(ActivityIntegrityMain.this, ActivityIntegrity.class);
            Bundle mBundle = new Bundle();
            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));


            mBundle.putString(Constants.Intent_GameDocumentId, getIntent().getStringExtra(Constants.Intent_GameDocumentId));
            mBundle.putInt(Constants.Intent_GameCoinsPerDay, getIntent().getIntExtra(Constants.Intent_GameCoinsPerDay, Constants.Status_Zero));
            mBundle.putInt(Constants.Intent_ExcellenceBar, getIntent().getIntExtra(Constants.Intent_ExcellenceBar, Constants.Status_Zero));
            mBundle.putIntegerArrayList(getString(R.string.Intent_ArrayGameScore), (ArrayList<Integer>) btnSelf.getTag());

            homepage.putExtras(mBundle);
            startActivity(homepage);
        } else if (i == R.id.btn_int_targetWin) {
            Intent homepage = new Intent(ActivityIntegrityMain.this, HabitTraking.class);
            Bundle mBundle = new Bundle();
            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));

            mBundle.putString(Constants.Intent_GameDocumentId, getIntent().getStringExtra(Constants.Intent_GameDocumentId));
            mBundle.putInt(Constants.Intent_GameCoinsPerDay, getIntent().getIntExtra(Constants.Intent_GameCoinsPerDay, Constants.Status_Zero));
            mBundle.putInt(Constants.Intent_ExcellenceBar, getIntent().getIntExtra(Constants.Intent_ExcellenceBar, Constants.Status_Zero));
            mBundle.putIntegerArrayList(getString(R.string.Intent_ArrayGameScore), (ArrayList<Integer>) btnSelf.getTag());

            homepage.putExtras(mBundle);
            startActivity(homepage);
        } else if (i == R.id.btn_int_Self) {
            btnSelf.setTextColor(Color.BLACK);

            csrIntegrityGame = dbHelper.getIntegrityScore(fbId, dayOfTheYear, yr);
            csrIntegrityGame.moveToFirst();
            if (csrIntegrityGame.getCount() > Constants.Status_Zero) {
                selfWin = csrIntegrityGame.getString(Constants.DbSql_Integrity_SelfWin);
                placeWin = csrIntegrityGame.getString(Constants.DbSql_Integrity_PlaceWin);
                wordAgreement = csrIntegrityGame.getString(Constants.DbSql_Integrity_WordAgreement);
                respectWork = csrIntegrityGame.getString(Constants.DbSql_Integrity_RespectWork);
                wordAgreementItems = csrIntegrityGame.getString(Constants.HP_DbSql_Integrity_RespectWorkItems);

                dbHelper.updateIntegrityScore(Constants.Game_SelfWin, Integer.parseInt(selfWin), Integer.parseInt(wordAgreement), Integer.parseInt(wordAgreementItems), Float.parseFloat(respectWork), fbId, dayOfTheYear, yr, 1);

            } else {

                dbHelper.insertIntegrityScore(Constants.Game_SelfWin, 0,
                        0, 0, 0,
                        fbId, dayOfTheYear, yr, Constants.Status_One);

            }

            UserStorageGameObject userStorageGameObject = new UserStorageGameObject();
            userStorageGameObject.setGameDocumentId(getIntent().getStringExtra(Constants.Intent_GameDocumentId));
            userStorageGameObject.setUserCoinsPerDay(getIntent().getIntExtra(Constants.Intent_GameCoinsPerDay, Constants.Status_Zero));
            userStorageGameObject.setUserExellenceBar(getIntent().getIntExtra(Constants.Intent_ExcellenceBar, Constants.Status_Zero));

            UserGame userGame = cls.loadUserGame(fbId, dayOfTheYear, yr, userName, userStorageGameObject);
            userGame.setUserSelfWinScore(Constants.Game_SelfWin);

            ArrayList<Integer> arrayNewGameScore = cls.createGameScore(Constants.Game_CP__UserSelfWinScore, Constants.Game_SelfWin, arrayGameScore, userGame, ActivityIntegrityMain.this);
            arrayGameScore = arrayNewGameScore;
            if (arrayNewGameScore.size() == 20) {
                userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Game_CP__UserTotatScore));
                totalGameScore = arrayGameScore.get(Constants.Game_CP__UserTotatScore);
            } else {
                userGame.setUserTotatScore(Constants.Game_SelfWin);
                totalGameScore = arrayNewGameScore.get(Constants.Status_Zero);

            }
            btnSelf.setTag(arrayGameScore);

            dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), ActivityIntegrityMain.this, userGame, rootRef, getString(R.string.fs_Usergame_userSelfWinScore), Constants.Game_SelfWin, totalGameScore);

            makeSnackBar(coordinatorLayout);


        } else if (i == R.id.btn_int_place) {

            csrIntegrityGame = dbHelper.getIntegrityScore(fbId, dayOfTheYear, yr);
            csrIntegrityGame.moveToFirst();

            BtnPlace.setTextColor(Color.BLACK);

            if (csrIntegrityGame.getCount() > 0) {

                selfWin = csrIntegrityGame.getString(Constants.DbSql_Integrity_SelfWin);
                placeWin = csrIntegrityGame.getString(Constants.DbSql_Integrity_PlaceWin);
                wordAgreement = csrIntegrityGame.getString(Constants.DbSql_Integrity_WordAgreement);
                respectWork = csrIntegrityGame.getString(Constants.DbSql_Integrity_RespectWork);
                wordAgreementItems = csrIntegrityGame.getString(Constants.HP_DbSql_Integrity_RespectWorkItems);
                dbHelper.updateIntegrityScore(Integer.parseInt(selfWin), Constants.Game_PlaceWin, Integer.parseInt(wordAgreement), Integer.parseInt(wordAgreementItems), Double.parseDouble(respectWork), fbId, dayOfTheYear, yr, 1);
            } else {
                dbHelper.insertIntegrityScore(0, Constants.Game_PlaceWin,
                        0, 0, 0,
                        fbId, dayOfTheYear, yr, 1);
            }

            UserStorageGameObject userStorageGameObject = new UserStorageGameObject();
            userStorageGameObject.setGameDocumentId(getIntent().getStringExtra(Constants.Intent_GameDocumentId));
            userStorageGameObject.setUserCoinsPerDay(getIntent().getIntExtra(Constants.Intent_GameCoinsPerDay, Constants.Status_Zero));
            userStorageGameObject.setUserExellenceBar(getIntent().getIntExtra(Constants.Intent_ExcellenceBar, Constants.Status_Zero));




            UserGame userGame = cls.loadUserGame(fbId, dayOfTheYear, yr, userName, userStorageGameObject);
            userGame.setUserPlaceWinScore(Constants.Game_PlaceWin);

            ArrayList<Integer> arrayNewGameScore = cls.createGameScore(Constants.Game_CP__UserPlaceWinScore, Constants.Game_PlaceWin, arrayGameScore, userGame, ActivityIntegrityMain.this);
            arrayGameScore = arrayNewGameScore;

            if (arrayNewGameScore.size() == 20) {
                userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Game_CP__UserTotatScore));
                totalGameScore = arrayGameScore.get(Constants.Game_CP__UserTotatScore);

            } else {
                userGame.setUserTotatScore(Constants.Game_PlaceWin);
                totalGameScore = arrayNewGameScore.get(Constants.Status_Zero);

            }
            btnSelf.setTag(arrayGameScore);
            dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), ActivityIntegrityMain.this, userGame, rootRef, getString(R.string.fs_Usergame_userPlaceWinScore), Constants.Game_PlaceWin, totalGameScore);

            makeSnackBar(coordinatorLayout);

        } else if (i == R.id.btn_int_completion) {

            Intent homepage = new Intent(ActivityIntegrityMain.this, RespectWork.class);
            Bundle mBundle = new Bundle();
            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));

            mBundle.putString(Constants.Intent_GameDocumentId, getIntent().getStringExtra(Constants.Intent_GameDocumentId));
            mBundle.putInt(Constants.Intent_GameCoinsPerDay, getIntent().getIntExtra(Constants.Intent_GameCoinsPerDay, Constants.Status_Zero));
            mBundle.putInt(Constants.Intent_ExcellenceBar, getIntent().getIntExtra(Constants.Intent_ExcellenceBar, Constants.Status_Zero));
            mBundle.putIntegerArrayList(getString(R.string.Intent_ArrayGameScore), (ArrayList<Integer>) btnSelf.getTag());
            homepage.putExtras(mBundle);
            startActivity(homepage);

        }
    }


    protected void getToastGamePlayed() {
        Toast.makeText(this, "Game Played Well!", Toast.LENGTH_LONG);
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
}