package app.actionnation.actionapp;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

import app.actionnation.actionapp.Database_Content.CommonData;
import app.actionnation.actionapp.Database_Content.Personal_Distraction;
import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

public class Activity_TrueLearning extends BaseClassUser implements View.OnClickListener {
    RecyclerView recyclerView;

    FirebaseAuth mAuth;
    ArrayList<String> strTlPattern = new ArrayList<>();
    String TAG = "True Learning";

    private FirebaseDatabase mFirebaseDatabase;
    FirebaseRecyclerAdapter fbAdapter;
    CoordinatorLayout coordinatorLayout;
    ExtendedFloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__true_learning);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = findViewById(R.id.listTrueLearning);
        coordinatorLayout = findViewById(R.id.cl_trueLearning);
        fab = findViewById(R.id.fab_act_trueLearning_finish);


        final String usrId = fetchUserId();

        Calendar c = Calendar.getInstance();
        int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
        int yr = c.get(Calendar.YEAR);

        DbHelper db = new DbHelper(Activity_TrueLearning.this);

        Log.d(TAG, "Enter Db");

        Cursor res = db.getDataTrueLearning(String.valueOf(dayOfYear), String.valueOf(yr), usrId);
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                Log.d(TAG, "Enter Db no count inside while" + res.getString(1));

                if (res.getString(2).equals("1")) {
                    strTlPattern.add(res.getString(1));
                    Log.d(TAG, "Enter Db");
                }
            }
        }

        CommonClass cl = new CommonClass();

        mGoogleSignInClient = cl.GoogleStart(Activity_TrueLearning.this);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_TrueLearning.this));
        recyclerView.setHasFixedSize(false);
        fetch(strTlPattern);
        //getDataFirebase();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                CommonClass cls = new CommonClass();
                int totalGameScore = 0;

                Calendar c = Calendar.getInstance();

                int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
                int yr = c.get(Calendar.YEAR);
                ArrayList<String> userArray = fetchUserArray();
                String userName = userArray.get(1);


                DbHelper db = new DbHelper(Activity_TrueLearning.this);
                Cursor cus = db.getDataTrueLearning(String.valueOf(dayOfYear), String.valueOf(yr), usrId);

                boolean trueLearningBool = false;
                if (cus.getCount() > 0) {
                    cus.moveToFirst();
                    trueLearningBool = true;
                }
                ArrayList<String> arrayCaptains = getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));
                UserGame userGame = cls.loadUserGame(usrId, dayOfYear, yr, arrayCaptains, userName);

                userGame.setUserTrueLearningScore(Constants.Game_TrueLearning);

                ArrayList<Integer> arrayGameScore = getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore)));

                ArrayList<Integer> arrayNewGameScore = cls.createGameScore(Constants.Game_CP__UserTrueLearningScore, Constants.Game_TrueLearning, arrayGameScore, userGame, Activity_TrueLearning.this);

                if (arrayNewGameScore.size() == 20) {
                    userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Game_CP__UserTotatScore));
                    arrayGameScore = arrayNewGameScore;
                    totalGameScore = arrayGameScore.get(Constants.Game_CP__UserTotatScore);
                } else {
                    userGame.setUserTotatScore(Constants.Game_TrueLearning);
                    totalGameScore = arrayNewGameScore.get(Constants.Status_Zero);
                }

                DbHelperClass dbHelperClass = new DbHelperClass();
                if (trueLearningBool) {
                    dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), Activity_TrueLearning.this, userGame, rootRef, getString(R.string.fs_Usergame_userTrueLearningScore), Constants.Game_TrueLearning, totalGameScore);
                    makeSnackBar(coordinatorLayout);

                }
            }
        });
    }


    private void fetch(final ArrayList<String> strTPattern) {

        Query qry = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).orderByChild("status").equalTo(6);

        FirebaseRecyclerOptions<CommonData> options = new FirebaseRecyclerOptions.Builder<CommonData>()
                .setQuery(qry, CommonData.class)
                .build();

        fbAdapter = new FirebaseRecyclerAdapter<CommonData, ViewHolderTrueLearning>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderTrueLearning holder, int position, @NonNull CommonData model) {

                holder.mIdTrueLearning.setText(model.getDataContent());
                Log.d(TAG, "Just Before Binding");

                if (strTPattern != null) {
                    if (strTPattern.contains(model.getDataContent())) {
                        Log.d(TAG, "Enter Checked changed no count inside while");

                        holder.chkTrueLearning.setChecked(true);
                        Log.d(TAG, "Enter Checked changed no count inside while After Checked + " + model.getDataContent());

                    }
                }

                holder.chkTrueLearning.setTag(model.getDataContent());
                holder.chkTrueLearning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        String usrId = fetchUserId();
                        int dayOfTheYear = fetchDate(0);
                        int yr = fetchDate(1);
                        DbHelper db = new DbHelper(Activity_TrueLearning.this);
                        if (isChecked == true) {
                            db.insertTrueLearning(holder.chkTrueLearning.getTag().toString(), 1, dayOfTheYear, usrId, yr);
                            CommonClass cls = new CommonClass();
                            cls.SubmitGenericGame(Constants.GS_trueLearningScore, db, usrId, dayOfTheYear, yr);
                        } else {
                            db.updateDataTrueLearning(holder.chkTrueLearning.getTag().toString(), 0, dayOfTheYear, usrId, yr);
                        }
                    }

                });

            }

            @NonNull
            @Override
            public ViewHolderTrueLearning onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_distractionlist, parent, false);
                ViewHolderTrueLearning viewHolder = new ViewHolderTrueLearning(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(fbAdapter);
        fbAdapter.startListening();
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


    public static class ViewHolderTrueLearning extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdTrueLearning;
        public final CheckBox chkTrueLearning;

        public Personal_Distraction mItem;

        public ViewHolderTrueLearning(View view) {
            super(view);
            mView = view;
            mIdTrueLearning = view.findViewById(R.id.distraction_name);
            chkTrueLearning = view.findViewById(R.id.chkDistraction);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdTrueLearning.getText() + "'";
        }
    }
}