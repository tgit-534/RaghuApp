package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import app.actionnation.actionapp.Database_Content.CommonData;
import app.actionnation.actionapp.Database_Content.Personal_Distraction;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelper;

public class ActivityRevealStory extends BaseClassUser {
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    String usrId;
    FirebaseUser fbUser;
    DbHelper db = new DbHelper(ActivityRevealStory.this);

    String TAG = "Activity Reveal Story";
    Button btnFinish;


    private FirebaseDatabase mFirebaseDatabase;
    FirebaseRecyclerAdapter fbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal_story);
        generatePublicMenu();
        usrId = fetchUserId();


        btnFinish = findViewById(R.id.btn_story_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usrId = fetchUserId();
                int dayOfTheYear = fetchDate(0);
                int yr = fetchDate(1);
                CommonClass cls = new CommonClass();
                cls.SubmitGenericGame(Constants.GS_yourStoryScore, db, usrId, dayOfTheYear, yr);
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