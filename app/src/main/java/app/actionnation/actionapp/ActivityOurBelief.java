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

import java.util.ArrayList;

import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelper;

public class ActivityOurBelief extends BaseClassUser implements View.OnClickListener {

    RecyclerView recyclerView;
    ArrayList<String> strAttPattern = new ArrayList<>();
    FirebaseAuth mAuth;
    String usrId;
    FirebaseUser fbUser;
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
                usrId = fetchUserId();
                int dayOfTheYear = fetchDate(0);
                int yr = fetchDate(1);
                CommonClass cls = new CommonClass();
                cls.SubmitGenericGame(Constants.GS_ourBeliefScore, db, usrId, dayOfTheYear, yr);
            }
        });
        Cursor res = db.getBeliefList(usrId);
        if (res.getCount() == 0) {
            // return;
        } else {
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