package app.actionnation.actionapp.adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import app.actionnation.actionapp.FragmentEatHealthy;
import app.actionnation.actionapp.FragmentExercise;
import app.actionnation.actionapp.Storage.Constants;

public class EatExerciseAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    int activityNumber;

    String TitleEatExercise[] = {Constants.eatHealty_Datatabs_Eat, Constants.eatHealthy_Datatabs_Avoid, Constants.eatHealty_Datatabs_Exercise};

    public EatExerciseAdapter(FragmentManager fm, int NoofTabs, int activityName) {
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
        seq = TitleEatExercise[position];
        return seq;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment home = null;

        switch (position) {
            case 0:

                home = new FragmentEatHealthy(Constants.aaq_EatHealthy_Number);
                return home;
            case 1:
                home = new FragmentEatHealthy(Constants.aaq_AvoidFood_Number);
                return home;
            case 2:
                home = new FragmentExercise();
                return home;
            default:
                return null;
        }
    }
}

