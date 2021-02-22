package app.actionnation.actionapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.login.LoginManager;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.actionnation.actionapp.Database_Content.Challenges;
import app.actionnation.actionapp.Database_Content.UserCourse;

public class Detail_Challenges extends AppCompatActivity {
    private List<Challenges> challengesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    FirebaseRecyclerAdapter fbAdapter;


    private DatabaseReference myRef;
    private static final String TAG = "GoogleActivity";
    int challengesNumber;


    String value, strEduName, strChallengeId;
    private GoogleSignInClient mGoogleSignInClient;
    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__challenges);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString(getString(R.string.Intent_EduId));
            strEduName = extras.getString(getString(R.string.Intent_EduName));
        }
        getSupportActionBar().setTitle(strEduName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        final FirebaseUser fbUser = mAuth.getCurrentUser();
        if (mAuth.getCurrentUser() != null) {
            String usrName = fbUser.getDisplayName();
            String usrId = fbUser.getUid();
        } else {
            return;
        }

        myRef = FirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_UserCourse)).child(fbUser.getUid()).child(value);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    int clgNum = 1;

                    clgNum = dataSnapshot.getValue(UserCourse.class).getChallenge_Number();

                    myRef = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_Challenges));
                    FirebaseUser user = mAuth.getCurrentUser();

                    recyclerView = findViewById(R.id.rv_ShowChallanges);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Detail_Challenges.this));
                    recyclerView.setHasFixedSize(true);
                    fetch(clgNum);
                } catch (NullPointerException e) {
                    myRef = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_Challenges));
                    FirebaseUser user = mAuth.getCurrentUser();

                    recyclerView = findViewById(R.id.rv_ShowChallanges);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Detail_Challenges.this));
                    recyclerView.setHasFixedSize(true);
                    String param_LoadChallenges = getString(R.string.pm_firstLoad_Challenges);
                    fetch(Integer.parseInt(param_LoadChallenges));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.main_menu, menu);
        return true;
    }

    private void fetch(final Integer clgNumber) {

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            value = extras.getString(getString(R.string.Intent_EduId));
        }
        //Query qry = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_Challenges)).orderByChild("edu_Id").equalTo(value);
        Query qry = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_Challenges)).orderByChild(getString(R.string.fb_eduId_ChallengesTable)).equalTo(value);


        FirebaseRecyclerOptions<Challenges> options = new FirebaseRecyclerOptions.Builder<Challenges>()
                .setQuery(qry, Challenges.class)
                .build();

        fbAdapter = new FirebaseRecyclerAdapter<Challenges, FindViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FindViewHolder holder, int position, @NonNull final Challenges model) {
                challengesNumber = position;
                if (model.getChallengenumber() <= clgNumber) {
                    holder.mIdView.setText((model.getChallenge_Name()));
                    holder.mContentView.setText((String.valueOf(model.getChallengenumber())));
                    holder.mChallengeHeading.setText("Challenge " + (String.valueOf(model.getChallengenumber())));
                    holder.mChallengeHeading.setTag(model.getChallengenumber());
                    holder.mContentView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_orange));
                    holder.mContentView.setTextColor(ctx.getResources().getColor(android.R.color.white));
                    strChallengeId = fbAdapter.getRef(position).getKey();
                    holder.imgbtnChallengeClick.setTag(fbAdapter.getRef(position).getKey());

                } else {
                    holder.mIdView.setText((model.getChallenge_Name()));
                    holder.mContentView.setText((String.valueOf(model.getChallengenumber())));
                    holder.mChallengeHeading.setText("Challenge " + (String.valueOf(model.getChallengenumber())));
                    holder.mChallengeHeading.setTag(model.getChallengenumber());
                    holder.ivCheck.setVisibility(View.GONE);
                    holder.imgbtnChallengeClick.setTag(fbAdapter.getRef(position).getKey());

                }

                    holder.imgbtnChallengeClick.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                           Integer CurrentChallengeNo = Integer.parseInt(holder.mChallengeHeading.getTag().toString());

                           if(CurrentChallengeNo > clgNumber+1) {

                               AlertDialog.Builder builder = new AlertDialog.Builder(Detail_Challenges.this);
                               builder.setTitle(getString(R.string.common_confirm));
                               builder.setMessage(getString(R.string.DCC_AlertMessage));

                               builder.setPositiveButton(getString(R.string.common_yes), new DialogInterface.OnClickListener() {

                                   public void onClick(DialogInterface dialog, int which) {
                                       // Do nothing but close the dialog
                                       GotoExpandPage(String.valueOf(holder.imgbtnChallengeClick.getTag()),String.valueOf(holder.mChallengeHeading.getTag()));
                                       dialog.dismiss();
                                   }
                               });

                               builder.setNegativeButton(getString(R.string.common_no), new DialogInterface.OnClickListener() {

                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {

                                       // Do nothing
                                       dialog.dismiss();
                                   }
                               });

                               AlertDialog alert = builder.create();
                               alert.show();
                           }
                           else
                           {
                               GotoExpandPage(String.valueOf(holder.imgbtnChallengeClick.getTag()),String.valueOf(holder.mChallengeHeading.getTag()));
                           }

                        }
                    });
                }

            @NonNull
            @Override
            public FindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_challenges__expand, parent, false);
                FindViewHolder viewHolder = new FindViewHolder(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(fbAdapter);
        fbAdapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //  fbAdapter.startListening();
    }


    private void GotoExpandPage(String ChallengeId, String ChallengeNumber)
    {
        Intent in = getIntent();
        String StrData = in.getStringExtra(getString(R.string.Intent_Auth));
        String StrEduname = in.getStringExtra(getString(R.string.Intent_EduName));
        Intent homepage = new Intent(Detail_Challenges.this, OneChallenge_Expand.class);
        Bundle mBundle = new Bundle();
        mBundle.putString(getString(R.string.Intent_GetChallengeId), ChallengeId); //String.valueOf(holder.imgbtnChallengeClick.getTag())
        mBundle.putString(getString(R.string.Intent_Auth), StrData);
        mBundle.putString(getString(R.string.Intent_EduId), value);
        mBundle.putString(getString(R.string.Intent_EduName), StrEduname);
        mBundle.putString(getString(R.string.Intent_ChallengesNumber), ChallengeNumber); //String.valueOf(holder.mChallengeHeading.getTag())
        homepage.putExtras(mBundle);
        startActivity(homepage);
        finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
        // fbAdapter.stopListening();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menulogout:
                Intent in = getIntent();
                String StrData = in.getStringExtra(getString(R.string.Intent_Auth));
                // String StrData= in.getStringExtra("auth");
           /*     if (StrData.equals("fb")) {
                    Toast.makeText(Detail_Challenges.this, "Hi FB registered", Toast.LENGTH_LONG).show();
                    logoutFromFacebook();
                } else*/
                if (StrData.equals(getString(R.string.common_google))) {
                    signOut();
                }
                break;
            case R.id.itemFriendsInvite:
                InviteToActionNation obj = new InviteToActionNation();
                obj.CreateLink(Detail_Challenges.this);
                break;
            case android.R.id.home:
                Intent homepage = new Intent(Detail_Challenges.this, RedirectFromMain.class);
                Bundle mBundle = new Bundle();
                mBundle.putString(getString(R.string.Intent_Auth),getString(R.string.common_google));
                homepage.putExtras(mBundle);
                startActivity(homepage);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        // Firebase sign out
        // mAuth.signOut();
        // Google sign out
        mGoogleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(Detail_Challenges.this, GoFbLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public static class FindViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mChallengeHeading;
        public final ImageView ivCheck;
        public final ImageButton imgbtnChallengeClick;

        public Challenges mItem;

        public FindViewHolder(View view) {
            super(view);
            mView = view;

            mContentView = view.findViewById(R.id.rv_tx_ChallengeNo);
            mIdView = view.findViewById(R.id.rv_tx_ChallengeName);
            mChallengeHeading = view.findViewById(R.id.rv_tx_ChallengeHeading);
            ivCheck = view.findViewById(R.id.iv_ce_done);
            imgbtnChallengeClick = view.findViewById(R.id.imgbtn_clg_submit);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}







