package app.actionnation.actionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityExcerciseEat extends BaseClassUser {

    Button btnExercise, btnEatProperly;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise_eat);
        generatePublicMenu();


        btnExercise = findViewById(R.id.btn_Activity_Excercise);
        btnEatProperly = findViewById(R.id.btn_Activity_Eat);

        btnExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(ActivityExcerciseEat.this, ActivityExerciseNew.class);
                Bundle mBundle = new Bundle();
                mBundle.putIntegerArrayList(getString(R.string.Intent_ArrayGameScore), getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore))));
                mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
                homepage.putExtras(mBundle);
                startActivity(homepage);
            }
        });


        btnEatProperly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(ActivityExcerciseEat.this, ActivityEatHealthy.class);
                Bundle mBundle = new Bundle();
                mBundle.putIntegerArrayList(getString(R.string.Intent_ArrayGameScore), getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore))));
                mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
                homepage.putExtras(mBundle);
                startActivity(homepage);
            }
        });
    }
}