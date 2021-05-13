package app.actionnation.actionapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import app.actionnation.actionapp.Database_Content.TeamGame;
import app.actionnation.actionapp.utils.TabsAdapterGameCreation;

public class ActivityGameCreation extends BaseClassUser implements View.OnClickListener, FragmentSendGameInvitations.OnFragmentCommunicationListener {

    ViewPager viewPager;
    TabLayout tabLayout;
    private final String TAG = getClass().getSimpleName();
    ExtendedFloatingActionButton fabGamecreation;
    TabsAdapterGameCreation ds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_creation);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeControls();
        loadTabs();

        fabGamecreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    private void initializeControls() {
        viewPager = findViewById(R.id.view_pager_gameCreation);
        tabLayout = findViewById(R.id.tab_gameCreation);
        fabGamecreation = findViewById(R.id.fab_act_gameset);

    }

    private void loadTabs() {

        tabLayout.addTab(tabLayout.newTab().setText("Captain!"));
        tabLayout.addTab(tabLayout.newTab().setText("Player!"));
        tabLayout.addTab(tabLayout.newTab().setText("Player!"));

        Log.d(TAG, "2");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        ArrayList<String> arrayCaptains = getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));

        ds = new TabsAdapterGameCreation(getSupportFragmentManager(), tabLayout.getTabCount(), arrayCaptains);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(ds);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPlayersSend(ArrayList<String> listPlayers) {
        String tag = "android:switcher:" + R.id.view_pager_gameCreation + ":" + 1;

        FragmentSelectGame fm = (FragmentSelectGame) ds.instantiateItem(viewPager, 2);
        fm.onNameChange(listPlayers);


        /*FragmentSelectGame fragment = (FragmentSelectGame) getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            fragment.onNameChange(listPlayers);

        }*/
    }

    public static class ViewHolderSelectGame extends RecyclerView.ViewHolder {
        public final View mView;
        public final CheckBox mIdSelectGame;
        public final TextView mIdGameName;
        public final TextView mIdGameStart;
        public final TextView mIdGameEnd;

        public TeamGame mItem;

        public ViewHolderSelectGame(View view) {
            super(view);
            mView = view;
            mIdSelectGame = view.findViewById(R.id.lf_fg_chk_selectGame);
            mIdGameName = view.findViewById(R.id.lf_fg_gameName);
            mIdGameStart = view.findViewById(R.id.lf_fg_gameStartDate);
            mIdGameEnd = view.findViewById(R.id.lf_fg_gameEndDate);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdGameName.getText() +
                    " " + "'";
        }
    }


}