package app.actionnation.actionapp;

import android.os.Bundle;
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

import app.actionnation.actionapp.Database_Content.Education;
import app.actionnation.actionapp.Database_Content.leadershipprogram;
import app.actionnation.actionapp.admin.AdminCommonClass;

public class adminleadership extends AppCompatActivity {

    EditText EtName, EtDesc, EtOpening, EtStep1, EtStep2, EtStep3, EtStep4, EtStep5, EtClosing;
    Button BtnSave, BtnUpdate, BtnCancel;
    Spinner spnLeadership;
    Bundle extras;
    String strEduId;


    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference mReferenceActionUniversity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminleadership);

        EtName = findViewById(R.id.al_et_LeadershipName);
        EtDesc = findViewById(R.id.al_et_LeadershipDesc);
        EtOpening = findViewById(R.id.al_et_Opening);
        EtStep1 = findViewById(R.id.al_et_Step1);
        EtStep2 = findViewById(R.id.al_et_Step2);
        EtStep3 = findViewById(R.id.al_et_Step3);
        EtStep4 = findViewById(R.id.al_et_Step4);
        EtStep5 = findViewById(R.id.al_et_Step5);
        EtClosing = findViewById(R.id.al_et_Ending);

        spnLeadership = findViewById(R.id.spn_al_edu);

        BtnSave = findViewById(R.id.btn_al_Save);
        BtnUpdate = findViewById(R.id.btn_al_Update);
        BtnCancel = findViewById(R.id.btn_al_Cancel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_admin);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        extras = getIntent().getExtras();
        if (extras != null) {
            strEduId = extras.getString(getString(R.string.Intent_EduId));
            mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_leadershipProg))
                    .orderByChild(getString(R.string.fb_eduId_ChallengesTable)).equalTo(strEduId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String strLeadershipName = ds.getValue(leadershipprogram.class).getLeadership_name().toString();
                                String strLeadershipDesc = ds.getValue(leadershipprogram.class).getLeadership_desc().toString();
                                /*String strStep1 = dataSnapshot.getValue(leadershipprogram.class).getStep1().toString();
                                String strStep2 = dataSnapshot.getValue(leadershipprogram.class).getStep2().toString();
                                String strStep3 = dataSnapshot.getValue(leadershipprogram.class).getStep3().toString();
                                String strStep4 = dataSnapshot.getValue(leadershipprogram.class).getStep4().toString();
                                String strStep5 = dataSnapshot.getValue(leadershipprogram.class).getStep5().toString();
                                String strOpening = dataSnapshot.getValue(leadershipprogram.class).getLd_opening().toString();
                                String strClosing = dataSnapshot.getValue(leadershipprogram.class).getLd_closing().toString();*/

                                EtName.setText(strLeadershipName);
                                EtDesc.setText(strLeadershipDesc);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


        } else {


            //mReferenceActionUniversity = mFirebaseDatabase.getInstance().getReference().child("Challenges");
            mReferenceActionUniversity = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_Education));

            mAuth = FirebaseAuth.getInstance();
            mFirebaseDatabase = FirebaseDatabase.getInstance();

            FirebaseUser user = mAuth.getCurrentUser();

            mReferenceActionUniversity.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    loadSpinnerData(dataSnapshot);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            spnLeadership.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Education ct = (Education) adapterView.getSelectedItem();
                    // txtSpinnerId.setText());
                    // txtSpinnerId.setText(((int) ct.getId()));
                    spnLeadership.setTag((ct.getFb_Id()));
                    Toast.makeText(getApplicationContext(), spnLeadership.getTag().toString(), Toast.LENGTH_SHORT).show();


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReferenceActionUniversity = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_leadershipProg));

                leadershipprogram lp = new leadershipprogram();
                lp.setLd_opening(EtOpening.getText().toString());
                lp.setLeadership_name(EtName.getText().toString());
                lp.setLeadership_desc(EtDesc.getText().toString());

                lp.setStep1(EtStep1.getText().toString());
                lp.setStep2(EtStep2.getText().toString());
                lp.setStep3(EtStep3.getText().toString());
                lp.setStep4(EtStep4.getText().toString());
                lp.setStep5(EtStep5.getText().toString());
                lp.setLd_closing(EtClosing.getText().toString());


                lp.setStatus(1);
                lp.setEduid(spnLeadership.getTag().toString());

                mReferenceActionUniversity.push().setValue(lp);
            }
        });


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
        return super.onOptionsItemSelected(acc.AdminMenuSelected(item, adminleadership.this));
    }

    private void loadSpinnerData(DataSnapshot dataSnapshot) {
        // database handler
        List<Education> LEducation = new ArrayList<>();


        for (DataSnapshot ds : dataSnapshot.getChildren()) {

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
        spnLeadership.setAdapter(adapter);
        //SpnEducation.setSelection(1,true);
    }


}
