package app.actionnation.actionapp;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import app.actionnation.actionapp.Storage.Constants;

public class displayDataTabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    String Title[] = {Constants.display_Datatabs_Values,Constants.display_Datatabs_Affirmation,Constants.display_Datatabs_Description, Constants.display_Datatabs_MVP};

    public displayDataTabsAdapter(FragmentManager fm, int NoofTabs){
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

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                ActionPhilosophy home = new ActionPhilosophy(Title[0]);
                //  HomeFragment home = new HomeFragment();
                return home;
            case 1:
                home = new ActionPhilosophy(Title[1]);
                //  HomeFragment home = new HomeFragment();
                return home;
            case 2:
                home = new ActionPhilosophy(Title[2]);
                //  HomeFragment home = new HomeFragment();
                return home;
            case 3:
                home = new ActionPhilosophy(Title[3]);
                //  HomeFragment home = new HomeFragment();
                return home;
            default:
                return null;
        }
    }
}
