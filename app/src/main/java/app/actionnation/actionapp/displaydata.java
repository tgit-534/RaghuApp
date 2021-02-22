package app.actionnation.actionapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import app.actionnation.actionapp.Storage.Constants;

public class displaydata extends BaseClassUser implements ActionPhilosophy.OnFragmentInteractionListener {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaydata);
        generatePublicMenu();

        viewPager = findViewById(R.id.view_pager_displayData);
        tabLayout = findViewById(R.id.tab_displayData);
        tabLayout.addTab(tabLayout.newTab().setText("Values"));
        tabLayout.addTab(tabLayout.newTab().setText("Affirmations"));
        tabLayout.addTab(tabLayout.newTab().setText("ActionAsNation"));
        tabLayout.addTab(tabLayout.newTab().setText(Constants.display_Datatabs_MVP));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        displayDataTabsAdapter ds;
        ds = new displayDataTabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(ds);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static class ViewHolderActionPhilosopy extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
       // public CommonData mItem;

        public ViewHolderActionPhilosopy(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_name_philosophy);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }
}

