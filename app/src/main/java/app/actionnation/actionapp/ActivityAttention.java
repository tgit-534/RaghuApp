package app.actionnation.actionapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import app.actionnation.actionapp.Database_Content.Personal_Distraction;
import app.actionnation.actionapp.Storage.Constants;

public class ActivityAttention extends BaseClassUser implements View.OnClickListener, FragmentDataInsertion.ListenFromActivity {
    private long START_TIME_IN_MILLIS = 1000;

    String TAG = "attention";
    ViewPager viewPager;
    TabLayout tabLayout;
    ExtendedFloatingActionButton fabDistraction, fabTraction;
    int tabPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabDistraction = findViewById(R.id.fab_act_distraction_finish);
        fabTraction = findViewById(R.id.fab_act_traction_finish);


        viewPager = findViewById(R.id.view_pager_attention);
        tabLayout = findViewById(R.id.tab_attention);
        tabLayout.addTab(tabLayout.newTab().setText("Gratitude"));
        tabLayout.addTab(tabLayout.newTab().setText("Forgiveness"));
        Log.d(TAG, "2");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                animateFab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        /*tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/

        // tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));


        TabsAdapterCommon ds;
        ds = new TabsAdapterCommon(getSupportFragmentManager(), tabLayout.getTabCount(), Constants.TabAttention);
        tabLayout.setupWithViewPager(viewPager);
        //   viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                animateFab(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        viewPager.setAdapter(ds);


        String strInsertionData = getIntent().getStringExtra(Constants.Intent_DataInsertion);
        if (strInsertionData != null) {
            if (strInsertionData.equals(getString(R.string.Page_Redirect_Traction))) {
                viewPager.setCurrentItem(1);
            }
        }


        fabDistraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();

                if (tabPosition == 0) {

                    FragmentDistraction fragment =
                            (FragmentDistraction) fm.getFragments().get(0);
                    fragment.submitWin();
                } else if (tabPosition == 1) {

                    FragmentTraction fragment =
                            (FragmentTraction) fm.getFragments().get(1);
                    fragment.submitWin();
                }


            }
        });

        fabTraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();
                FragmentTraction fragment =
                        (FragmentTraction) fm.getFragments().get(1);
                fragment.submitWin();


            }
        });


        Log.d(TAG, "3");


    }


    @Override
    public void onClick(View v) {

    }

    private void animateFab(int position) {
        switch (position) {
            case 0:
                fabDistraction.show();
                fabTraction.hide();
                break;
            case 1:
                fabDistraction.hide();
                fabTraction.show();
                break;

            default:
                fabDistraction.show();
                fabTraction.hide();
                break;
        }
    }

    @Override
    public void doSomethingInFragment(int insertStatus) {

        if (insertStatus == Integer.parseInt(getString(R.string.viewHolder_Insert_traction))) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTraction fragment =
                    (FragmentTraction) fm.getFragments().get(1);
            fragment.fetch();
        }

    }


    public static class ViewHolderDistraction extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdDistraction;
        public final CheckBox chkDistraction;

        public Personal_Distraction mItem;

        public ViewHolderDistraction(View view) {
            super(view);
            mView = view;
            mIdDistraction = view.findViewById(R.id.distraction_name);
            chkDistraction = view.findViewById(R.id.chkDistraction);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdDistraction.getText() + "'";
        }
    }

}
