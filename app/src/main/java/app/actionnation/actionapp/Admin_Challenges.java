package app.actionnation.actionapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.actionnation.actionapp.Database_Content.Challenges;
import app.actionnation.actionapp.Database_Content.Education;
import app.actionnation.actionapp.admin.AdminCommonClass;

public class Admin_Challenges extends AppCompatActivity {

    private static final String TAG = "DatabaseActivity";

    EditText etChalName, etChalDesc, etChalNumber;
    Spinner SpnEducation, spinner;
    Button btnSaveChal;
    Intent intent;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference mReferenceActionUniversity;
    //private static final String TAG = "GoogleActivity";
    Bundle extras;
    String StrChallengeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__challenges);
        etChalName = findViewById(R.id.txtNameChal);
        etChalDesc = findViewById(R.id.txtChalDesc);
        etChalNumber = findViewById(R.id.txtChalNumber);

        SpnEducation = findViewById(R.id.spnEduChal);

        btnSaveChal = findViewById(R.id.btnSaveChal);

        Toolbar toolbar = findViewById(R.id.toolbar_admin);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        //Spinner setup for active and Inactive
        setSpinnerStatus();

        //mReferenceActionUniversity = mFirebaseDatabase.getInstance().getReference().child("Challenges");
        mReferenceActionUniversity = mFirebaseDatabase.getInstance().getReference().child("Education");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        extras = getIntent().getExtras();

        if (extras != null) {
            StrChallengeId = extras.getString(getString(R.string.Intent_GetChallengeId));

            mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_Challenges)).orderByKey().equalTo(StrChallengeId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String ChallengeName = ds.getValue(Challenges.class).getChallenge_Name();
                        String ChallengeDesc = ds.getValue(Challenges.class).getChallenge_Desc();
                        String EduId = ds.getValue(Challenges.class).getEduid();
                        String ChallengeNumber = String.valueOf(ds.getValue(Challenges.class).getChallengenumber());

                        etChalName.setText(ChallengeName);
                        etChalDesc.setText(ChallengeDesc);
                        etChalNumber.setText(ChallengeNumber);

                        SpnEducation.setVisibility(View.GONE);
                        btnSaveChal.setText(getString(R.string.AC_Editbtn));
                        btnSaveChal.setTag(EduId);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {

            SpnEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Education ct = (Education) adapterView.getSelectedItem();
                    // txtSpinnerId.setText());
                    // txtSpinnerId.setText(((int) ct.getId()));
                    SpnEducation.setTag((ct.getFb_Id()));

                    Toast.makeText(getApplicationContext(), SpnEducation.getTag().toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            mReferenceActionUniversity.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    loadSpinnerData(dataSnapshot);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user1 = firebaseAuth.getCurrentUser();
                if (user1 != null) {
                    Toast.makeText(getApplicationContext(), "Details Inserted Successfully", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getApplicationContext(), "Details Inserted Not Successfully", Toast.LENGTH_SHORT).show();

                }
            }
        };

        // ON click listeners
        btnSaveChal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonClass cls = new CommonClass();

                if (extras != null) {
                    Challenges chal = new Challenges();
                    mReferenceActionUniversity = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_Challenges)).child(StrChallengeId);
                    chal.setChallenge_Name(etChalName.getText().toString());
                    chal.setChallenge_Desc(etChalDesc.getText().toString());
                    chal.setChallengenumber(Integer.parseInt(etChalNumber.getText().toString()));
                    chal.setEduid(btnSaveChal.getTag().toString());

                    if (spinner.getSelectedItem().equals(getString(R.string.Status_Active))) {
                        chal.setStatus(1);
                    } else if (spinner.getSelectedItem().equals(getString(R.string.Status_InActive))) {
                        chal.setStatus(0);
                    }
                    mReferenceActionUniversity.setValue(chal);
                    cls.callToast(Admin_Challenges.this, "Editing Done!");

                } else {
                    mReferenceActionUniversity = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_Challenges));

                    Challenges chal = new Challenges();
                    chal.setChallenge_Name(etChalName.getText().toString());
                    chal.setChallenge_Desc(etChalDesc.getText().toString());
                    chal.setChallengenumber(Integer.parseInt(etChalNumber.getText().toString()));
                    if (spinner.getSelectedItem().equals(getString(R.string.Status_Active))) {
                        chal.setStatus(1);
                    } else if (spinner.getSelectedItem().equals(getString(R.string.Status_InActive))) {
                        chal.setStatus(0);
                    }
                    chal.setEduid(SpnEducation.getTag().toString());

                    mReferenceActionUniversity.push().setValue(chal);
                    cls.callToast(Admin_Challenges.this, "Inserting Done!");

                }
            }
        });

        // ON click listeners

    }

    private void setSpinnerStatus() {
        spinner = findViewById(R.id.spnEduChalStatus);

        String[] data = {getString(R.string.Status_Active),
                getString(R.string.Status_InActive),

        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Admin_Challenges.this, android.R.layout.simple_spinner_item, data);

        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.menuadmin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        AdminCommonClass acc = new AdminCommonClass();
        return super.onOptionsItemSelected(acc.AdminMenuSelected(item, Admin_Challenges.this));
    }

    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadSpinnerData(DataSnapshot dataSnapshot) {
        // database handler
        List<Education> LEducation = new ArrayList<>();


        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            Log.w(TAG, "Admin_Challenges" + ds.getKey());
            Education uInfo = new Education();

            String Edu_Name = ds.getValue(Education.class).getEdu_name();
            String Value = String.valueOf(ds.getValue(Education.class).getStatus());
            String Category_Id = String.valueOf(ds.getValue(Education.class).getCategory_ID());
            // String Value = String.valueOf(ds1.getValue(Education.class).);

            String fb_Id = ds.getKey();

            uInfo.setCategory_ID(Category_Id);
            uInfo.setStatus(ds.getValue(Education.class).getStatus());
            uInfo.setEdu_name(Edu_Name);
            uInfo.setFb_Id(fb_Id);


            LEducation.add(uInfo);


            //   }
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, LEducation);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpnEducation.setAdapter(adapter);
        //SpnEducation.setSelection(1,true);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }
}
