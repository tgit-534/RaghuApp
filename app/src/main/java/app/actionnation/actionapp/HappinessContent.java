package app.actionnation.actionapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import app.actionnation.actionapp.data.DbHelper;

public class HappinessContent extends BaseClassUser implements View.OnClickListener {

    EditText etGratitudeName, etBeliefName, etBeliefDesc, etTraction, etReframe;
    Button btnSaveGratitude, btnBeliefSubmit, btnTractionSubmit, btnReframe;
    ImageButton btnNavigateNext, btnNavigatePrevious;
    ViewFlipper vf;
    FirebaseAuth mAuth;
    String usrId;
    FirebaseUser fbUser;
    String TAG = "Happy Content";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happiness_content);
        generatePublicMenu();
        vf = findViewById(R.id.vfHappiness);

        btnSaveGratitude = findViewById(R.id.btnHpSaveGratitude);
        btnNavigatePrevious = findViewById(R.id.btnHpPrev);
        btnTractionSubmit = findViewById(R.id.btnHpSaveTraction);
        btnReframe = findViewById(R.id.btnHpSaveReframe);

        btnNavigateNext = findViewById(R.id.btnHpNext);
        etGratitudeName = findViewById(R.id.etGratitude);
        btnBeliefSubmit = findViewById(R.id.btnHpSaveBelief);
        etBeliefDesc = findViewById(R.id.etBelieveDesc);
        etBeliefName = findViewById(R.id.etBelieveName);
        etTraction = findViewById(R.id.etTractionName);
        etReframe = findViewById(R.id.etReframeName);

        Bundle extras = getIntent().getExtras();
        String pageRedirect = "";
        if (extras != null) {
            pageRedirect = extras.getString(getString(R.string.Page_Redirect));
        }

        if (pageRedirect != null) {
            if (pageRedirect.equals(getString(R.string.Page_Redirect_Gratitude))) {
                vf.setDisplayedChild(0);
            } else if (pageRedirect.equals(getString(R.string.Page_Redirect_Belief))) {
                vf.setDisplayedChild(1);
            } else if (pageRedirect.equals(getString(R.string.Page_Redirect_Traction))) {
                vf.setDisplayedChild(2);
            } else if (pageRedirect.equals(getString(R.string.Page_Redirect_Abundance))) {
                vf.setDisplayedChild(3);
            }
        }

        btnSaveGratitude.setOnClickListener(this);
        btnNavigateNext.setOnClickListener(this);
        btnNavigatePrevious.setOnClickListener(this);
        btnBeliefSubmit.setOnClickListener(this);
        btnTractionSubmit.setOnClickListener(this);
        btnReframe.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        mAuth = FirebaseAuth.getInstance();
        fbUser = mAuth.getCurrentUser();
        usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        } else {
            return;
        }

        CommonClass cls = new CommonClass();
        DbHelper dbh = new DbHelper(HappinessContent.this);

        if (i == R.id.btnHpNext) {
            vf.showNext();

        } else if (i == R.id.btnHpPrev) {
            vf.showPrevious();
        } else if (i == R.id.btnHpSaveGratitude) {

            dbh.insertGratitudeList(etGratitudeName.getText().toString(), 1);
            clearForm((ViewGroup) findViewById(R.id.vfHappiness));

        } else if (i == R.id.btnHpSaveBelief) {
            dbh.insertBeliefList(etBeliefName.getText().toString(), etBeliefDesc.getText().toString(), 1, usrId);
        } else if (i == R.id.btnHpSaveTraction) {
            dbh.insertTractionList(etTraction.getText().toString(), 1, usrId);
            Log.d(TAG, "Save Traction");
            clearForm((ViewGroup) findViewById(R.id.vfHappiness));

        } else if (i == R.id.btnHpSaveReframe) {
            dbh.insertAbundance(etReframe.getText().toString(), usrId, 1);
            Log.d(TAG, "Save Reframe");
            clearForm((ViewGroup) findViewById(R.id.vfHappiness));

        }

    }


    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }
}
