package app.actionnation.actionapp;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;

import app.actionnation.actionapp.data.DbHelper;

public class RespectWork extends BaseClassUser implements View.OnClickListener {


    Button btnFinished;
    Button btnAbandoned;
    Button btnUnfinished;
    TextView txtFinished, txtAbandoned, txtincomplete;
    DbHelper db = new DbHelper(RespectWork.this);
    int countData = 0;
    int countDataRespectWork = 0;
    FirebaseAuth mAuth;
    String usrId = "";
    int dayOfTheYear, yr;
    Cursor csrIntegrityGame;
    private static final String TAG = "Respect Work:Log";

    String placeWin, wordAgreement, respectWork, selfWin, wordAgreementItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respect_work);
        generatePublicMenu();

        btnFinished = findViewById(R.id.btn_rw_Finished);
        btnAbandoned = findViewById(R.id.btn_rw_Abandoned);
        btnUnfinished = findViewById(R.id.btn_rw_InComplete);

        txtFinished = findViewById(R.id.txt_rw_finished);
        txtAbandoned = findViewById(R.id.txt_rw_abandoned);
        txtincomplete = findViewById(R.id.txt_rw_Incomplete);


        btnFinished.setOnClickListener(this);
        btnAbandoned.setOnClickListener(this);
        btnUnfinished.setOnClickListener(this);


        usrId = fetchUserId();
        dayOfTheYear = fetchDate(0);
        yr = fetchDate(1);

        csrIntegrityGame = db.getIntegrityScore(usrId, dayOfTheYear, yr);
        countData = csrIntegrityGame.getCount();
        if (countData > 0) {

            while (csrIntegrityGame.moveToNext()) {

                selfWin = csrIntegrityGame.getString(2);
                placeWin = csrIntegrityGame.getString(3);
                wordAgreement = csrIntegrityGame.getString(4);
                respectWork = csrIntegrityGame.getString(5);
                wordAgreementItems = csrIntegrityGame.getString(9);

            }
        }


        Cursor res = db.getDataRespectWork(Integer.toString(dayOfTheYear));
        CommonClass cls = new CommonClass();

        countDataRespectWork = res.getCount();
        String strExcercisePattern = "";
        if (res.getCount() > 0) {

            while (res.moveToNext()) {
                //res.getString(2);
                cls.callToast(RespectWork.this, res.getString(2));
                txtFinished.setText(res.getString(2));
                txtincomplete.setText(res.getString(3));
                txtAbandoned.setText(res.getString(4));
            }

        }


    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        int finished, abandoned, incomplete;
        finished = Integer.parseInt(txtFinished.getText().toString());
        abandoned = Integer.parseInt(txtAbandoned.getText().toString());
        incomplete = Integer.parseInt(txtincomplete.getText().toString());

        if (i == R.id.btn_rw_Finished) {
            finished = finished + 1;
            txtFinished.setText(String.valueOf(finished));

            insertWorkStatus(usrId, finished, incomplete, abandoned, dayOfTheYear);
            insertWorkwinIntegrityTable(finished, abandoned, incomplete);

        } else if (i == R.id.btn_rw_Abandoned) {
            abandoned = abandoned + 1;
            txtAbandoned.setText(String.valueOf(abandoned));
            insertWorkStatus(usrId, finished, incomplete, abandoned, dayOfTheYear);

            insertWorkwinIntegrityTable(finished, abandoned, incomplete);


        } else if (i == R.id.btn_rw_InComplete) {
            incomplete = incomplete + 1;
            txtincomplete.setText(String.valueOf(incomplete));
            insertWorkStatus(usrId, finished, incomplete, abandoned, dayOfTheYear);


            insertWorkwinIntegrityTable(finished, abandoned, incomplete);

        }
    }


    private void insertWorkStatus(String usrId, int finished, int incomplete, int abandoned, int dayOfTheYear) {
        if (countDataRespectWork > 0) {
            db.updateRespectWork(usrId, finished, incomplete, abandoned, dayOfTheYear);

            //Insert
        } else {
            db.insertRespectWork(usrId, finished, incomplete, abandoned, dayOfTheYear);

        }


    }


    private double percentageCounter(int finished, int abadoned, int incomplete) {
        int totalWorkStatus = finished + abadoned + incomplete;
        double percentValue = (double) finished / totalWorkStatus;
        DecimalFormat df2 = new DecimalFormat("#.##");

        percentValue = Double.valueOf(df2.format(percentValue));
        //   percentValue = df2.format(String.valueOf(percentValue);
        Log.d(TAG, String.valueOf(percentValue));
        CommonClass cls = new CommonClass();
        cls.callToast(RespectWork.this, String.valueOf(percentValue));

        return percentValue * 100;
    }

    private void insertWorkwinIntegrityTable(int finished, int abandoned, int incomplete) {
        usrId = fetchUserId();
        dayOfTheYear = fetchDate(0);
        yr = fetchDate(1);
        csrIntegrityGame = db.getIntegrityScore(usrId, dayOfTheYear, yr);
        countData = csrIntegrityGame.getCount();

        if (csrIntegrityGame.getCount() > 0) {


            db.updateIntegrityScore(Integer.parseInt(selfWin), Integer.parseInt(placeWin), Integer.parseInt(wordAgreement), Integer.parseInt(wordAgreementItems), percentageCounter(finished, abandoned, incomplete), usrId, dayOfTheYear, yr, 1);

        } else {


            db.insertIntegrityScore(0, 0,
                    0, 0, percentageCounter(finished, abandoned, incomplete),
                    fetchUserId(), fetchDate(0), fetchDate(1), 1);

        }
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
}