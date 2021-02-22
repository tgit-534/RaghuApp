package app.actionnation.actionapp;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import app.actionnation.actionapp.Storage.Constants;

public class TabsAdapterCommon extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    int activityNumber;

    String Title[] = {Constants.attention_Datatabs_distraction, Constants.attention_Datatabs_traction};
    String TitleEating[] = {Constants.eatHealty_Datatabs_Eat, Constants.eatHealthy_Datatabs_Avoid};

    public TabsAdapterCommon(FragmentManager fm, int NoofTabs, int activityName) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = NoofTabs;
        activityNumber = activityName;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence seq = "";
        if (activityNumber == Constants.TabAttention)
            seq = Title[position];
        else if (activityNumber == Constants.TabEatHealthy)
            seq = TitleEating[position];
        return seq;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment home = null;

        switch (position) {
            case 0:
                if (activityNumber == Constants.TabAttention)
                    home = new FragmentDistraction();
                else if (activityNumber == Constants.TabEatHealthy)
                    home = new FragmentEatHealthy(Constants.aaq_EatHealthy_Number);
                return home;
            case 1:
                if (activityNumber == Constants.TabAttention)
                    home = new FragmentTraction();
                else if (activityNumber == Constants.TabEatHealthy)
                    home = new FragmentEatHealthy(Constants.aaq_AvoidFood_Number);
                return home;
            default:
                return null;
        }
    }
}

