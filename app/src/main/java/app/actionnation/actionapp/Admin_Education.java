package app.actionnation.actionapp;

import android.content.Intent;
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
import android.widget.TextView;
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

import app.actionnation.actionapp.Database_Content.Category;
import app.actionnation.actionapp.Database_Content.Education;
import app.actionnation.actionapp.admin.AdminCommonClass;

public class Admin_Education extends AppCompatActivity {
    private static final String TAG = "DatabaseActivity";

    EditText EduName;
    Spinner EduCategory, EduArea;
    Button saveBtnEdu;
    Intent intent;
    TextView txtSpinnerId;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference mReferenceActionUniversity;
    //private static final String TAG = "GoogleActivity";
    Bundle extras;
    String strEduId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__education);

        EduName = findViewById(R.id.txtNameEdu);
        EduCategory = (Spinner) findViewById(R.id.spnEduCat);
        txtSpinnerId = (TextView) findViewById(R.id.txtSpinnerId);
        saveBtnEdu = (Button) findViewById(R.id.btnSaveEdu);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_admin);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        setSpinnerStatus();

        extras = getIntent().getExtras();
        if (extras != null) {
            strEduId = extras.getString(getString(R.string.Intent_EduId));
            mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_Education)).child(strEduId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String strEduName = dataSnapshot.getValue(Education.class).getEdu_name().toString();
                    String strCategoryId = dataSnapshot.getValue(Education.class).getCategory_ID().toString();
                    EduCategory.setVisibility(View.GONE);
                    saveBtnEdu.setText(R.string.AC_Editbtn);
                    saveBtnEdu.setTag(strCategoryId);
                    EduName.setText(strEduName);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } else {

            mReferenceActionUniversity = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_Category_Db));

            EduCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    Category ct = (Category) adapterView.getSelectedItem();
                    EduCategory.setTag(ct.getFb_Id());
                    Toast.makeText(getApplicationContext(), EduCategory.getTag().toString(), Toast.LENGTH_SHORT).show();
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


        // ON click listeners
        saveBtnEdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonClass cls = new CommonClass();
                if (extras != null) {
                    mReferenceActionUniversity = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_Education)).child(strEduId);
                    Education edu = new Education();
                    edu.setEdu_name(EduName.getText().toString());

                    if (EduArea.getSelectedItem().equals(getString(R.string.AE_EduArea_Action))) {

                        edu.setStatus(Integer.parseInt(getString(R.string.AE_EduArea_Action_Number)));

                    } else if (EduArea.getSelectedItem().equals(getString(R.string.AE_EduArea_Inspire))) {

                        edu.setStatus(Integer.parseInt(getString(R.string.AE_EduArea_Inspire_Number)));

                    } else if (EduArea.getSelectedItem().equals(getString(R.string.AE_EduArea_Task))) {

                        edu.setStatus(Integer.parseInt(getString(R.string.AE_EduArea_Task_Number)));

                    } else if (EduArea.getSelectedItem().equals(getString(R.string.AE_EduArea_DeActivate))) {

                        edu.setStatus(Integer.parseInt(getString(R.string.AE_EduArea_DeActivate_Number)));

                    }


                    edu.setCategory_ID(String.valueOf(saveBtnEdu.getTag()));
                    mReferenceActionUniversity.setValue(edu);

                    cls.callToast(Admin_Education.this, getString(R.string.Toast_Updated));


                } else {

                    mReferenceActionUniversity = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_Education));
                    Education edu = new Education();
                    edu.setEdu_name(EduName.getText().toString());

                    if (EduArea.getSelectedItem().equals(getString(R.string.AE_EduArea_Action))) {

                        edu.setStatus(Integer.parseInt(getString(R.string.AE_EduArea_Action_Number)));

                    } else if (EduArea.getSelectedItem().equals(getString(R.string.AE_EduArea_Inspire))) {

                        edu.setStatus(Integer.parseInt(getString(R.string.AE_EduArea_Inspire_Number)));

                    } else if (EduArea.getSelectedItem().equals(getString(R.string.AE_EduArea_Task))) {

                        edu.setStatus(Integer.parseInt(getString(R.string.AE_EduArea_Task_Number)));

                    } else if (EduArea.getSelectedItem().equals(getString(R.string.AE_EduArea_DeActivate))) {

                        edu.setStatus(Integer.parseInt(getString(R.string.AE_EduArea_DeActivate_Number)));
                    }
                    edu.setCategory_ID(String.valueOf(EduCategory.getTag()));
                    mReferenceActionUniversity.push().setValue(edu);
                    cls.callToast(Admin_Education.this, getString(R.string.Toast_Inserted));

                }
            }
        });

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


    }

    private void setSpinnerStatus() {
        EduArea = (Spinner) findViewById(R.id.spnEduArea);

        String[] data = {getString(R.string.AE_EduArea_Action),
                getString(R.string.AE_EduArea_Inspire),
                getString(R.string.AE_EduArea_Task),
                getString(R.string.AE_EduArea_DeActivate)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Admin_Education.this, android.R.layout.simple_spinner_item, data);

        EduArea.setAdapter(adapter);
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
        return super.onOptionsItemSelected(acc.AdminMenuSelected(item, Admin_Education.this));
    }

    private void loadSpinnerData(DataSnapshot dataSnapshot) {
        // database handler
        List<Category> LCategory = new ArrayList<>();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Category uInfo = new Category();
            String CategoryName1 = ds.getValue(Category.class).getCategoryName();
            String fb_Id = ds.getKey();
            uInfo.setCategoryName(CategoryName1);
            uInfo.setStatus(ds.getValue(Category.class).getStatus());
            uInfo.setFb_Id(fb_Id);
            LCategory.add(uInfo);

        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, LCategory);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EduCategory.setAdapter(adapter);
        EduCategory.setSelection(1, true);
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

    public void getSelectedUser(View view) {
        Category ct = (Category) EduCategory.getSelectedItem();
        displayUserData(ct);
    }

    public void displayUserData(Category ct) {
        String categoryName = ct.getCategoryName();
        //  long categoryId = ct.getId();
        Toast.makeText(this, categoryName, Toast.LENGTH_LONG).show();
    }
}
