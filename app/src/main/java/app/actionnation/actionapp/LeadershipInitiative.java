package app.actionnation.actionapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.actionnation.actionapp.Database_Content.leadershipprogram;

public class LeadershipInitiative extends AppCompatActivity implements BottomNavigationView.OnNavigationItemReselectedListener {

    GoogleSignInClient mGoogleSignInClient;
    TextView tvName, tvDesc, tvOpening, tvStep1, tvStep2, tvStep3, tvStep4, tvStep5, tvClosing, tvAboutLeadership;
    Button btnSave, btnShare;
    ImageButton ibtnInviteFriends;
    DatabaseReference rootRef;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leadership_initiative);
        activityInitializations();


        rootRef = FirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_leadershipProg));

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Bundle extras = getIntent().getExtras();
                    String value = "";
                    if (extras != null) {
                        value = extras.getString(getString(R.string.Intent_EduId));
                    }
                    String str = ds.getValue(leadershipprogram.class).getEduid();
                    if (str != null) {
                        if (str.equals(value)) {

                            String LeadershipName = ds.getValue(leadershipprogram.class).getLeadership_name();
                            String LeadershipDesc = ds.getValue(leadershipprogram.class).getLeadership_desc();
                            String OpeningStatement = ds.getValue(leadershipprogram.class).getLd_opening();
                            String Step1 = ds.getValue(leadershipprogram.class).getStep1();
                            String Step2 = ds.getValue(leadershipprogram.class).getStep2();
                            String Step3 = ds.getValue(leadershipprogram.class).getStep3();
                            String Step4 = ds.getValue(leadershipprogram.class).getStep4();
                            String Step5 = ds.getValue(leadershipprogram.class).getStep5();
                            String ClosingStatement = ds.getValue(leadershipprogram.class).getLd_closing();

                            tvName.setText(LeadershipName);
                            tvDesc.setText(getString(R.string.li_description) + LeadershipDesc);
                            tvOpening.setText(getString(R.string.li_introduction) + OpeningStatement);
                            tvStep1.setText(getString(R.string.li_step1) + Step1);
                            tvStep2.setText(getString(R.string.li_step2) + Step2);
                            tvStep3.setText(getString(R.string.li_step3) + Step3);
                            tvStep4.setText(getString(R.string.li_step4) + Step4);
                            tvStep5.setText(getString(R.string.li_step5) + Step5);
                            tvClosing.setText(getString(R.string.li_conclusion) + ClosingStatement);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            int grantResultsLength = grantResults.length;
            if (grantResultsLength > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "You grant write external storage permission. Please click original button again to continue.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "You denied write external storage permission.", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menulogout:
                CommonClass cls = new CommonClass();
                cls.menuGenerationGeneral(LeadershipInitiative.this, item, mGoogleSignInClient);
                break;
            case android.R.id.home:
                Intent homepage = new Intent(LeadershipInitiative.this, RedirectFromMain.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("auth", "google");
                homepage.putExtras(mBundle);
                startActivity(homepage);
                break;
            case R.id.itemFriendsInvite:
                InviteToActionNation obj = new InviteToActionNation();
                obj.CreateLink(LeadershipInitiative.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void onStart() {
        super.onStart();
        //  fbAdapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        // fbAdapter.stopListening();
    }


    //Activity Initializations
    private void activityInitializations() {
        //Text Views
        tvName = findViewById(R.id.tv_li_LeadershipName);
        tvDesc = findViewById(R.id.tv_li_LedershipDesc);
        tvOpening = findViewById(R.id.tv_li_Opening);
        tvStep1 = findViewById(R.id.tv_li_Step1);
        tvStep2 = findViewById(R.id.tv_li_Step2);
        tvStep3 = findViewById(R.id.tv_li_Step3);
        tvStep4 = findViewById(R.id.tv_li_Step4);
        tvStep5 = findViewById(R.id.tv_li_Step5);

        tvClosing = findViewById(R.id.tv_li_Ending);
        //Buttons
        //btnSave = findViewById(R.id.btn_li_Submit);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);


        Bundle extras = getIntent().getExtras();
        String value = "";
        String strEduId = "";
        String strEduName = "";
        if (extras != null) {
            value = extras.getString("get_ChallengeId");
            strEduId = extras.getString("EduId");
            strEduName = extras.getString("EduName");

        }
        getSupportActionBar().setTitle(strEduName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CommonClass cl = new CommonClass();
        mGoogleSignInClient = cl.GoogleStart(LeadershipInitiative.this);
    }


    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_share_single:
                CommonClass cls = new CommonClass();
                cls.ShareToOtherPlatforms("", "", "", "", LeadershipInitiative.this);
        }
    }
}
