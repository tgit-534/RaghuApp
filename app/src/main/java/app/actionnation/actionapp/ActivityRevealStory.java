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

public class ActivityRevealStory extends BaseClassUser {
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    String usrId;
    FirebaseUser fbUser;
    DbHelper db = new DbHelper(ActivityRevealStory.this);
    CoordinatorLayout coordinatorLayout;
    ExtendedFloatingActionButton fab;

    String TAG = "Activity Reveal Story";

    private FirebaseDatabase mFirebaseDatabase;
    FirebaseRecyclerAdapter fbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal_story);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        usrId = fetchUserId();
        coordinatorLayout = findViewById(R.id.cl_revealStory);
        fab = findViewById(R.id.fab_act_revealStory_finish);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> userArray = fetchUserArray();
                int dayOfTheYear = fetchDate(0);
                int yr = fetchDate(1);
                CommonClass cls = new CommonClass();
                cls.SubmitGenericGame(Constants.GS_yourStoryScore, db, usrId, dayOfTheYear, yr);

                DbHelperClass dbHelperClass = new DbHelperClass();
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                ArrayList<String> arrayCaptains = getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));


                UserStorageGameObject userStorageGameObject = new UserStorageGameObject();
                userStorageGameObject.setGameDocumentId(getIntent().getStringExtra(Constants.Intent_GameDocumentId));
                userStorageGameObject.setUserCoinsPerDay(getIntent().getIntExtra(Constants.Intent_GameCoinsPerDay, Constants.Status_Zero));
                userStorageGameObject.setUserExellenceBar(getIntent().getIntExtra(Constants.Intent_ExcellenceBar, Constants.Status_Zero));

                UserGame userGame = cls.loadUserGame(userArray.get(0), dayOfTheYear, yr, arrayCaptains, userArray.get(1), userStorageGameObject);
                userGame.setUserRevealStoryScore(Constants.Game_RevealStory);

                int totalGameScore = 0;
                ArrayList<Integer> arrayGameScore = getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore)));

                ArrayList<Integer> arrayNewGameScore = cls.createGameScore(Constants.Game_CP__UserRevealStoryScore, Constants.Game_RevealStory, arrayGameScore, userGame, ActivityRevealStory.this);

                if (arrayNewGameScore.size() == 20) {
                    userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Game_CP__UserTotatScore));
                    arrayGameScore = arrayNewGameScore;
                    totalGameScore = arrayGameScore.get(Constants.Game_CP__UserTotatScore);
                } else {
                    userGame.setUserTotatScore(Constants.Game_RevealStory);
                    totalGameScore = arrayNewGameScore.get(Constants.Status_Zero);
                }

                dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), ActivityRevealStory.this, userGame, rootRef, getString(R.string.fs_Usergame_userRevealStoryScore), Constants.Game_RevealStory, totalGameScore);
                makeSnackBar(coordinatorLayout);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.listStory);
        CommonClass cl = new CommonClass();

        mGoogleSignInClient = cl.GoogleStart(ActivityRevealStory.this);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityRevealStory.this));
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


        Query qry = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).orderByChild("status").equalTo(Integer.valueOf(getString(R.string.aaq_Story_Number)));

        FirebaseRecyclerOptions<CommonData> options = new FirebaseRecyclerOptions.Builder<CommonData>()
                .setQuery(qry, CommonData.class)
                .build();

        fbAdapter = new FirebaseRecyclerAdapter<CommonData, ActivityRevealStory.ViewHolderStory>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderStory holder, int position, @NonNull CommonData model) {
                holder.mId.setText(model.getDataContent());
                holder.chk.setVisibility(View.INVISIBLE);
                holder.chk.setTag(model.getDataContent());
            }


            @NonNull
            @Override
            public ActivityRevealStory.ViewHolderStory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_distractionlist, parent, false);
                ActivityRevealStory.ViewHolderStory viewHolder = new ActivityRevealStory.ViewHolderStory(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(fbAdapter);
        fbAdapter.startListening();
    }


    public static class ViewHolderStory extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mId;
        public final CheckBox chk;

        public Personal_Distraction mItem;

        public ViewHolderStory(View view) {
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