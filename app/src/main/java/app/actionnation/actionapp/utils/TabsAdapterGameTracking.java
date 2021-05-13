package app.actionnation.actionapp.utils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import app.actionnation.actionapp.FragmentCoinTracker;
import app.actionnation.actionapp.FragmentGameTracker;
import app.actionnation.actionapp.FragmentStakeGame;

public class TabsAdapterGameTracking extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    int activityNumber;
    String Title[] = {"Game Tracking", "Game Challenge", "CoinTracking"};


    public TabsAdapterGameTracking(FragmentManager fm, int NoofTabs) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = NoofTabs;
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
                home = new FragmentGameTracker();

                return home;
            case 1:
                home = new FragmentStakeGame();
                return home;
            case 2:
                home = new FragmentCoinTracker();
                return home;
            default:
                return null;
        }
    }
}


