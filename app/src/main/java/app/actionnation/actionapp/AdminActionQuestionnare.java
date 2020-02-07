package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.actionnation.actionapp.Database_Content.CommonData;

public class AdminActionQuestionnare extends AdminCommonClass implements View.OnClickListener {


    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference mReferenceActionUniversity;
    EditText etData, etDataNumber, etDataExpand;
    Spinner spnStatus;
    Button btnSave;
    Bundle extras;
    String StrCommonDataId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_action_questionnare);
        generatePublicMenu();
        extras = getIntent().getExtras();


        InitializeControls();
        setSpinnerStatus();
        if (extras != null) {
            StrCommonDataId = extras.getString(getString(R.string.Intent_CommonDataId));
            mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).child(StrCommonDataId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String strCommonData = dataSnapshot.getValue(CommonData.class).getDataString().toString();
                    String strCommonDataExpand = dataSnapshot.getValue(CommonData.class).getDataContent().toString();
                    String strDataNumber = String.valueOf(dataSnapshot.getValue(CommonData.class).getDataNumber());
                    btnSave.setText(R.string.AC_Editbtn);
                    btnSave.setTag(StrCommonDataId);
                    etData.setText(strCommonData);
                    etDataExpand.setText(strCommonDataExpand);
                    etDataNumber.setText(strDataNumber);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        btnSave.setOnClickListener(this);
    }

    private void InitializeControls() {
        etData = findViewById(R.id.et_aaq_Data);
        etDataNumber = findViewById(R.id.et_aaq_Number);
        etDataExpand = findViewById(R.id.et_aaq_DataExpand);
        spnStatus = findViewById(R.id.spn_aaq_Status);
        btnSave = findViewById(R.id.btn_aaq_Save);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdminMenuSelected(item);
        return super.onOptionsItemSelected(AdminMenuSelected(item));
    }


    private void setSpinnerStatus() {
        String[] data = {getString(R.string.aaq_CareerQuestionnare),
                getString(R.string.aaq_values),
                getString(R.string.aaq_Affirmations),
                getString(R.string.aaq_Inactive),

        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminActionQuestionnare.this, android.R.layout.simple_spinner_item, data);
        spnStatus.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        CommonClass cls = new CommonClass();
        int i = view.getId();
        if (i == R.id.btn_aaq_Save) {

            if (extras != null) {
                mReferenceActionUniversity = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).child(StrCommonDataId);
                CommonData aq = new CommonData();
                aq.setDataString(etData.getText().toString());
                aq.setDataContent(etDataExpand.getText().toString());
                if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_CareerQuestionnare))) {
                    aq.setStatus(Integer.parseInt(getString(R.string.aaq_CareerQuestionnare_Number)));
                } else if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_values))) {
                    aq.setStatus(Integer.parseInt(getString(R.string.aaq_values_Number)));
                } else if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_Affirmations))) {

                    aq.setStatus(Integer.parseInt(getString(R.string.aaq_Affirmations_Number)));
                }
                if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_Inactive))) {

                    aq.setStatus(Integer.parseInt(getString(R.string.aaq_Inactive_Number)));
                }
                aq.setDataNumber(Integer.parseInt(etDataNumber.getText().toString()));
                mReferenceActionUniversity.setValue(aq);
                cls.callToast(AdminActionQuestionnare.this, getString(R.string.Toast_Inserted));
            } else {
                mReferenceActionUniversity = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db));
                CommonData aq = new CommonData();
                aq.setDataString(etData.getText().toString());
                aq.setDataContent(etDataExpand.getText().toString());
                if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_CareerQuestionnare))) {
                    aq.setStatus(Integer.parseInt(getString(R.string.aaq_CareerQuestionnare_Number)));
                } else if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_values))) {
                    aq.setStatus(Integer.parseInt(getString(R.string.aaq_values_Number)));
                } else if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_Affirmations))) {

                    aq.setStatus(Integer.parseInt(getString(R.string.aaq_Affirmations_Number)));
                }
                if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_Inactive))) {

                    aq.setStatus(Integer.parseInt(getString(R.string.aaq_Inactive_Number)));
                }
                aq.setDataNumber(Integer.parseInt(etDataNumber.getText().toString()));
                mReferenceActionUniversity.push().setValue(aq);
                cls.callToast(AdminActionQuestionnare.this, getString(R.string.Toast_Inserted));
            }
        }
    }
}