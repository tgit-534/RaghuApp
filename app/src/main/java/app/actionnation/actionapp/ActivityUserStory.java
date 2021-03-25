package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ActivityUserStory extends BaseClassUser implements View.OnClickListener {

    private static FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_story);
        generatePublicMenu();

        fragmentManager = getSupportFragmentManager();

        Fragment argumentFragment = new FragmentShowUserStory();//Get Fragment Instance
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerShowStory, argumentFragment).commit();

    }

    @Override
    public void onClick(View v) {

    }
}