package app.actionnation.actionapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelper;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link timingset#newInstance} factory method to
 * create an instance of this fragment.
 */
public class timingset extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private long START_TIME_IN_MILLIS;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunner;
    private long mTimeLeftInMillis;
    Button btnStart, btnReset;
    TextView mTextViewCountDown;
    private boolean mStartBoolean = false;

    // TODO: Rename and change types of parameters
    NumberPicker picker1;
    private String[] pickerVals;
    String activityName;
    private long mEndTime;
    String strHabit = "";
    String HabitTotal = "";
    String activity_Number = "";


    public timingset() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        picker1 = view.findViewById(R.id.numberpicker_main_picker_fragment);
        btnStart = view.findViewById(R.id.btn_tset_clock);
        mTextViewCountDown = view.findViewById(R.id.txt_tset_showtimer);

        /*if (isAdded()) {
            activityName = getActivity().getClass().getSimpleName();
        }*/

        CommonClass cls = new CommonClass();

        /*Log.d("picker value" + "raghunandan", activityName);
        cls.callToast(getActivity(), activityName);*/


        if (getArguments() != null) {

            activity_Number = getArguments().getString(getString(R.string.Activity_Number));

            if (activity_Number != null) {
                activityName = getArguments().getString("params");
                strHabit = getArguments().getString("Habit_Name");
                HabitTotal = getArguments().getString("Habit_Total");
            }
        }

        // cls.callToast(getActivity(), activityName);
        // Log.d( "raghunandan", activityName);


        picker1.setMaxValue(12);
        picker1.setMinValue(0);
        pickerVals = new String[]{"Select Time", "5 Minutes", "10 Minutes", "15 Minutes", "20 Minutes", "25 Minutes", "30 Minutes", "35 Minutes", "40 Minutes", "45 Minutes", "50 Minutes", "55 Minutes", "60 Minutes"};
        picker1.setDisplayedValues(pickerVals);

        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int valuePicker1 = picker1.getValue();
                Log.d("picker value", pickerVals[valuePicker1]);
            }

        });


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mTimerRunner) {

                    AlertBoxYesNo();


                } else {
                    startTimer();

                    if (mTimeLeftInMillis > 0)
                        btnStart.setText("Stop");

                }
            }
        });
    }

    private long GetPicValue() {

        int pickerVal = picker1.getValue();

        if (pickerVal == 0) {


        } else if (pickerVal == 1) {
            START_TIME_IN_MILLIS = 300000;
        } else if (pickerVal == 2) {
            START_TIME_IN_MILLIS = 600000;
        } else if (pickerVal == 3) {
            START_TIME_IN_MILLIS = 900000;
        } else if (pickerVal == 4) {
            START_TIME_IN_MILLIS = 1200000;
        } else if (pickerVal == 5) {
            START_TIME_IN_MILLIS = 1500000;
        } else if (pickerVal == 6) {
            START_TIME_IN_MILLIS = 1800000;

        } else if (pickerVal == 7) {
            START_TIME_IN_MILLIS = 2100000;

        } else if (pickerVal == 8) {
            START_TIME_IN_MILLIS = 2400000;

        } else if (pickerVal == 9) {
            START_TIME_IN_MILLIS = 2700000;

        } else if (pickerVal == 10) {
            START_TIME_IN_MILLIS = 3000000;

        } else if (pickerVal == 11) {
            START_TIME_IN_MILLIS = 3300000;

        } else if (pickerVal == 12) {
            START_TIME_IN_MILLIS = 3600000;

        } else if (pickerVal == 13) {
            START_TIME_IN_MILLIS = 3900000;

        }
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        return mTimeLeftInMillis;
    }


    private void startTimer() {

        if (!mStartBoolean) {
            mTimeLeftInMillis = GetPicValue();
            if (mTimeLeftInMillis == 0) {
                CommonClass cls = new CommonClass();
                cls.callToast(getContext(), "Select a value!");
                return;
            }

            Log.d("On Stop Raghunandan startboolean", String.valueOf(mTimeLeftInMillis));

        }
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        Log.d("On starttimer Raghunandan", String.valueOf(mTimeLeftInMillis));
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                if (mTimeLeftInMillis > 0) {
                    mTimerRunner = false;
                    CommonClass cls = new CommonClass();
                    cls.callToast(getActivity(), "I'm Meditation");
                    String usrId = cls.fetchUserId(FirebaseAuth.getInstance());
                    int dayOfTheYear = cls.fetchDate(0);
                    int yr = cls.fetchDate(1);

                    DbHelper db = new DbHelper(getActivity());
                    if (HabitTotal != null) {
                        if (!HabitTotal.equals("")) {
                            db.insertHabitDayTrack(strHabit, usrId, dayOfTheYear, yr, Constants.Status_One);
                            cls.SubmitHabitScore(Constants.Game_CommonScore, Integer.parseInt(HabitTotal), strHabit, db, cls.fetchUserId(FirebaseAuth.getInstance()), cls.fetchDate(0), cls.fetchDate(1));
                        }
                    }

                    if (activity_Number.equals(String.valueOf(Constants.GS_meditationScore))) {
                        cls.SubmitGenericGame(Constants.GS_meditationScore, db, usrId, dayOfTheYear, yr);

                    } else if (activity_Number.equals(String.valueOf(Constants.GS_trueLearningScore))) {

                        cls.SubmitGenericGame(Constants.GS_trueLearningScore, db, usrId, dayOfTheYear, yr);

                    } else if (activity_Number.equals(String.valueOf(Constants.GS_exerciseScore))) {

                        cls.SubmitGenericGame(Constants.GS_exerciseScore, db, usrId, dayOfTheYear, yr);

                    } else if (activity_Number.equals(String.valueOf(Constants.GS_natureScore))) {

                        cls.SubmitGenericGame(Constants.GS_natureScore, db, usrId, dayOfTheYear, yr);

                    } else if (activity_Number.equals(String.valueOf(Constants.GS_yourStoryScore))) {

                        cls.SubmitGenericGame(Constants.GS_yourStoryScore, db, usrId, dayOfTheYear, yr);

                    } else if (activity_Number.equals(String.valueOf(Constants.GS_ourBeliefScore))) {
                        cls.SubmitGenericGame(Constants.GS_ourBeliefScore, db, usrId, dayOfTheYear, yr);

                    }


                    btnStart.setText("Start");
                }
            }


        }.start();
        mTimerRunner = true;
        mStartBoolean = true;
    }

    /*private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);


    }*/

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_timingset, container, false);

    }


    public void AlertBoxYesNo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set a title for alert dialog
        builder.setTitle("Stop the timer.");

        // Ask the final question
        builder.setMessage("Are you sure to stop?");

        // Set the alert dialog yes button click listener
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when user clicked the Yes button
                // Set the TextView visibility GONE

                SharedPreferences prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putLong("millisLeft", mTimeLeftInMillis);
                editor.putBoolean("timerRunning", false);
                editor.putLong("endTime", 0);
                editor.apply();


                btnStart.setText("Start");
                mTextViewCountDown.setText("00:00");
                picker1.setValue(0);
                mTimerRunner = false;
                mStartBoolean = false;
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                }

            }
        });

        // Set the alert dialog no button click listener
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }


    @Override
    public void onStop() {
        super.onStop();

        if (mStartBoolean) {
            SharedPreferences prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong("millisLeft", mTimeLeftInMillis);
            editor.putBoolean("timerRunning", mTimerRunner);

            Log.d("On Stop Raghunanda stop n", String.valueOf(mTimerRunner));

            editor.putLong("endTime", mEndTime);
            editor.apply();
        }

        Log.d("On Stop Raghunandan", String.valueOf(mTimeLeftInMillis));
        Log.d("On Stop Raghunandan", String.valueOf(mEndTime));
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mTimeLeftInMillis);
        mTimerRunner = prefs.getBoolean("timerRunning", false);
        mStartBoolean = false;
        Log.d("On Stop Raghunandan 3 boolean ", String.valueOf(mTimerRunner));


        Log.d("On Stop Raghunandan 3", String.valueOf(mTimeLeftInMillis));

        updateCountDownText();
        //  updateButtons();
        if (mTimerRunner) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            Log.d("On Stop Raghunandan 2", String.valueOf(mTimeLeftInMillis));

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunner = false;
                mTextViewCountDown.setText("00:00");
            } else {
                Log.d("On Stop Raghunandan 4", String.valueOf(mTimeLeftInMillis));
                mStartBoolean = true;
                btnStart.setText("Stop");
                startTimer();
            }
        } else {
            mTextViewCountDown.setText("00:00");
        }
    }
}





