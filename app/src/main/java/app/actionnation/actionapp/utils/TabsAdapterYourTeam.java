package app.actionnation.actionapp.utils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import app.actionnation.actionapp.FragmentGameGraph;

public class TabsAdapterYourTeam extends FragmentStatePagerAdapter{
    int mNumOfTabs;
    int activityNumber;
    ArrayList<String> fragmentArrayCaptains;
    String Title[] = {"Captain", "Player"};


    public TabsAdapterYourTeam(FragmentManager fm, int NoofTabs, ArrayList<String> arrayCaptains) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = NoofTabs;
        arrayCaptains = fragmentArrayCaptains;
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
                    home = new FragmentGameGraph(0, fragmentArrayCaptains);

                return home;
            case 1:
                    home = new FragmentGameGraph(1, fragmentArrayCaptains);
                return home;
            default:
                return null;
        }
    }
}

