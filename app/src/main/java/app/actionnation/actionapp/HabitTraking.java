package app.actionnation.actionapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

import app.actionnation.actionapp.Database_Content.Personal_Habit;
import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

public class HabitTraking extends BaseClassUser implements View.OnClickListener {
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth mAuth;
    ExtendedFloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_traking);
        generatePublicMenu();

        recyclerView = findViewById(R.id.listHabits);
        fab = findViewById(R.id.fab_act_habit_finish);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fab.hide();
                } else {
                    fab.show();
                }


                super.onScrolled(recyclerView, dx, dy);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // recyclerView.smoothScrollToPosition(0);
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                CommonClass cls = new CommonClass();
                Calendar c = Calendar.getInstance();

                int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
                int yr = c.get(Calendar.YEAR);
                ArrayList<String> userArray = fetchUserArray();
                String usrId = userArray.get(0);
                String userName = userArray.get(1);

                DbHelper db = new DbHelper(HabitTraking.this);
                Cursor cus = db.getHabitScore(usrId, dayOfYear, yr);

                int HabitScore = 0;
                int HabitTot = 0;
                if (cus.getCount() > 0) {
                    cus.moveToFirst();

                    HabitScore = Integer.parseInt(cus.getString(Constants.Game_AS_HabitScore));
                    HabitTot = Integer.parseInt(cus.getString(Constants.Game_AS_TotHabit));

                }
                double gameHabitScore = (double) (HabitScore) / HabitTot;

                gameHabitScore = gameHabitScore * Constants.Game_Habits;

                ArrayList<String> arrayCaptains = getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));
                UserGame userGame = cls.loadUserGame(usrId, dayOfYear, yr, arrayCaptains, userName);

                userGame.setUserHabitsScore((int) gameHabitScore);


                int totalGameScore = 0;
                ArrayList<Integer> arrayGameScore = getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore)));

                ArrayList<Integer> arrayNewGameScore = cls.createGameScore(Constants.Game_CP__UserHabitsScore, (int) gameHabitScore, arrayGameScore, userGame, HabitTraking.this);

                if (arrayNewGameScore.size() == 20) {
                    userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Game_CP__UserTotatScore));
                    arrayGameScore = arrayNewGameScore;
                    totalGameScore = arrayGameScore.get(Constants.Game_CP__UserTotatScore);
                } else {
                    userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Status_Zero));
                    totalGameScore = arrayNewGameScore.get(Constants.Status_Zero);

                }

                DbHelperClass dbHelperClass = new DbHelperClass();
                dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), HabitTraking.this, userGame, rootRef, getString(R.string.fs_Usergame_userHabitsScore), (int) gameHabitScore, totalGameScore);
            }
        });

        CommonClass cl = new CommonClass();
        mGoogleSignInClient = cl.GoogleStart(HabitTraking.this);
        mAuth = FirebaseAuth.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(HabitTraking.this));
        recyclerView.setHasFixedSize(false);
        fetch();

    }

    private void fetch() {
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        } else {
            return;
        }
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        com.google.firebase.firestore.Query query1 = rootRef.collection(getString(R.string.fs_PersonalHabits)).whereEqualTo(getString(R.string.fb_Column_Fb_Id), usrId);

        DbHelperClass dbh = new DbHelperClass();
        adapter = dbh.GetFireStoreAdapterHabits(adapter, getString(R.string.fs_PersonalHabits), query1, HabitTraking.this, usrId);

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onClick(View view) {

    }

    public void RedirectHabit(View view) {
        Intent homepage = new Intent(HabitTraking.this, PersondetailsActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
        mBundle.putString(getString(R.string.Page_Redirect), getString(R.string.Page_Redirect_Habit));
        homepage.putExtras(mBundle);
        startActivity(homepage);
    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public static class ViewHolderHabit extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdHabit;
        public final Button btnHabitSubmit;
        public Personal_Habit mItem;

        public ViewHolderHabit(View view) {
            super(view);
            mView = view;
            mIdHabit = view.findViewById(R.id.habit_name);
            btnHabitSubmit = view.findViewById(R.id.btn_ht_HabitSubmit);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdHabit.getText() + "'";
        }
    }
}
