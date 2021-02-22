package app.actionnation.actionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityMainProfile extends AppCompatActivity {


    Button btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);

        btnProfile = findViewById(R.id.btn_finish_Profile);


        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(ActivityMainProfile.this, ActivityMainObjectives.class);
                Bundle mBundle = new Bundle();
                mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
                homepage.putExtras(mBundle);
                startActivity(homepage);
            }
        });


    }
}