package app.actionnation.actionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import app.actionnation.actionapp.Database_Content.Personal_Habit;
import app.actionnation.actionapp.data.DbHelperClass;

public class HabitTraking extends BaseClassUser implements View.OnClickListener {
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_traking);
        generatePublicMenu();

        CommonClass cl = new CommonClass();

        mGoogleSignInClient = cl.GoogleStart(HabitTraking.this);

        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.listHabits);
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
