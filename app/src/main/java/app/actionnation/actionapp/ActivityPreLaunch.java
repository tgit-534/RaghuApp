package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.fragment.app.FragmentManager;

public class ActivityPreLaunch extends BaseClassUser implements View.OnClickListener {

    TextView habitHeadline;
    Button setHabit, setDistraction, setTraction, setGratitude, setAbundance, setBelief;
    ViewFlipper vw;
    ImageButton imgPrev, imgNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_launch);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setHabit = findViewById(R.id.btn_preLaunch_HabitSet);
        setDistraction = findViewById(R.id.btn_preLaunch_DistractionSetup);
        setTraction = findViewById(R.id.btn_preLaunch_TractionSetup);
        setGratitude = findViewById(R.id.btn_preLaunch_GratitudeSet);
        setAbundance = findViewById(R.id.btn_preLaunch_AbundanceSetup);
        setBelief = findViewById(R.id.btn_preLaunch_BeliefSetup);
        vw = findViewById(R.id.vf_act_preLoad);
        imgPrev = findViewById(R.id.ImgBtn_PreLaunch_Prev);

        imgNext = findViewById(R.id.ImgBtn_PreLaunch_Next);

        setHabit.setOnClickListener(this);
        setDistraction.setOnClickListener(this);
        setTraction.setOnClickListener(this);
        setGratitude.setOnClickListener(this);
        setAbundance.setOnClickListener(this);
        setBelief.setOnClickListener(this);
        imgPrev.setOnClickListener(this);
        imgNext.setOnClickListener(this);




    }

    private void showEditDialog(String insertionType) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentDataInsertion editNameFragment = FragmentDataInsertion.newInstance(insertionType);
        editNameFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public void onClick(View v) {
        int position = v.getId();
        switch (position) {
            case R.id.btn_preLaunch_HabitSet:
                showEditDialog(getString(R.string.Page_Redirect_Habit));
                break;
            case R.id.btn_preLaunch_DistractionSetup:
                showEditDialog(getString(R.string.Page_Redirect_Attention));
                break;
            case R.id.btn_preLaunch_TractionSetup:
                showEditDialog(getString(R.string.Page_Redirect_Traction));

                break;
            case R.id.btn_preLaunch_GratitudeSet:
                showEditDialog(getString(R.string.Page_Redirect_Gratitude));

                break;
            case R.id.btn_preLaunch_AbundanceSetup:
                showEditDialog(getString(R.string.Page_Redirect_Abundance));

                break;
            case R.id.btn_preLaunch_BeliefSetup:
                showEditDialog(getString(R.string.Page_Redirect_Belief));
                break;

            case R.id.ImgBtn_PreLaunch_Prev:
                vw.showPrevious();
                break;
            case R.id.ImgBtn_PreLaunch_Next:
                vw.showNext();
                break;
        }
    }
}