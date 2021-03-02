package app.actionnation.actionapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

public class ActivityIntegrityMain extends BaseClassUser implements View.OnClickListener {

    Button btnSelf, BtnPlace, BtnWord, BtnWork, BtnDreamWin;
    DbHelper dbHelper;
    String fbId, placeWin, wordAgreement, respectWork, selfWin, wordAgreementItems;
    int dayOfTheYear, yr;
    Cursor csrIntegrityGame;
    FirebaseFirestore rootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrity_main);
        generatePublicMenu();


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


        dbHelper = new DbHelper(ActivityIntegrityMain.this);

        fbId = fetchUserId();
        dayOfTheYear = fetchDate(Constants.Status_Zero);
        yr = fetchDate(Constants.Status_One);
        //csrIntegrityGame = dbHelper.getIntegrityScore();
        csrIntegrityGame = dbHelper.getIntegrityScore(fbId, dayOfTheYear, yr);

        if (csrIntegrityGame.getCount() > Constants.Status_Zero) {

            while (csrIntegrityGame.moveToNext()) {

                selfWin = csrIntegrityGame.getString(Constants.DbSql_Integrity_SelfWin);
                placeWin = csrIntegrityGame.getString(Constants.DbSql_Integrity_PlaceWin);
                wordAgreement = csrIntegrityGame.getString(Constants.DbSql_Integrity_WordAgreement);
                respectWork = csrIntegrityGame.getString(Constants.DbSql_Integrity_RespectWork);
                wordAgreementItems = csrIntegrityGame.getString(Constants.HP_DbSql_Integrity_RespectWorkItems);
                if (selfWin.equals(getString(R.string.Game_SelfWin_Number))) {
                    btnSelf.setTextColor(Color.RED);
                }
                if (placeWin.equals(getString(R.string.Game_PlaceWin_Number))) {
                    BtnPlace.setTextColor(Color.RED);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        DbHelperClass dbHelperClass = new DbHelperClass();
        rootRef = FirebaseFirestore.getInstance();
        CommonClass cls = new CommonClass();
        if (i == R.id.btn_int_word) {
            Intent homepage = new Intent(ActivityIntegrityMain.this, ActivityIntegrity.class);
            Bundle mBundle = new Bundle();
            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
            mBundle.putStringArrayList(getString(R.string.Intent_ArrayCaptain), (ArrayList<String>) getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain))));

            homepage.putExtras(mBundle);
            startActivity(homepage);
        } else if (i == R.id.btn_int_targetWin) {
            Intent homepage = new Intent(ActivityIntegrityMain.this, HabitTraking.class);
            Bundle mBundle = new Bundle();
            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
            mBundle.putStringArrayList(getString(R.string.Intent_ArrayCaptain), (ArrayList<String>) getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain))));

            homepage.putExtras(mBundle);
            startActivity(homepage);
        } else if (i == R.id.btn_int_Self) {
            btnSelf.setTextColor(Color.RED);

            csrIntegrityGame = dbHelper.getIntegrityScore(fbId, dayOfTheYear, yr);
            csrIntegrityGame.moveToFirst();
            if (csrIntegrityGame.getCount() > Constants.Status_Zero) {
                selfWin = csrIntegrityGame.getString(Constants.DbSql_Integrity_SelfWin);
                placeWin = csrIntegrityGame.getString(Constants.DbSql_Integrity_PlaceWin);
                wordAgreement = csrIntegrityGame.getString(Constants.DbSql_Integrity_WordAgreement);
                respectWork = csrIntegrityGame.getString(Constants.DbSql_Integrity_RespectWork);
                wordAgreementItems = csrIntegrityGame.getString(Constants.HP_DbSql_Integrity_RespectWorkItems);

                dbHelper.updateIntegrityScore(25, Integer.parseInt(placeWin), Integer.parseInt(wordAgreement), Integer.parseInt(wordAgreementItems), Float.parseFloat(respectWork), fbId, dayOfTheYear, yr, 1);

            } else {

                dbHelper.insertIntegrityScore(Constants.Game_SelfWin, 0,
                        0, 0, 0,
                        fbId, dayOfTheYear, yr, Constants.Status_One);

            }
            ArrayList<String> arrayCaptains = getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));
            UserGame userGame = cls.loadUserGame(fbId, dayOfTheYear, yr, arrayCaptains);
            userGame.setUserSelfWinScore(Constants.Game_SelfWin);

            dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), ActivityIntegrityMain.this, userGame, rootRef, getString(R.string.fs_Usergame_userSelfWinScore), Constants.Game_SelfWin);


        } else if (i == R.id.btn_int_place) {

            csrIntegrityGame = dbHelper.getIntegrityScore(fbId, dayOfTheYear, yr);
            csrIntegrityGame.moveToFirst();

            BtnPlace.setTextColor(Color.RED);

            if (csrIntegrityGame.getCount() > 0) {

                selfWin = csrIntegrityGame.getString(Constants.DbSql_Integrity_SelfWin);
                placeWin = csrIntegrityGame.getString(Constants.DbSql_Integrity_PlaceWin);
                wordAgreement = csrIntegrityGame.getString(Constants.DbSql_Integrity_WordAgreement);
                respectWork = csrIntegrityGame.getString(Constants.DbSql_Integrity_RespectWork);
                wordAgreementItems = csrIntegrityGame.getString(Constants.HP_DbSql_Integrity_RespectWorkItems);
                dbHelper.updateIntegrityScore(Integer.parseInt(selfWin), 25, Integer.parseInt(wordAgreement), Integer.parseInt(wordAgreementItems), Double.parseDouble(respectWork), fbId, dayOfTheYear, yr, 1);
            } else {
                dbHelper.insertIntegrityScore(0, 25,
                        0, 0, 0,
                        fbId, dayOfTheYear, yr, 1);
            }

            ArrayList<String> arrayCaptains = getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));
            UserGame userGame = cls.loadUserGame(fbId, dayOfTheYear, yr, arrayCaptains);
            userGame.setUserPlaceWinScore(Constants.Game_PlaceWin);

            dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), ActivityIntegrityMain.this, userGame, rootRef, getString(R.string.fs_Usergame_userPlaceWinScore), Constants.Game_PlaceWin);


        } else if (i == R.id.btn_int_completion) {
            Intent homepage = new Intent(ActivityIntegrityMain.this, RespectWork.class);
            Bundle mBundle = new Bundle();
            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
            mBundle.putStringArrayList(getString(R.string.Intent_ArrayCaptain), (ArrayList<String>) getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain))));
            homepage.putExtras(mBundle);
            startActivity(homepage);

        }
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