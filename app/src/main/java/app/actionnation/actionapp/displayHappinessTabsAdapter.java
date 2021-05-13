package app.actionnation.actionapp;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import app.actionnation.actionapp.Storage.Constants;

public class displayHappinessTabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    String Title[] = {Constants.happy_Datatabs_Gratitude, Constants.happy_Datatabs_Abundance, Constants.happy_Datatabs_Forgiveness};

    public displayHappinessTabsAdapter(FragmentManager fm, int NoofTabs) {
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
        return Title[position];
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                gratitude home = new gratitude();
                return home;
            case 1:
                FragmentAbundance home2 = new FragmentAbundance();
                return home2;
            case 2:
                FragmentForgiveness home3 = new FragmentForgiveness();
                return home3;
            default:
                return null;
        }
    }
}

