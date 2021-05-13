package app.actionnation.actionapp.utils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import app.actionnation.actionapp.FragmentGameDefine;
import app.actionnation.actionapp.FragmentSelectGame;
import app.actionnation.actionapp.FragmentSendGameInvitations;

public class TabsAdapterGameCreation extends FragmentStatePagerAdapter {
        int mNumOfTabs;
        int activityNumber;
        ArrayList<String> fragmentArrayCaptains;
        String Title[] = {"Game Creation", "Game Invitation", "Game Selection"};


        public TabsAdapterGameCreation(FragmentManager fm, int NoofTabs, ArrayList<String> arrayCaptains) {
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
                    home = new FragmentGameDefine();

                    return home;
                case 1:
                    home = new FragmentSendGameInvitations();
                    return home;
                case 2:
                    home = new FragmentSelectGame();
                    return home;
                    default:
                    return null;
            }
        }
    }




