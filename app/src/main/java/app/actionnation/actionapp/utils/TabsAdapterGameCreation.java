package app.actionnation.actionapp.utils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import app.actionnation.actionapp.FragmentSelectGame;
import app.actionnation.actionapp.Storage.Constants;

public class TabsAdapterGameCreation extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    int activityNumber;
    String gameDataObject;
    String Title[] = {Constants.tabsAdapter_GameCreation_FirstTab, Constants.tabsAdapter_GameCreation_SecondTab};


    public TabsAdapterGameCreation(FragmentManager fm, int NoofTabs, String gameDataObj) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = NoofTabs;
        gameDataObject = gameDataObj;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence seq = "";
        seq = Title[position];
        return seq;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment home = null;

        switch (position) {
            case 0:
                home = new FragmentSelectGame(gameDataObject, Constants.Status_Zero);
                return home;
            case 1:
                home = new FragmentSelectGame(gameDataObject, Constants.Status_One);
                return home;
            default:
                return null;
        }
    }
}




