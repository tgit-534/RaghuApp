package app.actionnation.actionapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import app.actionnation.actionapp.Database_Content.TeamGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.OnFragmentRefreshMainListener;
import app.actionnation.actionapp.utils.TabsAdapterGameCreation;

public class ActivityGameCreation extends BaseClassUser implements View.OnClickListener, OnFragmentRefreshMainListener {

    ViewPager viewPager;
    TabLayout tabLayout;
    private final String TAG = getClass().getSimpleName();
    ExtendedFloatingActionButton fabGamecreation;
    TabsAdapterGameCreation ds;
    ExtendedFloatingActionButton fab;
    String mDocumentGameId ="";

    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_creation);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeControls();

        String strDocumentObject = getIntent().getStringExtra(Constants.Intent_GameDocumentId);

        if (strDocumentObject == null)
            strDocumentObject = mDocumentGameId;

        loadTabs(strDocumentObject);

        fragmentManager = getSupportFragmentManager();

        if (strDocumentObject != null) {
            String[] gameObj = strDocumentObject.split(getString(R.string.fm_fieldPartition));
            Fragment fragmentGameInProgress = new FragmentGameInProgress(gameObj[0]);//Get Fragment Instance
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerGameInProgress, fragmentGameInProgress).commit();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strDocumentObject = getIntent().getStringExtra(Constants.Intent_GameDocumentId);

                if (strDocumentObject == null)
                    strDocumentObject = "";
                FragmentManager fm = getSupportFragmentManager();
                FragmentGameDefine editNameDialogFragment = FragmentGameDefine.newInstance(strDocumentObject);
                editNameDialogFragment.show(fm, "fragment_edit_name");
            }
        });
    }

    private void initializeControls() {
        fab = findViewById(R.id.fab_act_createGame);

    }

    private void loadTabs(String strGameObject) {
        viewPager = findViewById(R.id.view_pager_selectGame);
        tabLayout = findViewById(R.id.tab_selectGame);
        tabLayout.addTab(tabLayout.newTab().setText("Captain!"));
        tabLayout.addTab(tabLayout.newTab().setText("Player!"));

        Log.d(TAG, "2");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ds = new TabsAdapterGameCreation(getSupportFragmentManager(), tabLayout.getTabCount(), strGameObject);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(ds);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void refreshMain(TeamGame teamGame) {
        FragmentGameInProgress fm = (FragmentGameInProgress) fragmentManager.findFragmentById(R.id.fragmentContainerGameInProgress);
        fm.updateFromTheGameDefine(teamGame);



    }

    @Override
    public void refreshMain(String documentGameId) {
        /*FragmentGameInProgress fm = (FragmentGameInProgress) fragmentManager.findFragmentById(R.id.fragmentContainerGameInProgress);
        fm.mainDataUpdate(documentGameId);*/

        TeamGame teamGame = new TeamGame();
        String[] gameObj = documentGameId.split(getString(R.string.fm_fieldPartition));

        teamGame.setGameName(gameObj[Constants.gameObject_gameName]);
        teamGame.setStartDate(Long.parseLong(gameObj[Constants.gameObject_playerStartDate]));
        teamGame.setEndDate(Long.parseLong(gameObj[Constants.gameObject_playerEndDate]));

        FragmentGameInProgress fm = (FragmentGameInProgress) fragmentManager.findFragmentById(R.id.fragmentContainerGameInProgress);
        fm.updateFromTheGameDefine(teamGame);


    }


    public static class ViewHolderSelectGame extends RecyclerView.ViewHolder {
        public final View mView;
        public final CheckBox mIdSelectGame;
        public final TextView mIdGameName;
        public final TextView mIdGameStart;
        public final TextView mIdGameEnd;
        public final Button mIdAddPlayers;

        public TeamGame mItem;

        public ViewHolderSelectGame(View view) {
            super(view);
            mView = view;
            mIdSelectGame = view.findViewById(R.id.lf_fg_chk_selectGame);
            mIdGameName = view.findViewById(R.id.lf_fg_gameName);
            mIdGameStart = view.findViewById(R.id.lf_fg_gameStartDate);
            mIdGameEnd = view.findViewById(R.id.lf_fg_gameEndDate);
            mIdAddPlayers = view.findViewById(R.id.lf_fg_btnAddPlayers);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdGameName.getText() +
                    " " + "'";
        }
    }


}