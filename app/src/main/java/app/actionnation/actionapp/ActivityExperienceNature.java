package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

import app.actionnation.actionapp.Database_Content.CommonData;
import app.actionnation.actionapp.Database_Content.Personal_Distraction;
import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.Storage.UserStorageGameObject;
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

public class ActivityExperienceNature extends BaseClassUser {

    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    String usrId;
    FirebaseUser fbUser;
    String TAG = "Experience Nature";
    DbHelper db = new DbHelper(ActivityExperienceNature.this);
    CoordinatorLayout coordinatorLayout;
    ExtendedFloatingActionButton fab;

    private FirebaseDatabase mFirebaseDatabase;
    FirebaseRecyclerAdapter fbAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_nature);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = findViewById(R.id.fab_act_experienceNature_finish);


        mAuth = FirebaseAuth.getInstance();
        fbUser = mAuth.getCurrentUser();
        usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        } else {
            return;
        }
        recyclerView = findViewById(R.id.listNature);
        coordinatorLayout = findViewById(R.id.cl_expNature);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> userArray = fetchUserArray();
                String usrId = userArray.get(0);
                String userName = userArray.get(1);
                int dayOfTheYear = fetchDate(0);
                int yr = fetchDate(1);
                CommonClass cls = new CommonClass();
                cls.SubmitGenericGame(Constants.GS_natureScore, db, usrId, dayOfTheYear, yr);

                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

                UserStorageGameObject userStorageGameObject = new UserStorageGameObject();
                userStorageGameObject.setGameDocumentId(getIntent().getStringExtra(Constants.Intent_GameDocumentId));
                userStorageGameObject.setUserCoinsPerDay(getIntent().getIntExtra(Constants.Intent_GameCoinsPerDay, Constants.Status_Zero));
                userStorageGameObject.setUserExellenceBar(getIntent().getIntExtra(Constants.Intent_ExcellenceBar, Constants.Status_Zero));
                UserGame userGame = cls.loadUserGame(usrId, dayOfTheYear, yr, userName, userStorageGameObject);

                userGame.setUserDistractionScore(Constants.Game_ExperienceNature);

                int totalGameScore = 0;
                ArrayList<Integer> arrayGameScore = getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore)));

                ArrayList<Integer> arrayNewGameScore = cls.createGameScore(Constants.Game_CP__UserExperienceNatureScore, (int) Constants.Game_ExperienceNature, arrayGameScore, userGame, ActivityExperienceNature.this);

                if (arrayNewGameScore.size() == 20) {
                    userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Game_CP__UserTotatScore));
                    arrayGameScore = arrayNewGameScore;
                    totalGameScore = arrayGameScore.get(Constants.Game_CP__UserTotatScore);
                } else {
                    userGame.setUserTotatScore(Constants.Game_ExperienceNature);
                    totalGameScore = arrayNewGameScore.get(Constants.Status_Zero);
                }
                DbHelperClass dbHelperClass = new DbHelperClass();

                dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), ActivityExperienceNature.this, userGame, rootRef, getString(R.string.fs_Usergame_userExperienceNatureScore), Constants.Game_ExperienceNature, totalGameScore);
                makeSnackBar(coordinatorLayout);
            }
        });

        CommonClass cl = new CommonClass();

        mGoogleSignInClient = cl.GoogleStart(ActivityExperienceNature.this);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityExperienceNature.this));
        recyclerView.setHasFixedSize(false);
        fetch();
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


    private void fetch() {

        // Query qry = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).orderByChild("status").equalTo(6);


        Query qry = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).orderByChild("status").equalTo(Integer.valueOf(getString(R.string.aaq_Nature_Number)));

        FirebaseRecyclerOptions<CommonData> options = new FirebaseRecyclerOptions.Builder<CommonData>()
                .setQuery(qry, CommonData.class)
                .build();

        fbAdapter = new FirebaseRecyclerAdapter<CommonData, ActivityExperienceNature.ViewHolderNature>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderNature holder, int position, @NonNull CommonData model) {
                holder.mId.setText(model.getDataContent());

                holder.chk.setVisibility(View.INVISIBLE);
            }


            @NonNull
            @Override
            public ActivityExperienceNature.ViewHolderNature onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_distractionlist, parent, false);
                ActivityExperienceNature.ViewHolderNature viewHolder = new ActivityExperienceNature.ViewHolderNature(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(fbAdapter);
        fbAdapter.startListening();
    }


    public static class ViewHolderNature extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mId;
        public final CheckBox chk;

        public Personal_Distraction mItem;

        public ViewHolderNature(View view) {
            super(view);
            mView = view;
            mId = view.findViewById(R.id.distraction_name);
            chk = view.findViewById(R.id.chkDistraction);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mId.getText() + "'";
        }
    }


}