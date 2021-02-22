package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.View;

public class ActivityProfileComplete extends BaseClassUser implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_complete);
        generatePublicMenu();




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }


}






