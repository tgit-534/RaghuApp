package app.actionnation.actionapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ActivityCommonInsertion extends BaseClassUser {
    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_insertion);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //BarChart
        fragmentManager = getSupportFragmentManager();

        Fragment argumentFragment = new FragmentInsertComments();//Get Fragment Instance
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerInsertComment, argumentFragment).commit();

    }
}