package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ActivityUserStory extends BaseClassUser implements View.OnClickListener {

    private static FragmentManager fragmentManager;
    Fragment fragmentOneStoryShow;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_story);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int showStoryStatus = 0;
        String documentId = "";
        ArrayList<String> storyDetails = getIntent().getStringArrayListExtra(getString(R.string.Intent_ArrayOneStory));
        fragmentManager = getSupportFragmentManager();

        if (storyDetails != null) {
            if (storyDetails.size() > 0) {

                Fragment argumentOneStoryFragment = FragmentShowOneStory.newInstance(storyDetails);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainerShowOneStory, argumentOneStoryFragment).commit();
                showStoryStatus = 1;
                documentId = storyDetails.get(0);
            }
        }
        fab = findViewById(R.id.fabStory);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog();
            }
        });

        Fragment argumentFragment = FragmentShowUserStory.newInstance(getIntent().getStringExtra(getString(R.string.Intent_UserImagePath)), showStoryStatus, documentId);
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerShowStory, argumentFragment).commit();

    }


    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        String imgUrl = getIntent().getStringExtra(getString(R.string.Intent_UserImagePath));
        FragmentCreateStory editNameDialogFragment = FragmentCreateStory.newInstance(imgUrl);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public void onClick(View v) {

    }


}