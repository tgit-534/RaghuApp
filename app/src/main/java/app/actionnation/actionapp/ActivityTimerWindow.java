package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;

import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelper;

public class ActivityTimerWindow extends BaseClassUser implements View.OnClickListener {
    private static FragmentManager fragmentManager;

    TextView txtHeading;
    Button btnFinish;
    String HabitName = "";
    String HabitTotal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_window);
        generatePublicMenu();
        txtHeading = findViewById(R.id.timeWindowHeading);
        btnFinish = findViewById(R.id.btn_hbt_finish);
        btnFinish.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            HabitName = extras.getString("Habit_Name");
            HabitTotal = extras.getString("Habit_Total");
        }
        txtHeading.setText(HabitName);


        fragmentManager = getSupportFragmentManager();
        Fragment argumentFragment = new timingset();//Get Fragment Instance
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString("Habit_Name", HabitName);
        data.putString("Habit_Total", HabitTotal);

        argumentFragment.setArguments(data);//Finally set argument bundle to fragment
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, argumentFragment).commit();

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_hbt_finish) {
            CommonClass cls = new CommonClass();
            String usrId = cls.fetchUserId(FirebaseAuth.getInstance());
            int dayOfTheYear = fetchDate(0);
            int yr = fetchDate(1);
            DbHelper db = new DbHelper(ActivityTimerWindow.this);
            cls.SubmitHabitScore(Constants.Game_CommonScore, Integer.parseInt(HabitTotal), HabitName, db, usrId, dayOfTheYear, yr);
            db.insertHabitDayTrack(HabitName, usrId, dayOfTheYear, yr, Constants.Status_One);

        }
    }


}