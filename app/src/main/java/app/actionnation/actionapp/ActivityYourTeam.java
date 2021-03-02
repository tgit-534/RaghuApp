package app.actionnation.actionapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import app.actionnation.actionapp.Database_Content.UserTeam;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelperClass;

public class ActivityYourTeam extends BaseClassUser {
    CoordinatorLayout coordinatorLayout;
    Button btnSubmitEmail;
    EditText etEmail;
    private final String TAG = getClass().getSimpleName();
    String usrId;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_team);
        generatePublicMenu();
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        btnSubmitEmail = findViewById(R.id.btn_team_invite);
        etEmail = findViewById(R.id.etInviteForTeam);
        getPlayerEmailId();
        mainDataUpdate();
        btnSubmitEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DbHelperClass Db = new DbHelperClass();
                mAuth = FirebaseAuth.getInstance();
                CommonClass cls = new CommonClass();
                usrId = cls.fetchUserId(mAuth);

                if (TextUtils.isEmpty(etEmail.getText().toString())) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Message is deleted", Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            });

                    snackbar.show();
                } else {
                    db = FirebaseFirestore.getInstance();

                    DbHelperClass dbh = new DbHelperClass();
                    UserTeam ut = new UserTeam();
                    ut.setFb_Id(fetchUserId());
                    ut.setStatus(Constants.Status_One);
                    List<String> teamMembers = new ArrayList<>();
                    teamMembers = (List<String>) btnSubmitEmail.getTag();
                    if (teamMembers.size() > 0)
                        teamMembers.add(etEmail.getText().toString());
                    else
                        teamMembers.add(fetchUserId());
                    ut.setTeamMembers(teamMembers);
                    mAuth = FirebaseAuth.getInstance();
                    ut.setTeamName(mAuth.getCurrentUser().getDisplayName());
                    dbh.insertFireUserTeam(getString(R.string.fs_UserTeam), ActivityYourTeam.this, ut, db);
                }
            }
        });
    }

    //Update the User Data
    private void mainDataUpdate() {
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection(getString(R.string.fs_UserTeam)).document(fetchUserId());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserTeam userTeam = documentSnapshot.toObject(UserTeam.class);

                if (userTeam != null) {
                    btnSubmitEmail.setTag(userTeam.getTeamMembers());

                }
            }
        });
    }


    private void getPlayerEmailId() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        ArrayList<String> arrayCaptain = getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));
        etEmail.setTag(arrayCaptain);

        if (arrayCaptain == null) {
            Task<QuerySnapshot> docRef = db.collection(getString(R.string.fs_UserTeam))
                    .whereArrayContains("teamMembers", mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {



                                    UserTeam userTeam = document.toObject(UserTeam.class);



                                    ArrayList<String> loopArrayCaptains = new ArrayList<>();

                                    loopArrayCaptains.add(fetchUserId());
                                    loopArrayCaptains.add(document.getId());

                                    FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                                    // Upload upload = new Upload(user1.getUid(), downloadUrl.getPath());
                                    DbHelperClass dbh = new DbHelperClass();

                                    //  dbh.updateFireUserProfile(getString(R.string.common_auth),ActivityMainObjectives.this, fetchUserId(),downloadUrl.getPath(),rootRef )
                                    dbh.updateFireUserProfile(getString(R.string.fs_UserProfile), ActivityYourTeam.this, fetchUserId(), getString(R.string.fs_UserProfile_teamCaptains), loopArrayCaptains , db);


                                    Log.d(TAG, document.getId() + " => " + document.getData() + " =" + userTeam.getFb_Id() + "james bond");
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }


                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, "Message is deleted", Snackbar.LENGTH_LONG)
                                    .setAction("UNDO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                            snackbar1.show();
                                        }
                                    });

                            snackbar.show();
                        }
                    });

        }
    }


}