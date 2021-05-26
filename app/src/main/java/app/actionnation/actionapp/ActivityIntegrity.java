package app.actionnation.actionapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.actionnation.actionapp.Database_Content.Person_Integrity;
import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.Storage.UserStorageGameObject;
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

public class ActivityIntegrity extends BaseClassUser implements View.OnClickListener {

    Button btnDatePicker, btnTimePicker, btnSubmitIntegrity;
    EditText txtDate, txtTime, txtIntegrityDesc;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Date dtIntegrity;
    SimpleDateFormat simpleDateFormat;
    private FirebaseFirestore db;
    Date dt;
    Long dateInMilli;
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth mAuth;
    ExtendedFloatingActionButton fab;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrity);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        linearLayout = findViewById(R.id.ll_Integrity);
        btnDatePicker = findViewById(R.id.btn_date);
        btnTimePicker = findViewById(R.id.btn_time);
        btnSubmitIntegrity = findViewById(R.id.in_btn_submit);
        recyclerView = findViewById(R.id.listIntegrity);

        fab = findViewById(R.id.fab_act_int_finish);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fab.hide();
                } else {
                    fab.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // recyclerView.smoothScrollToPosition(0);
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                CommonClass cls = new CommonClass();
                Calendar c = Calendar.getInstance();
                int totalGameScore = 0;
                int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
                int yr = c.get(Calendar.YEAR);
                String usrId = fetchUserId();
                ArrayList<String> userArray = fetchUserArray();
                usrId = userArray.get(0);
                String userName = userArray.get(1);


                DbHelper db = new DbHelper(ActivityIntegrity.this);
                Cursor cus = db.getIntegrityScore(usrId, dayOfYear, yr);

                int wordScore = 0;
                int wordTot = 0;
                if (cus.getCount() > 0) {
                    cus.moveToFirst();

                    wordScore = Integer.parseInt(cus.getString(Constants.Game_AS_WordScore));
                    wordTot = Integer.parseInt(cus.getString(Constants.Game_AS_TotWordScore));

                }
                cus.close();

                if (wordScore == 0) {
                    makeSnackBar(linearLayout, getString(R.string.snakbar_NoData));
                    return;
                }

                double gameWordScore = (double) (wordScore) / wordTot;

                gameWordScore = gameWordScore * Constants.Game_WordWin;

                ArrayList<String> arrayCaptains = getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));


                UserStorageGameObject userStorageGameObject = new UserStorageGameObject();
                userStorageGameObject.setGameDocumentId(getIntent().getStringExtra(Constants.Intent_GameDocumentId));
                userStorageGameObject.setUserCoinsPerDay(getIntent().getIntExtra(Constants.Intent_GameCoinsPerDay, Constants.Status_Zero));
                userStorageGameObject.setUserExellenceBar(getIntent().getIntExtra(Constants.Intent_ExcellenceBar, Constants.Status_Zero));

                UserGame userGame = cls.loadUserGame(usrId, dayOfYear, yr, arrayCaptains, userName, userStorageGameObject);
                userGame.setUserWordWinScore((int) gameWordScore);


                ArrayList<Integer> arrayGameScore = getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore)));

                ArrayList<Integer> arrayNewGameScore = cls.createGameScore(Constants.Game_CP__UserWordWinScore, (int) gameWordScore, arrayGameScore, userGame, ActivityIntegrity.this);

                if (arrayNewGameScore.size() == 20) {
                    userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Game_CP__UserTotatScore));
                    arrayGameScore = arrayNewGameScore;
                    totalGameScore = arrayGameScore.get(Constants.Game_CP__UserTotatScore);
                } else {
                    userGame.setUserTotatScore((int) gameWordScore);
                    totalGameScore = arrayNewGameScore.get(Constants.Status_Zero);

                }

                DbHelperClass dbHelperClass = new DbHelperClass();

                dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), ActivityIntegrity.this, userGame, rootRef, getString(R.string.fs_Usergame_userWordWinScore), (int) gameWordScore, totalGameScore);
                makeSnackBar(linearLayout, getString(R.string.snakbar_Success));

            }
        });


        txtDate = findViewById(R.id.in_date);
        txtTime = findViewById(R.id.in_time);
        txtIntegrityDesc = findViewById(R.id.in_et_Promise);
        dtIntegrity = new Date();
        //  btnDatePicker.setBackgroundResource(R.drawable.button_border);
        btnDatePicker.setBackground(getResources().getDrawable(R.drawable.button_border));


        mAuth = FirebaseAuth.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityIntegrity.this));
        recyclerView.setHasFixedSize(false);
        fetch();

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnSubmitIntegrity.setOnClickListener(this);

    }

    private void fetch() {
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        } else {
            return;
        }


        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        Calendar calToday = Calendar.getInstance();
        calToday.setTimeInMillis(Calendar.getInstance().getTimeInMillis());

        Calendar cNew = Calendar.getInstance();

        cNew.set(calToday.get(Calendar.YEAR), calToday.get(Calendar.MONTH), calToday.get(Calendar.DAY_OF_MONTH), 00, 00);

        //com.google.firebase.firestore.Query query1 = rootRef.collection(getString(R.string.fs_PersonalHabits)).whereEqualTo(getString(R.string.fb_Column_Fb_Id), usrId);
        com.google.firebase.firestore.Query query1 = rootRef.collection(getString(R.string.fs_PersonIntegrity))
                .whereEqualTo(getString(R.string.fb_Column_Fb_Id), usrId).whereGreaterThan(getString(R.string.fs_PersonalIntegrity_PromiseDate), cNew.getTimeInMillis());

        DbHelperClass dbh = new DbHelperClass();
        adapter = dbh.GetFireStoreAdapterIntegrity(adapter, getString(R.string.fs_PersonIntegrity), query1, ActivityIntegrity.this, usrId);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);


                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if (v == btnSubmitIntegrity) {
            if (TextUtils.isEmpty(txtTime.getText()) || TextUtils.isEmpty(txtDate.getText()) || TextUtils.isEmpty(txtIntegrityDesc.getText())) {
                CommonClass cls = new CommonClass();
            } else {


                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser fbUser = mAuth.getCurrentUser();
                String usrId = "";
                if (mAuth.getCurrentUser() != null) {
                    usrId = fbUser.getUid();
                } else {
                    return;
                }

                db = FirebaseFirestore.getInstance();

                String dtIntegrity = txtDate.getText() + " " + txtTime.getText() + ":00";

                try {

                    simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    //  String format = simpleDateFormat.format(new Date());
                    dt = simpleDateFormat.parse(dtIntegrity);
                    dateInMilli = dt.getTime();
                    //date.date.gettime.gettime
                } catch (ParseException e) {


                }

                DbHelperClass Db = new DbHelperClass();
                Person_Integrity pi = new Person_Integrity();
                pi.setFb_Id(usrId);
                //ph.setHabitDate(Timestamp.valueOf());
                pi.setPromiseDate(dateInMilli);
                pi.setPromiseDescription(txtIntegrityDesc.getText().toString());
                pi.setStatus(1);
                Db.insertFireStoreDataIntegrity(getString(R.string.fs_PersonIntegrity), ActivityIntegrity.this, pi, db);
                clearForm(linearLayout);

                mAuth = FirebaseAuth.getInstance();
                recyclerView.setLayoutManager(new LinearLayoutManager(ActivityIntegrity.this));
                recyclerView.setHasFixedSize(false);
                fetch();


            }
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

    public static class ViewHolderIntegrity extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdIntegrity;
        public final TextView mIdIntegrityDate;
        public final TextView mIdIntegrityTime;
        public final TextView mIdNoOfDays;
        public final CheckBox chkSelect;

        public Person_Integrity mItem;

        public ViewHolderIntegrity(View view) {
            super(view);
            mView = view;
            mIdIntegrity = view.findViewById(R.id.txt_Int_name);
            mIdIntegrityTime = view.findViewById(R.id.txt_int_time);
            mIdIntegrityDate = view.findViewById(R.id.txt_int_date);
            mIdNoOfDays = view.findViewById(R.id.txt_NoOfDaysLeft);
            chkSelect = view.findViewById(R.id.chkIntegrity);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdIntegrityDate.getText() + "'";
        }
    }


}

