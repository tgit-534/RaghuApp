package app.actionnation.actionapp;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    String Title[] = { "Action","Inspire","Task"};

    public TabsAdapter(FragmentManager fm, int NoofTabs){
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
                MainListFragment home = new MainListFragment("Challenges");
              //  HomeFragment home = new HomeFragment();
                return home;
            case 1:
                 home = new MainListFragment("Leadership");
                //  HomeFragment home = new HomeFragment();
                return home;
            case 2:
                 home = new MainListFragment("NLW");
                //  HomeFragment home = new HomeFragment();
                return home;
            default:
                return null;
        }
    }
}
