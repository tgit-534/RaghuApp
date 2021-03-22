package app.actionnation.actionapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
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
import app.actionnation.actionapp.utils.TabsAdapterYourTeam;

public class ActivityYourTeam extends BaseClassUser implements View.OnClickListener {
    CoordinatorLayout coordinatorLayout;
    Button btnSubmitEmail, btnChooseCaptain;
    EditText etEmail;
    private final String TAG = getClass().getSimpleName();
    String usrId;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    ViewPager viewPager;
    TabLayout tabLayout;
    ArrayList<String> arrayCaptain = new ArrayList<>();
    ExtendedFloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_team);
        generatePublicMenu();
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        btnSubmitEmail = findViewById(R.id.btn_team_invite);
        btnChooseCaptain = findViewById(R.id.btn_team_chooseCaptain);
        fab = findViewById(R.id.fab_act_rate_captain);

        etEmail = findViewById(R.id.etInviteForTeam);
        arrayCaptain = getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));

        btnChooseCaptain.setOnClickListener(this);
        //getPlayerEmailId(arrayCaptain);
        mainDataUpdate();
        loadTabs();
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

                    ArrayList<String> strUser = fetchUserArray();

                    DbHelperClass dbh = new DbHelperClass();
                    UserTeam ut = new UserTeam();
                    ut.setFb_Id(strUser.get(0));
                    ut.setStatus(Constants.Status_One);
                    List<String> teamMembers = new ArrayList<>();
                    if (btnSubmitEmail.getTag() != null) {
                        teamMembers = (List<String>) btnSubmitEmail.getTag();
                        teamMembers.add(etEmail.getText().toString());
                    } else {
                        teamMembers.add(etEmail.getText().toString());
                    }
                    ut.setTeamMembers(teamMembers);
                    mAuth = FirebaseAuth.getInstance();
                    ut.setTeamName(mAuth.getCurrentUser().getDisplayName());
                    dbh.insertFireUserTeam(getString(R.string.fs_UserTeam), ActivityYourTeam.this, ut, db);
                }
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog();
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


    private void getPlayerEmailId(final ArrayList<String> strArrayCaptain) {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        etEmail.setTag(strArrayCaptain);

        if (strArrayCaptain == null) {
            Task<QuerySnapshot> docRef = db.collection(getString(R.string.fs_UserTeam))
                    .whereArrayContains(getString(R.string.fs_UserTeam_teamMembers), mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    UserTeam userTeam = document.toObject(UserTeam.class);
                                    ArrayList<String> loopArrayCaptains = new ArrayList<>();

                                    loopArrayCaptains.add(fetchUserId());
                                    loopArrayCaptains.add(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                                    loopArrayCaptains.add(document.getId());


                                    etEmail.setTag(loopArrayCaptains);
                                    FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                                    // Upload upload = new Upload(user1.getUid(), downloadUrl.getPath());
                                    Log.d(TAG, document.getId() + " => " + document.getData() + " =" + userTeam.getFb_Id() + "james bond");
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }


                            if (etEmail.getTag() != null) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityYourTeam.this);

                                builder.setTitle("Captain requested you to join the team!");
                                builder.setMessage("Are you sure?");

                                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        DbHelperClass dbh = new DbHelperClass();
                                        ArrayList<String> loopArrayCaptains = (ArrayList<String>) etEmail.getTag();
                                        dbh.updateFireUserProfile(getString(R.string.fs_UserProfile), ActivityYourTeam.this, fetchUserId(), getString(R.string.fs_UserProfile_teamCaptains), loopArrayCaptains, db);
                                        dialog.dismiss();


                                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Added to the team!", Snackbar.LENGTH_SHORT);
                                        snackbar1.show();
                                    }
                                });

                                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Not Added to the team!", Snackbar.LENGTH_SHORT);
                                        snackbar1.show();

                                        // Do nothing
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        }
                    });

        }
    }

    private void loadTabs() {
        viewPager = findViewById(R.id.view_pager_yourTeam);
        tabLayout = findViewById(R.id.tab_yourTeam);
        tabLayout.addTab(tabLayout.newTab().setText("Captain!"));
        tabLayout.addTab(tabLayout.newTab().setText("Player!"));
        Log.d(TAG, "2");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        ArrayList<String> arrayCaptains = getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));

        TabsAdapterYourTeam ds;
        ds = new TabsAdapterYourTeam(getSupportFragmentManager(), tabLayout.getTabCount(), arrayCaptains);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(ds);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_team_chooseCaptain) {
            Dialog dialog = new Dialog(ActivityYourTeam.this, R.style.DialogSlideAnim);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setContentView(R.layout.dialog_recycler);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.show();
            rvTest = dialog.findViewById(R.id.recyclerDialog);
            rvTest.setLayoutManager(new LinearLayoutManager(ActivityYourTeam.this));
            rvTest.setHasFixedSize(false);
            fetchChooseCaptain();

        }
    }

    RecyclerView rvTest;

    private void fetchChooseCaptain() {

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fbUser = mAuth.getCurrentUser();

        ArrayList<String> strArrayCaptain = getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));

        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        } else {
            return;
        }
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        com.google.firebase.firestore.Query query1 = db.collection(getString(R.string.fs_UserTeam))
                .whereArrayContains(getString(R.string.fs_UserTeam_teamMembers), mAuth.getCurrentUser().getEmail());


        DbHelperClass dbh = new DbHelperClass();
        adapter = dbh.GetFireStoreAdapterCaptains(adapter, getString(R.string.fs_UserTeam), query1, ActivityYourTeam.this, strArrayCaptain, usrId);

        rvTest.setAdapter(adapter);
        adapter.startListening();

    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentRateCaptain editNameDialogFragment = FragmentRateCaptain.newInstance("Some Title");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }


    public static class ViewHolderTeam extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdPlayerName;
        public final TextView mIdGameScore;

        public UserTeam mItem;

        public ViewHolderTeam(View view) {
            super(view);
            mView = view;
            mIdPlayerName = view.findViewById(R.id.tv_playerName);
            mIdGameScore = view.findViewById(R.id.tv_gameScore);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdPlayerName.getText() + "'";
        }
    }

    public static class ViewHolderSelectCaptain extends RecyclerView.ViewHolder {
        public final View mView;
        public final CheckBox mIdSelectCaptain;

        public UserTeam mItem;

        public ViewHolderSelectCaptain(View view) {
            super(view);
            mView = view;
            mIdSelectCaptain = view.findViewById(R.id.chk_selectCaptain);

        }

        @Override
        public String toString() {
            return super.toString() + " '" +
                    " "+ "'";
        }
    }


}