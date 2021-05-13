package app.actionnation.actionapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
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
    CoordinatorLayout coordinatorLayout;
    ExtendedFloatingActionButton fab;

    String usrId;
    DbHelper db = new DbHelper(ActivityOurBelief.this);

    String TAG = "Belief System";
    Button btnBelief;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_belief);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnBelief = findViewById(R.id.btn_addBelief);
        coordinatorLayout = findViewById(R.id.cl_belief);
        fab = findViewById(R.id.fab_act_belief_finish);
        recyclerView = findViewById(R.id.listBelief);

        usrId = fetchUserId();


        btnBelief.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fab.hide();
                } else {
                    fab.show();
                }


                super.onScrolled(recyclerView, dx, dy);
            }
        });*/


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonClass cls = new CommonClass();

                if (Integer.valueOf(btnBelief.getTag().toString()) == 0) {
                    cls.makeSnackBar(coordinatorLayout, getString(R.string.snakbar_NoData));
                    return;
                }

                ArrayList<String> userArray = fetchUserArray();

                usrId = userArray.get(0);
                int dayOfTheYear = fetchDate(0);
                int yr = fetchDate(1);
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
                    userGame.setUserTotatScore(Constants.Game_OurBeliefSystem);
                    totalGameScore = arrayNewGameScore.get(Constants.Status_Zero);
                }

                dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), ActivityOurBelief.this, userGame, rootRef, getString(R.string.fs_Usergame_userOurBeliefScore), Constants.Game_OurBeliefSystem, totalGameScore);
                makeSnackBar(coordinatorLayout, getString(R.string.snakbar_Success));
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
       // recyclerView.setLayoutManager(new LinearLayoutManager(ActivityOurBelief.this));

        BeliefAdapter mAdapter = new BeliefAdapter(ActivityOurBelief.this, res);
        btnBelief.setTag(mAdapter.getItemCount());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

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
    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentDataInsertion editNameFragment = FragmentDataInsertion.newInstance(getString(R.string.Page_Redirect_Belief));
        editNameFragment.show(fm, "fragment_edit_name");
    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_top) {
            recyclerView.smoothScrollToPosition(0);
            return true;
        } else if (id == R.id.action_bottom) {
            recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


}